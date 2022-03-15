package com.onelive.websocket.handler;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onelive.websocket.constants.StatusCode;
import com.onelive.websocket.constants.WebSocketRedisKeys;
import com.onelive.websocket.dto.AppLoginUser;
import com.onelive.websocket.dto.ChatBodyEntity;
import com.onelive.websocket.dto.ChatEntity;
import com.onelive.websocket.dto.SysAdvNoticeDto;
import com.onelive.websocket.util.AesUtil;
import com.onelive.websocket.util.RandomUtil;
import com.onelive.websocket.util.RedisKeys;
import com.onelive.websocket.util.SimpleConstant;
import com.onelive.websocket.util.SpringUtils;
import com.onelive.websocket.util.StringUtils;
import com.onelive.websocket.util.SymbolConstant;
import com.onelive.websocket.util.TokenRedisGuavaCacheUtils;
import com.onelive.websocket.util.UUIDUtils;
import com.onelive.websocket.util.WebsocketBusinessRedisUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理请求
 *
 * @author mao
 * @description
 */
@Slf4j
@Component
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	public static final String KEY = "c1kgVioySoUVimtw";
	public static final String CONTENT_TYPE = "Content-Type";

	/**
	 * 存储已经登录用户的channel对象
	 */
	public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * 存储用户id和用户的channelId绑定
	 */
	public static ConcurrentHashMap<String, ChannelId> userMap = new ConcurrentHashMap<>();
	/**
	 * 用于存储群聊房间号和群聊成员的channel信息
	 */
	public static ConcurrentHashMap<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();
	
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 添加到channelGroup通道组
		channelGroup.add(ctx.channel());
		ctx.channel().id();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// 添加到channelGroup 通道组
		channelGroup.remove(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 首次连接
			if (null != msg && msg instanceof FullHttpRequest) {
				FullHttpRequest request = (FullHttpRequest) msg;
				String[] uris = request.uri().split("\\?");
				String uri = uris[1];
				// todo 加密解密秘钥 先定义写死 后续初始化常量数据里面
				uri = AesUtil.aesDecrypt(uri, KEY);
				uri = uris[0] + "?" + uri;
				Map<String, String> param = StringUtils.parseUri(uri);
				// 验签通过 token直接续期
				log.info("==========解密后的uri=========：" + uri);
				if (!checkSign(param)) {
					Channel ct = ctx.channel();
					if (ct != null) {
						CommonSendErrorMsgAndCloseToOne(JSONObject.toJSONString(ChatEntity
								.getErrorChatEntity(StatusCode.UNLEGAL.getMsg(), StatusCode.UNLEGAL.getCode())), ctx,
								HttpResponseStatus.BAD_REQUEST);
//                        channelGroup.remove(ctx.channel());//移除管道
//                        ctx.close();//关闭连接
//                        ctx.disconnect();
						return;
					}
				}
				log.info("===========第一次连接===开始加入主播房间操作======Start=========");
				this.enterRoom(uri, ctx);
				log.info("===========第一次连接===开始加入主播房间操作=======end========");
				// 如果url包含参数，需要处理
				if (uri.contains("?")) {
					String newUri = uri.substring(0, uri.indexOf("?"));
					request.setUri(newUri);
				}
			} else if (msg instanceof TextWebSocketFrame) {
				TextWebSocketFrame frame = (TextWebSocketFrame) msg;
				log.info("客户端收到服务器数据：{}", frame.text());
				ChannelId id = ctx.channel().id();
				// 当前用户的id
				String userId = userMap.entrySet().stream()
						.filter(kvEntry -> kvEntry.getValue().toString().equals(id.toString())).map(Map.Entry::getKey)
						.findFirst().get();
				processMessage(AesUtil.aesDecrypt(frame.text(), KEY), userId);
			}
			super.channelRead(ctx, msg);
		} catch (Exception e) {
			log.error("WebsocketServer--------连接接发送错误------", e);
			if (e instanceof IOException) {
				log.error("==========用户强制关闭了一个连接============");
			} else {
				log.error(e.getMessage().toString());
			}
			channelInactive(ctx);
		}
	}

	/**
	 * 处理收到的消息 根据chatType 和 operatorType 做不同处理
	 */
	public void processMessage(String text, String userId) {
		ChatEntity socketMessage = JSON.parseObject(text, ChatEntity.class);
		// isReceive为true表示回执，不做任何处理，仅仅打印日志
		if (socketMessage.isReceive()) {
			log.info("WebsocketServer_receive----用户id：{} 收到回执消息，MsgId： {}", userId, socketMessage.getMsgId());
			return;
		}
		ChatBodyEntity chatBodyEntity = socketMessage.getBody();
		socketMessage.setMsgId(UUIDUtils.getUUID());

		// 禁言标识
		Object banned = WebsocketBusinessRedisUtils
				.hGet(WebSocketRedisKeys.banned_room_key + chatBodyEntity.getTargetId(), userId);
		// 如果在禁言中,返回消息给用户
		if (banned != null) {
			try {
				ChatEntity chatEntity = ChatEntity.getChatEntity("您在禁言中！", userId, 3);
				chatEntity.getBody().setBannedEndTime((long) banned);
				this.singleNotice(chatEntity);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		// 群聊
		if (chatBodyEntity.getChatType() == 2) {
			ChannelGroup channelGroupTarget = groupMap.get(chatBodyEntity.getTargetId());
			if (channelGroupTarget == null) {
				// TODO 返回错误给前端
				return;
			}
			CommonSendMsgToMore(JSONObject.toJSONString(socketMessage), channelGroupTarget);
			// 单聊
		} else if (chatBodyEntity.getChatType() == 1) {
			ChannelId channelId = userMap.get(chatBodyEntity.getTargetId());
			if (channelId != null) {
				Channel ct = channelGroup.find(channelId);
				if (ct != null) {
					CommonSendMsgToOne(JSONObject.toJSONString(socketMessage), ct);
				}
			}
		}
	}

	/**
	 * 主播开播创建房间
	 *
	 * @param studioNum
	 */
	public void createRoom(String studioNum) {
		ChannelGroup cGroup = null;
		// 查询用户拥有的组是否已经创建了
		cGroup = groupMap.get(studioNum);
		// 如果群聊管理对象没有创建
		if (cGroup == null) {
			// 构建一个channelGroup群聊管理对象然后放入groupMap中
			cGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
			groupMap.put(studioNum, cGroup);
		}
	}

	/**
	 * 用户首次进入房间
	 *
	 * @param uri
	 * @param ctx
	 */
	public void enterRoom(String uri, ChannelHandlerContext ctx) {
		Map<String, String> param = StringUtils.parseUri(uri);
		// 房间号
		String studioNum = param.get("studioNum");
		String userId = param.get("userId");
		try {
			// 获取redis中的房间号
//            Object byRoomNum = WebsocketBusinessRedisUtils.get(WebSocketRedisKeys.anchor_studioNum + studioNum);
//            if (byRoomNum == null) {
//                log.info("当前直播间未开播：{}", studioNum);
//                return;
//            }

			// 被踢用户不可以进入
			if (StringUtils.isNotEmpty(userId)) {
				Object kicking = WebsocketBusinessRedisUtils.hGet(WebSocketRedisKeys.kicking_room_key + studioNum,
						userId);
				if (kicking != null) {
					return;
				}
			}

			// 收费直播间试看超时用户不可以进入
			if (StringUtils.isNotEmpty(userId)) {
				Object trySee = WebsocketBusinessRedisUtils
						.hGet(WebSocketRedisKeys.studioNum_Try_See_User_Id + studioNum, userId);
				if (trySee != null) {
					return;
				}
			}

			// 第1次连接,判断roomId 是否空,不为空加入群聊
			if (StringUtils.isNotEmpty(studioNum)) {
				ChannelGroup cGroup = groupMap.get(studioNum);
				// 群聊管理对象没有创建
				if (cGroup == null) {
					log.info("WebsocketServer----当前直播间未连接");
					createRoom(studioNum); // TODO del 调试用
					cGroup = groupMap.get(studioNum);
//					return;
				}
				userMap.put(userId, ctx.channel().id());
				log.info("进入房间的的用户id是：{}", userId);
				
				// 查询用户信息，放入缓存
				AppLoginUser user = WebsocketBusinessRedisUtils.getLoginUserByToken(param.get("token"));
				if (StringUtils.isNotEmpty(param.get("lang"))) {
					user.setLang(param.get("lang"));
				}
				WebsocketBusinessRedisUtils.hset(WebSocketRedisKeys.user_list + studioNum, userId, JSON.toJSON(user));// 保存直播间登录用户ID信息
				// 直播间进入用户的人次: 用户进入房间统计次数，一个用户只会被统计一次
				WebsocketBusinessRedisUtils.sSet(WebSocketRedisKeys.studioNum_enter_times + studioNum, userId);
				// 房间号放入浏览历史 
				WebsocketBusinessRedisUtils.zSet(WebSocketRedisKeys.studioNum_browse_history + userId, studioNum, System.currentTimeMillis() / 1000);

				// 把用户放到群聊管理对象里去
				cGroup.add(ctx.channel());

				log.info("WebsocketServer ----onOpen() --------" + "连接房间roomId:" + studioNum);
				log.info("WebsocketServer ----onOpen() --------" + "连接人userId:" + userId);
				// int size = cGroup.size();
				enterRoomNotice(user, studioNum, param);
			}
		} catch (Exception e) {
			log.error("WebsocketServer----用户:{}进入直播间: {}报错", userId, studioNum);
			log.error("WebsocketServer----进入直播间报错报错", e);
		}
	}

	/**
	 * 首次进入房间对进行通知
	 *
	 * @param user
	 * @param studioNum
	 * @param param
	 */
	private void enterRoomNotice(AppLoginUser user, String studioNum, Map<String, String> param) {
		Long userId = user.getId();

		Object object = WebsocketBusinessRedisUtils.get(WebSocketRedisKeys.anchor_studioNum + studioNum);
		Integer memCountMultiple = object == null ? 1 : (Integer) object;
		// 计算假的用户数量
		int userSize = WebsocketBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + studioNum).intValue();
		int result = RandomUtil.getRandomOne(50, 60) + (userSize * memCountMultiple);
		user.setOnlineUsersCount(result);

		if (param.containsKey("source")) {
			user.setSource(param.get("source"));
		}
		log.info("WebsocketServer----enterRoomNotice() --------" + "连接房间roomId:" + studioNum);
		ChatEntity chatEntity = ChatEntity.getChatEntity(JSONObject.toJSONString(user), studioNum, 1);

		// 推送群聊信息
		CommonSendMsgToMore(JSONObject.toJSONString(chatEntity), groupMap.get(chatEntity.getBody().getTargetId()));
		SpringUtils.getBean(ThreadPoolTaskExecutor.class).execute(() -> {
			try {
				Thread.sleep(10);
				Channel ct = channelGroup.find(userMap.get(userId.toString()));
				if (ct != null) {
//					SysAdvNoticeDto sysAdvNotice = SpringUtil.getBean(SysAdvNoticeService.class).selectOneByType(2,
//							param.get("lang"));
					// TODO:查询缓存
					SysAdvNoticeDto sysAdvNotice = new SysAdvNoticeDto();
					ChatEntity chatEntityAdvNotice = ChatEntity.getChatEntity(JSONObject.toJSONString(sysAdvNotice),
							studioNum, -1);
					CommonSendMsgToOne(JSONObject.toJSONString(chatEntityAdvNotice), ct);
				}
			} catch (Exception e) {
				log.error("WebsocketServer用户首次连接发送消息出错----{}.async  ,:{}", getClass().getName(), e.getMessage(), e);
			}
		});
	}

	public void removeRoom(String studioNum) {
		ChannelGroup cGroup = groupMap.get(studioNum);
		if (cGroup != null) {
			groupMap.remove(studioNum);
			Map<Object, Object> users = WebsocketBusinessRedisUtils.hMGet(WebSocketRedisKeys.user_list + studioNum);
			for (Object userid : users.keySet()) {
				channelGroup.remove((Object) userMap.get(userid));
				userMap.remove(userid.toString());
			}
		}
	}

	public boolean new_removeRoom(String studioNum) {
		try {
			ChannelGroup cGroup = groupMap.get(studioNum);
			if (cGroup != null) {
				groupMap.remove(studioNum);
				Map<Object, Object> users = WebsocketBusinessRedisUtils.hMGet(WebSocketRedisKeys.user_list + studioNum);
				for (Object userid : users.keySet()) {
					channelGroup.remove((Object) userMap.get(userid));
					userMap.remove(userid.toString());
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean new_removeUser(Long userId) {
		try {
			ChannelId channelId = userMap.get(userId.toString());
			if (channelId != null) {
				userMap.remove(userId.toString());
				channelGroup.remove((Object) channelId);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 通用单个用户消息通知
	 */
	public void singleNotice(ChatEntity chatEntity) throws Exception {
		ChannelId channelId = userMap.get(chatEntity.getBody().getTargetId());
		if (channelId != null) {
			Channel ct = channelGroup.find(channelId);
			if (ct != null) {
				CommonSendMsgToOne(JSONObject.toJSONString(chatEntity), ct);

			}
		}
	}

	/**
	 * 通用单个用户消息通知
	 */
	public boolean new_singleNotice(ChatEntity chatEntity) {
		try {
			ChannelId channelId = userMap.get(chatEntity.getBody().getTargetId());
			if (channelId != null) {
				Channel ct = channelGroup.find(channelId);
				if (ct != null) {
					CommonSendMsgToOne(JSONObject.toJSONString(chatEntity), ct);

				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 通用房间广播
	 */
	public Boolean new_roomNotice(ChatEntity chatEntity) throws Exception {

		ChatBodyEntity chatBodyEntity = chatEntity.getBody();

		if (StringUtils.isEmpty(chatBodyEntity.getTargetId())) {
			log.error("roomNotice---------------推送弹幕消息失败!targetId为空");
			return false;
		}

		ChannelGroup channelGroupTarget = groupMap.get(chatBodyEntity.getTargetId());
		if (channelGroupTarget == null) {
			log.error("roomNotice---------------推送弹幕消息失败!targetId为空没有该房间号");
			return false;
		}

		CommonSendMsgToMore(JSONObject.toJSONString(chatEntity), groupMap.get(chatEntity.getBody().getTargetId()));
		return true;
	}

	/**
	 * 指定语言房间广播:房间内所有指定语言的用户收到消息
	 *
	 * @param chatEntity
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	public Boolean roomNoticeLang(ChatEntity chatEntity, String lang) {

		ChatBodyEntity chatBodyEntity = chatEntity.getBody();

		if (StringUtils.isEmpty(chatBodyEntity.getTargetId())) {
			log.error("roomNotice---------------推送弹幕消息失败!targetId为空");
			return false;
		}

		ChannelGroup channelGroupTarget = groupMap.get(chatBodyEntity.getTargetId());
		if (channelGroupTarget == null) {
			log.error("roomNotice---------------推送弹幕消息失败!targetId为空没有该房间号");
			return false;
		}
		taskExecutor.execute(() -> {
			Map<Object, Object> users = WebsocketBusinessRedisUtils
					.hMGet(WebSocketRedisKeys.user_list + chatBodyEntity.getTargetId());
			Collection<Object> userList = users.values();
			for (Object object : userList) {
				try {
					AppLoginUser appLoginUser = JSON.parseObject(object.toString(), AppLoginUser.class);
					if (lang.equals(appLoginUser.getLang())) {
						chatEntity.getBody().setTargetId(appLoginUser.getId().toString());
						this.singleNotice(chatEntity);
					}
				} catch (Exception e) {
					log.error("worldNoticeLang---------------异常 ：{}", e);
					log.error("worldNoticeLang---------------AppLoginUser解析发送消息失败 ：{}", object.toString());
				}
			}
		});
		return true;
	}

	/**
	 * 指定语言全站广播:全站所有指定语言的用户收到消息
	 *
	 * @param chatEntity
	 * @return
	 * @throws Exception
	 */
	public Boolean worldNoticeLang(ChatEntity chatEntity, String lang) {
		if (StringUtils.isEmpty(lang)) {
			log.error("worldNoticeLang---------------推送弹幕消息失败!targetId为空");
			return false;
		}
		taskExecutor.execute(() -> {
			for (Map.Entry<String, ChannelGroup> entry : groupMap.entrySet()) {
				CommonSendMsgToMore(JSONObject.toJSONString(chatEntity), groupMap.get(entry.getKey()));
				Map<Object, Object> users = WebsocketBusinessRedisUtils
						.hMGet(WebSocketRedisKeys.user_list + entry.getKey());
				Collection<Object> userList = users.values();
				for (Object object : userList) {
					try {
						AppLoginUser appLoginUser = JSON.parseObject(object.toString(), AppLoginUser.class);
						if (lang.equals(appLoginUser.getLang())) {
							chatEntity.getBody().setTargetId(appLoginUser.getId().toString());
							this.singleNotice(chatEntity);
						}
					} catch (Exception e) {
						log.error("worldNoticeLang---------------异常 ：{}", e);
						log.error("worldNoticeLang---------------AppLoginUser解析发送消息失败 ：{}", object.toString());
					}
				}
			}
		});
		return true;
	}

	/**
	 * 通用全站广播
	 *
	 * @param chatEntity
	 * @return
	 * @throws Exception
	 */
	public Boolean new_worldNotice(ChatEntity chatEntity) {
		try {
			for (Map.Entry<String, ChannelGroup> entry : groupMap.entrySet()) {
				CommonSendMsgToMore(JSONObject.toJSONString(chatEntity), groupMap.get(entry.getKey()));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame)
			throws Exception {

	}

	/**
	 * 连接异常 需要关闭相关资源
	 * 
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("【系统异常】======>" + cause.toString());
		ctx.close();
		ctx.channel().close();
	}

	/**
	 * 单发
	 *
	 * @param msg
	 * @param channel
	 * @return
	 */
	private void CommonSendMsgToOne(String msg, Channel channel) {
		log.info("單聊消息：" + msg);
		msg = AesUtil.aesEncrypt(msg, KEY);
		channel.writeAndFlush(new TextWebSocketFrame(msg));
	}

	private void CommonSendErrorMsgAndCloseToOne(String msg, ChannelHandlerContext ctx, HttpResponseStatus status)
			throws InterruptedException {
		log.info("单聊消息：" + msg);
		msg = AesUtil.aesEncrypt(msg, KEY);
		ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes());
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status);
		response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
		response.content().writeBytes(byteBuf);
		byteBuf.release();
		ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				log.info("单聊消息：发送错误信息完成");
				channelGroup.remove(ctx.channel());
				ctx.channel().disconnect();
				ctx.channel().close();
			}
		});
	}

	/**
	 * 群发
	 *
	 * @param msg
	 * @param group
	 * @return
	 */
	private void CommonSendMsgToMore(String msg, ChannelGroup group) {
		log.info("群裡消息：" + msg);
		msg = AesUtil.aesEncrypt(msg, KEY);
		group.writeAndFlush(new TextWebSocketFrame(msg));
	}

	/**
	 * 进行验签操作
	 *
	 * @param map
	 * @return
	 */
	private Boolean checkSign(Map<String, String> map) {
		log.info("websocket连接验签参数：" + JSONObject.toJSONString(map));
		// 签名随机数
		String flRandom = map.get("random");
		// 验签随机戳
		String flTimestamp = map.get("timestamp");
		// 验签url
		String flUrl = map.get("url");
		// 最后签名的值
		String oneSignature = map.get("signature");
		// 获取当前请求的最后一个字符串
		String lastUrl = "ws";
		// 获取token
		String token = map.get("token");

		// 根据签名规则 onelive-timestamp||onelive-random||Authorization||onelive-url 进行匹配
		StringBuilder beforeSign = new StringBuilder();
		beforeSign.append(flTimestamp);
		beforeSign.append(SymbolConstant.STRAIGHT_SLASH);
		beforeSign.append(flRandom);
		beforeSign.append(SymbolConstant.STRAIGHT_SLASH);
		beforeSign.append(token);
		beforeSign.append(SymbolConstant.STRAIGHT_SLASH);
		beforeSign.append(lastUrl);
		log.info("=====连接验签参数拼接完成====：" + beforeSign.toString());
		String sign = AesUtil.aesEncrypt(beforeSign.toString(), KEY);
		log.info("=====连接验签参数==加密后==：" + sign);
		log.info("=====连接验签参数==参数传入的加密==：" + oneSignature.replace("%2b", "="));
		if (sign.equals(oneSignature.replace("%2b", "="))) {
			log.info("=====验签成功====！");
//            AppLoginUser user = WebsocketBusinessRedisUtils.getLoginUserByToken(token);
//            log.info("获取用户信息："+JSONObject.toJSONString(user));
//            renewalToken(token, user.getAccno());
			return true;
		} else {
			log.info(
					"验签失败 fl_timestamp:{}, fl_random:{}, fl_url:{},token:{}, fl_signature:{},beforeSign:{},afterSign:{}",
					flTimestamp, flRandom, flUrl, token, oneSignature, beforeSign.toString(), sign);
		}
		return false;
	}

	/**
	 * 续期token，本地缓存和redis缓存，都只针对当天有效，当天只会续期一次
	 *
	 * @param token
	 * @param account
	 */
	private void renewalToken(String token, String account) {
		log.info("redis======用户：{}开始======token:{}续期：", account, token);
		// redis续期
		try {
			// 1、检查本地服务器是否有续期记录
			String renewalRecord = TokenRedisGuavaCacheUtils.getRenewal(token);
			log.info("======检查本地服务器是否有续期记录=====:" + renewalRecord);
			if (renewalRecord == null) {
				// 2、检查redis是否有续期记录
				if (WebsocketBusinessRedisUtils.isExistCurrentDayKey(RedisKeys.APP_ACTIVETOKEN, token)) {
					log.info("====更新本地服务缓存=====");
					// 4、更新本地服务缓存
					TokenRedisGuavaCacheUtils.putRenewal(token, SimpleConstant.ZERO);
					log.info("====更新本地服务完成=====");
				} else {
					// 3、若本地缓存和redis同时没有续期记录，则进行续期
					log.info("====若本地缓存和redis同时没有续期记录，则进行续期===Start==");
					WebsocketBusinessRedisUtils.setRenewalToken(token, account);
					// TODO 临时加入，后期去掉
					WebsocketBusinessRedisUtils.isExistCurrentDayKey(RedisKeys.APP_ACTIVETOKEN_ACCOUNT, account);
					log.info("====若本地缓存和redis同时没有续期记录，则进行续期===end==");
				}
			}
		} catch (Exception e) {
			log.error("redis续期报错 ", e);
		}
	}

	/**
	 * 获取当前直播间的用户ids
	 * 
	 * @param studioNum
	 * @return
	 */
	public Set<Long> getRoomUsers(String studioNum) {
		ChannelGroup cGroup = groupMap.get(studioNum);
		if (cGroup == null) {
			return null;
		}
		int intValue = (int) (WebsocketBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + studioNum).intValue()
				/ 0.75);
		Set<Long> result = new HashSet<Long>(intValue);
		for (Channel channel : cGroup) {
			try {
				String userId = userMap.entrySet().stream()
						.filter(kvEntry -> kvEntry.getValue().toString().equals(channel.id().toString()))
						.map(Map.Entry::getKey).findFirst().get();
				result.add(Long.parseLong(userId));
			} catch (Exception e) {
				log.error("====getRoomUsers异常====", e);
			}
		}
		return result;
	}

}
