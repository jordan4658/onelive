package com.onelive.anchor.modules.live.busiess;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.anchor.service.LiveAnchorRoomAdminService;
import com.onelive.anchor.service.LiveStudioListService;
import com.onelive.anchor.service.LotteryService;
import com.onelive.anchor.service.SysParameterService;
import com.onelive.anchor.util.AnchorBusinessRedisUtils;
import com.onelive.common.base.BaseController;
import com.onelive.common.client.OneliveApiFeignClient;
import com.onelive.common.client.WebSocketFeignClient;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.index.LiveCloseDto;
import com.onelive.common.model.req.live.LiveBeginReq;
import com.onelive.common.model.req.live.LiveSwitchChargeReq;
import com.onelive.common.model.req.live.RoomSetAdminReq;
import com.onelive.common.model.vo.live.LiveStudioForBegin;
import com.onelive.common.model.vo.lottery.LotteryRoomVO;
import com.onelive.common.model.vo.webSocket.SendMsgVO;
import com.onelive.common.mybatis.entity.LiveAnchorRoomAdmin;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.Lottery;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LiveBusiness extends BaseController {

	@Resource
	private OneliveApiFeignClient oneliveApiFeignClient;
	
	@Resource
	private WebSocketFeignClient wbeSocketFeignClient;

	@Autowired
	private LiveStudioListService liveStudioListService;

	@Resource
	private LiveAnchorRoomAdminService liveAnchorRoomAdminService;

	@Resource
	private SysParameterService sysParameterService;
	
	@Resource
    private LotteryService lotteryService;

	public ResultInfo<LiveStudioForBegin> begin(LiveBeginReq req) throws Exception {
		ResultInfo<LiveStudioForBegin> vo = liveStudioListService.getBeginToLive(req.getStudioTitle(),
				req.getStudioBackground(), req.getStudioThumbImage(), req.getCountryCode(), req.getColour(),
				req.getSharpness(), req.getProductId(), req.getTrySeeTime(), req.getGameId());
		return vo;
	}
	

	/**
	 * ???????????????????????????
	 * 
	 * @return
	 */
	public List<LotteryRoomVO> getLottery() {
		 //??????????????????
        List<LotteryRoomVO> voList = new LinkedList<>();
        QueryWrapper<Lottery> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(Lottery::getIsDelete,false).eq(Lottery::getIsWork,true);
        List<Lottery> list = lotteryService.listWithCurrentLang();//.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            list.stream().forEach(lottery -> {
                LotteryRoomVO vo = new LotteryRoomVO();
                vo.setIcon(lottery.getIcon());
                vo.setName(lottery.getName());
                vo.setId(lottery.getLotteryId());
                voList.add(vo);
            });
        }
        return voList;
	}

	/**
	 * ????????????
	 * 
	 * @return
	 */
	public ResultInfo<LiveCloseDto> liveClose() {
		Long userId = LoginInfoUtil.getUserId();
		LiveStudioList byUserId = liveStudioListService.getByUserId(userId);
		return liveStudioListService.liveClose(byUserId.getStudioNum(), userId,
				LoginInfoUtil.getDeviceType(), 1);
	}

	/**
	 * 
	 * ?????????????????????????????????????????????????????????????????????
	 * ?????????????????????????????????30???????????????????????????????????????????????????(30???????????????????????????????????????????????????????????????)??? ????????????/??????????????????????????????
	 * 
	 * @param req
	 * @return
	 */
	public Boolean switchCharge(LiveSwitchChargeReq req) {
		LiveStudioList liveStudioList = liveStudioListService.getByUserId(LoginInfoUtil.getUserId());
		long switch_charge = AnchorBusinessRedisUtils
				.getExpire(RedisKeys.STUDIO_SWITCH_CHARGE + liveStudioList.getStudioNum());
		if (switch_charge > 0) {
			throw new BusinessException(DateUtils.changeTimeFormat(switch_charge) + "???????????????????????????");
		}

		Integer productId = req.getProductId() == null ? 0 : req.getProductId();

		liveStudioList.setProductId(productId);
		boolean updateById = liveStudioListService.updateById(liveStudioList);
		if (updateById) {
			AnchorBusinessRedisUtils.set(RedisKeys.STUDIO_SWITCH_CHARGE + liveStudioList.getStudioNum(), 1, 60 * 30L);
		}
		return updateById;
	}

	/**
	 * ??????/????????????????????????????????????
	 *
	 * @param roomSetAdminReq
	 * @return
	 */
	public ResultInfo<Boolean> setAdmin(RoomSetAdminReq roomSetAdminReq) {

		QueryWrapper<LiveAnchorRoomAdmin> liveAnchorRoomAdminQueryWrapper = new QueryWrapper<>();
		liveAnchorRoomAdminQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAnchorId, LoginInfoUtil.getUserId()); // ?????????userid
		liveAnchorRoomAdminQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAdminId, roomSetAdminReq.getAdminId()); // ??????????????????id
		LiveAnchorRoomAdmin liveAnchorRoomAdmin = liveAnchorRoomAdminService.getOne(liveAnchorRoomAdminQueryWrapper);
		String context;
		Integer operatorType;
		// ???????????????
		if (roomSetAdminReq.getIsAdmin()) {
			context = "??????????????????????????????????????????????????????";
			operatorType = 8;
			if (liveAnchorRoomAdmin == null) {
				LiveAnchorRoomAdmin liveAnchorRoomAdmin2 = new LiveAnchorRoomAdmin();
				liveAnchorRoomAdmin2.setAnchorId(LoginInfoUtil.getUserId());
				liveAnchorRoomAdmin2.setAdminId(roomSetAdminReq.getAdminId());
				liveAnchorRoomAdminService.save(liveAnchorRoomAdmin2);
			}
		} else { // ????????????
			context = "?????????~????????????????????????????????????";
			operatorType = 9;
			if (liveAnchorRoomAdmin != null) {
				liveAnchorRoomAdminService.removeById(liveAnchorRoomAdmin.getId());
			}
		}

		try {
			// ????????????????????????????????????
			SendMsgVO singleMsgVo = new SendMsgVO();
			singleMsgVo.setContent(context);
			singleMsgVo.setOperatorType(operatorType);
			singleMsgVo.setTargetId(roomSetAdminReq.getAdminId().toString());
			wbeSocketFeignClient.sendSingleMsg(singleMsgVo);
		} catch (Exception e) {
			log.error("??????????????????????????????????????????!");
			e.printStackTrace();
		}

		return ResultInfo.ok(true);
	}


}