package com.onelive.api.service.live.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.live.LiveStudioListService;
import com.onelive.api.service.platform.LiveGiftLogService;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.client.WebSocketFeignClient;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.constants.webSocket.WebSocketRedisKeys;
import com.onelive.common.model.dto.index.LiveStudioListForIndexDTO;
import com.onelive.common.model.req.live.LiveColumnCodeReq;
import com.onelive.common.model.req.live.LiveListReq;
import com.onelive.common.model.vo.live.LiveStudioListForIndexVO;
import com.onelive.common.model.vo.live.LiveStudioUserCountVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.mapper.master.live.LiveStudioListMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioListMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.pay.RandomUtil;
import com.onelive.common.utils.upload.AWSS3Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LiveStudioListImpl extends ServiceImpl<LiveStudioListMapper, LiveStudioList>
        implements LiveStudioListService {

    @Resource
    private SlaveLiveStudioListMapper slaveLiveStudioListMapper;

    @Resource
    private LiveGiftLogService liveGiftLogService;

    @Resource
    WebSocketFeignClient wbeSocketFeignClient;

    @Autowired
    private SysParameterService sysParameterService;

    /**
     * 直播间为你推荐的房间，随机查询，且不包含当前直播间
     */
    @Override
    public List<LiveStudioListForIndexVO> getLiveRecommendListVO(String studioNum) {
        List<LiveStudioListForIndexVO> list = slaveLiveStudioListMapper.getLiveRecommendList(
                LoginInfoUtil.getMerchantCode(), LoginInfoUtil.getLang(), studioNum);
        for (LiveStudioListForIndexVO liveStudioListForIndexVO : list) {
            liveStudioListForIndexVO
                    .setViewsNumber(this.onlineUsersCount(liveStudioListForIndexVO.getStudioNum()));
//            liveStudioListForIndexVO
//                    .setStudioThumbImage(AWSS3Util.getAbsoluteUrl(liveStudioListForIndexVO.getStudioThumbImage()));
//            liveStudioListForIndexVO
//                    .setStudioBackground(AWSS3Util.getAbsoluteUrl(liveStudioListForIndexVO.getStudioBackground()));
            Integer studioNum_Heat = (Integer) ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.studioNum_Heat, liveStudioListForIndexVO.getStudioNum());
            liveStudioListForIndexVO.setStudioHeat(studioNum_Heat);
        }
        return list;
    }

    /**
     * 推荐直播间查询，排序处理 获取配置的排序方式 礼物金额， 在线人数， 随机排序， 开播时间
     *
     * @param selectList
     * @param isThisCountry
     * @return
     */
    private LinkedList<LiveStudioUserCountVO> getRecommendByOrder(List<LiveStudioList> selectList,
                                                                  boolean isThisCountry) {
        List<LiveStudioList> notFixedList = selectList;
        if (isThisCountry) {
            // 排除推荐的四个
            List<LiveStudioList> recommendStudioNum = getRecommendStudioNum(LoginInfoUtil.getCountryCode());
            Set<String> recommendStudioNumSet = recommendStudioNum.stream().map(t -> t.getStudioNum())
                    .collect(Collectors.toSet());
            // 过滤出非固定
            notFixedList = selectList.stream().filter(t -> !t.getIsFixed())
                    .collect(Collectors.toList());
            // 过滤出非置底的房间
            notFixedList = notFixedList.stream().filter(t -> t.getIsFirst() != 2)
                    .collect(Collectors.toList());
            // 排除四个推荐
            notFixedList = notFixedList.stream().filter(t -> !recommendStudioNumSet.contains(t.getStudioNum()))
                    .collect(Collectors.toList());
        }

        // 1 在线人数，2礼物金额 ， 3随机排序， 4开播时间
        Integer sortCode = Integer
                .parseInt(sysParameterService.getByCode(SysParameterConstants.LIVE_STUDIO_SORT).getParamValue());

        // 排序的list
        LinkedList<LiveStudioUserCountVO> orderList = new LinkedList<>();
        // 在线人数的排序，用以过滤游戏房间人数最多的前四 （光年推荐）
        List<LiveStudioUserCountVO> memCountList = new LinkedList<>();

        // 对出非固定的房间按照人数进行倒序排序
        for (LiveStudioList live : notFixedList) {
            // 获取每个直播间得真实在线人数
            int score = ApiBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + live.getStudioNum()).intValue();
            memCountList.add(new LiveStudioUserCountVO(live.getStudioNum(), score, live.getGameId()));
            //1 在线人数
            if (sortCode == 1) {
                orderList.add(new LiveStudioUserCountVO(live.getStudioNum(), score));
            }
            // 2礼物金额
            else if (sortCode == 2) {
                // 查询当前直播间的收礼金额
                Object giftTotalObject = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.live_StudioLog_gift,
                        live.getStudioNum());
                int scoreGift = giftTotalObject == null ? 0 : new BigDecimal(giftTotalObject.toString()).intValue();
                orderList.add(new LiveStudioUserCountVO(live.getStudioNum(), scoreGift));
            }
            // 3随机排序
            else if (sortCode == 3) {
                orderList.add(new LiveStudioUserCountVO(live.getStudioNum(),
                        RandomUtil.getRandomOne(0, 999 + notFixedList.size())));
            }
            // 4开播时间
            else if (sortCode == 4) {
                Long startTime = live.getStartTime().getTime() / 1000;
                orderList.add(new LiveStudioUserCountVO(live.getStudioNum(), startTime.intValue()));
            }
        }

        // 根据Score排序，降序
        orderList.sort(Comparator.comparing(LiveStudioUserCountVO::getScore, Comparator.reverseOrder()));

        // 当前国家，排除光年推荐4个，排除4个固定的推荐
        if (isThisCountry) {
            memCountList = memCountList.stream().filter(t -> t.getGameId() != null).collect(Collectors.toList());
            // 根据在线人数排序，降序
            memCountList.sort(Comparator.comparing(LiveStudioUserCountVO::getScore, Comparator.reverseOrder()));
            int endIndex = memCountList.size() > 4 ? 4 : memCountList.size();
            memCountList.subList(0, endIndex);
            // 排除前四个
            Iterator<LiveStudioUserCountVO> it = orderList.iterator();
            while (it.hasNext()) {
                LiveStudioUserCountVO next = it.next();
                Optional<LiveStudioUserCountVO> findFirst = memCountList.stream()
                        .filter(t -> t.getStudioNum().equals(next.getStudioNum())).findFirst();
                if (findFirst.isPresent()) {
                    it.remove();
                }
            }

            // 获取置底的房间号
            List<LiveStudioList> bottomList = selectList.stream().filter(t -> t.getIsFirst() == 2)
                    .collect(Collectors.toList());
            // 根据序号升序
            bottomList.sort(Comparator.comparing(LiveStudioList::getSortNum));
            // 添加到房间列表orderList
            for (LiveStudioList liveStudioList : bottomList) {
                orderList.addLast(new LiveStudioUserCountVO(liveStudioList.getStudioNum(), 0));
            }

            // 获取固定的房间号
            List<LiveStudioList> fixedList = selectList.stream().filter(t -> t.getIsFixed())
                    .collect(Collectors.toList());
            // 根据序号升序,插入到指定的位置
            fixedList.sort(Comparator.comparing(LiveStudioList::getSortNum));
            for (LiveStudioList liveStudioList : fixedList) {
                int addIndex = liveStudioList.getSortNum() - 1 > orderList.size() ? orderList.size()
                        : liveStudioList.getSortNum() - 1;
                orderList.add(addIndex, new LiveStudioUserCountVO(liveStudioList.getStudioNum(), 0));
            }
        }

        return orderList;
    }

    /**
     * 推荐标签直播间列表:根据主播火力值(排除推荐区的四个推荐和正在热播的4个)
     */
    @Override
    public List<LiveStudioListForIndexVO> getRecommendByHeat(LiveListReq req) {
        // 所有在直播的房间
        List<LiveStudioList> selectList = slaveLiveStudioListMapper.getAllOnline();
        String countryCode = LoginInfoUtil.getCountryCode();
        // 先给当前国家的直播间排序
        List<LiveStudioList> thisCountryList = selectList.stream()
                .filter(t -> t.getCountryCode().equals(countryCode)).collect(Collectors.toList());
        List<LiveStudioUserCountVO> orderList = new ArrayList<>(getRecommendByOrder(thisCountryList, true));
        // 非当前国家的直播间排序
        List<LiveStudioList> otherCountryList = selectList.stream()
                .filter(t -> !t.getCountryCode().equals(countryCode)).collect(Collectors.toList());
        orderList.addAll(getRecommendByOrder(otherCountryList, false));

        // 获取前req.getPageSize()条
        orderList = subStudioList(req.getStudioNum(), req.getPageSize(), orderList);
        // 要查询的房间号
        List<String> liveStudioNums = orderList.stream().map(t -> t.getStudioNum()).collect(Collectors.toList());
        // 根据房间号查询房间详细
        List<LiveStudioListForIndexVO> liveByStudioNums = this.getLiveByStudioNums(liveStudioNums);

        List<LiveStudioListForIndexVO> result = new ArrayList<>(liveByStudioNums.size());
        for (LiveStudioUserCountVO liveStudioUserCountVO : orderList) {
            Optional<LiveStudioListForIndexVO> findFirst = liveByStudioNums.stream().filter(t -> t.getStudioNum().equals(liveStudioUserCountVO.getStudioNum())).findFirst();
            boolean present = findFirst.isPresent();
            if (!present) {
                continue;
            }
            LiveStudioListForIndexVO liveStudioListForIndexVO = findFirst.get();
            result.add(liveStudioListForIndexVO);
        }
        return result;
    }

    /**
     * 直播间查询动态分页
     *
     * @param studioNum               起始的直播间
     * @param pageSize                条数
     * @param liveStudioUserCountList 被切割的数组
     */
    private List<LiveStudioUserCountVO> subStudioList(String studioNum, Integer pageSize,
                                                      List<LiveStudioUserCountVO> liveStudioUserCountList) {
        // 截取起始角标
        int start = 0;
        // 从studioNum这条开始，截取pageSize条
        if (StringUtils.isNotEmpty(studioNum)) {
            for (int i = 0; i < liveStudioUserCountList.size(); i++) {
                if (liveStudioUserCountList.get(i).getStudioNum().equals(studioNum)) {
                    start = i + 1;
                    break;
                }
            }
        }
        // 如果传入条数为空，默认查询所有studioNums的直播间
        pageSize = (pageSize == null || pageSize == 0) ? liveStudioUserCountList.size() : pageSize;
        // 截取结束角标
        int end = (start + pageSize) > liveStudioUserCountList.size() ? liveStudioUserCountList.size()
                : (start + pageSize);
        // 查询截取后得房间号列表
        return liveStudioUserCountList.subList(start, end);
    }

    @Override
    public LiveStudioList getByRoomNum(String studioNum) {
        QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveStudioList::getStudioNum, studioNum);
        LiveStudioList liveStudioList = slaveLiveStudioListMapper.selectOne(queryWrapper);
        liveStudioList.setStudioBackground(AWSS3Util.getAbsoluteUrl(liveStudioList.getStudioBackground()));
        liveStudioList.setStudioThumbImage(AWSS3Util.getAbsoluteUrl(liveStudioList.getStudioThumbImage()));
        return liveStudioList;
    }

    /**
     * 用户端查询直播间人数: 人数计算公式 : 50~60 随机数 + (在线人数 * 后台配置的倍数,如果后台没有配置默认 * 1)
     */
    @Override
    public Integer onlineUsersCount(String studioNum) {
        Object object = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.anchor_studioNum, studioNum);
        Integer memCountMultiple = object == null ? 1 : (Integer) object;
        // 真实的用户数
        int userSize = ApiBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + studioNum).intValue();
        int result = RandomUtil.getRandomOne(50, 60) + (userSize * memCountMultiple);
        return result;
    }

    @Override
    public LiveStudioList getByUserId(Long userId) {
        QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveStudioList::getUserId, userId);
        queryWrapper.lambda().eq(LiveStudioList::getStudioStatus, 1);
        LiveStudioList liveStudioList = slaveLiveStudioListMapper.selectOne(queryWrapper);
        return liveStudioList;
    }

    /**
     * 获取当前所有收费房间号
     *
     * @return
     */
    @Override
    public Set<String> getChargeRoom() {
        QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().isNotNull(true, LiveStudioList::getProductId);
        queryWrapper.lambda().eq(LiveStudioList::getStudioStatus, 1);
        List<LiveStudioList> selectList = slaveLiveStudioListMapper.selectList(queryWrapper);
        return selectList.stream().map(t -> t.getStudioNum()).collect(Collectors.toSet());
    }

    @Override
    public List<LiveStudioListForIndexVO> getLiveByStudioNums(Collection<String> liveStudioNums) {
        if (CollectionUtils.isEmpty(liveStudioNums)) {
            return Collections.emptyList();
        }
        List<LiveStudioListForIndexVO> liveByStudioNums = slaveLiveStudioListMapper.getLiveByStudioNums(liveStudioNums,
                LoginInfoUtil.getLang());
        // 设置图片 在线人数，火力值，并且按照浏览时间排序
        for (LiveStudioListForIndexVO liveStudioList : liveByStudioNums) {
            liveStudioList.setViewsNumber(this.onlineUsersCount(liveStudioList.getStudioNum()));
//            liveStudioList.setStudioThumbImage(AWSS3Util.getAbsoluteUrl(liveStudioList.getStudioThumbImage()));
//            liveStudioList.setStudioBackground(AWSS3Util.getAbsoluteUrl(liveStudioList.getStudioBackground()));
            Object studioNum_Heat = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.studioNum_Heat, liveStudioList.getStudioNum());
            liveStudioList.setStudioHeat(studioNum_Heat == null ? 0 : (Integer) studioNum_Heat);
        }
        return liveByStudioNums;
    }

    /**
     * 通用根据房间在线人数查询直播间信息
     *
     * @param studioNum
     * @param pageSize
     * @param orderBy   根据排序的字段 1：在线人数 2：火力值 3：sortNum顺序，越小越靠前
     * @return
     */
    @Override
    public List<LiveStudioListForIndexVO> studioListByOrder(List<LiveStudioList> liveStudioListList, String studioNum,
                                                            Integer pageSize, Integer orderBy) {
        if (CollectionUtils.isEmpty(liveStudioListList)) {
            return Collections.emptyList();
        }

        String countryCode = LoginInfoUtil.getCountryCode();
        // 获取当前国家的直播间
        List<LiveStudioList> thisCountryList = liveStudioListList.stream()
                .filter(t -> t.getCountryCode().equals(countryCode)).collect(Collectors.toList());
        List<LiveStudioUserCountVO> liveStudioUserCountList = getOrderStudioNums(thisCountryList, orderBy);

        // 获取非当前国家的直播间
        List<LiveStudioList> otherCountryList = liveStudioListList.stream()
                .filter(t -> !t.getCountryCode().equals(countryCode)).collect(Collectors.toList());
        liveStudioUserCountList.addAll(getOrderStudioNums(otherCountryList, orderBy));

        // 动态截取
        liveStudioUserCountList = subStudioList(studioNum, pageSize, liveStudioUserCountList);
        // 要查询的房间号
        List<String> liveStudioNums = liveStudioUserCountList.stream().map(t -> t.getStudioNum())
                .collect(Collectors.toList());
        // 根据房间号查询房间详细
        List<LiveStudioListForIndexVO> liveByStudioNums = this.getLiveByStudioNums(liveStudioNums);
        List<LiveStudioListForIndexVO> result = new ArrayList<>(liveByStudioNums.size());
        // 设置图片 在线人数，火力值，并且按照浏览时间排序
        for (String liveStudioNum : liveStudioNums) {
            Optional<LiveStudioListForIndexVO> findFirst = liveByStudioNums.stream()
                    .filter(t -> t.getStudioNum().equals(liveStudioNum)).findFirst();
            boolean present = findFirst.isPresent();
            if (!present) {
                continue;
            }
            LiveStudioListForIndexVO liveStudioListForIndexVO = findFirst.get();
            result.add(liveStudioListForIndexVO);
        }
        return result;
    }

    /**
     * 房间号排序，根据orderBy排序类型
     * 
     *
     * @param studioNumList
     * @param orderBy 1:在线人数 2：活力值 3：sortNum 顺序排序，越小越靠前
     * @return
     */
    private List<LiveStudioUserCountVO> getOrderStudioNums(List<LiveStudioList> studioNumList, Integer orderBy) {
        // 直播间人数list，排序用
        List<LiveStudioUserCountVO> liveStudioUserCountList = new ArrayList<>(studioNumList.size());
        for (LiveStudioList liveStudioList : studioNumList) {
            int score = 0;
            if (orderBy == 1) {
                // 获取每个直播间得真是在线人数
                score = ApiBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + liveStudioList.getStudioNum()).intValue();
            } else if (orderBy == 2) {
                Object studioNum_Heat = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.studioNum_Heat, liveStudioList.getStudioNum());
                // 火力值
                score = studioNum_Heat == null ? 0 : (Integer) studioNum_Heat;
            } else if (orderBy == 3) {
            	// 根据sortNum 顺序排序，越小越靠前
            	 score = Integer.MAX_VALUE - liveStudioList.getSortNum();
            }
            liveStudioUserCountList.add(new LiveStudioUserCountVO(liveStudioList.getStudioNum(), score));
        }
        // 根据score排序，降序
        liveStudioUserCountList.sort(Comparator.comparing(LiveStudioUserCountVO::getScore, Comparator.reverseOrder()));
        return liveStudioUserCountList;
    }

    /**
     * 热门栏目直播间,根据人数
     */
    @Override
    public List<LiveStudioListForIndexVO> getHotList(LiveColumnCodeReq req) {
        // 查询所有在线直播间，
        QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveStudioList::getStudioStatus, 1);
        List<LiveStudioList> selectList = slaveLiveStudioListMapper.selectList(queryWrapper);
        return this.studioListByOrder(selectList, req.getStudioNum(), req.getPageSize(), 1);
    }

    /**
     * 与用户同一国家的直播间，根据在线人数排序
     * <p>
     * other:查询用户当前国家以外的直播间 ，null：不限制国家"
     */
    @Override
    public List<LiveStudioListForIndexVO> getNearbyList(LiveColumnCodeReq req) {
        String countryCode = req.getCountryCode();

        QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
        // 查询对应的国家直播间
        if (StringUtils.isNotEmpty(countryCode)) {
            // 0：不限制国家,即：不设置条件
            if ("other".equals(countryCode)) {
                queryWrapper.lambda().ne(LiveStudioList::getCountryCode, countryCode);
            } else {
                queryWrapper.lambda().eq(LiveStudioList::getCountryCode, countryCode);
            }
        }

        queryWrapper.lambda().eq(LiveStudioList::getStudioStatus, 1);
        List<LiveStudioList> selectList = slaveLiveStudioListMapper.selectList(queryWrapper);
        return this.studioListByOrder(selectList, req.getStudioNum(), req.getPageSize(), 1);
    }

    /**
     * 黄播房间，并且根据在线人数排序
     */
    @Override
    public List<LiveStudioListForIndexVO> getStarList(LiveColumnCodeReq req) {
        // 获取所有开播中得黄播直播间
        QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveStudioList::getStudioStatus, 1);
        queryWrapper.lambda().eq(LiveStudioList::getColour, 1);
        List<LiveStudioList> selectList = slaveLiveStudioListMapper.selectList(queryWrapper);
        return this.studioListByOrder(selectList, req.getStudioNum(), req.getPageSize(), 1);
    }

    /**
     * 开播中gameid不为空得 ,根据在线人数排序
     */
    @Override
    public List<LiveStudioListForIndexVO> getGameList(LiveColumnCodeReq req) {
        // 获取所有开播中gameid不为空得
        QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveStudioList::getStudioStatus, 1);
        queryWrapper.lambda().isNotNull(LiveStudioList::getGameId);
        List<LiveStudioList> selectList = slaveLiveStudioListMapper.selectList(queryWrapper);
        return this.studioListByOrder(selectList, req.getStudioNum(), req.getPageSize(), 1);
    }

    /**
     * 光年推荐(最多4个，人数最多的的游戏直播间)
     */
    @Override
    public List<LiveStudioListForIndexVO> getPromotion() {
        // 获取所有开播中gameid不为空得
        QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveStudioList::getStudioStatus, 1);
        queryWrapper.lambda().isNotNull(LiveStudioList::getGameId);
        List<LiveStudioList> selectList = slaveLiveStudioListMapper.selectList(queryWrapper);
        int size = selectList.size() > 4 ? 4 : selectList.size();
        return this.studioListByOrder(selectList, null, size, 1);
    }

    /**
     * 获取当前用户关注的，并且在直播的直播间
     */
    @Override
    public List<LiveStudioListForIndexVO> getLiveFocusListVO(LiveListReq req) {
        // 获取当前用户关注的，并且在直播的直播间num
        List<LiveStudioList> selectList = slaveLiveStudioListMapper.focusStudioNums(LoginInfoUtil.getUserId());
        return this.studioListByOrder(selectList, req.getStudioNum(), req.getPageSize(), 1);
    }

    /**
     * 关注页签推荐的主播，排除已经关注房间,随机查询十个
     */
    @Override
    public List<LiveStudioListForIndexVO> getLiveFocusRecommendList() {
        List<LiveStudioListForIndexVO> liveFocusRecommendList = slaveLiveStudioListMapper.getLiveFocusRecommendList(
                LoginInfoUtil.getMerchantCode(), LoginInfoUtil.getLang(), LoginInfoUtil.getUserId());
        for (LiveStudioListForIndexVO liveStudioList : liveFocusRecommendList) {
//            liveStudioList.setStudioBackground(AWSS3Util.getAbsoluteUrl(liveStudioList.getStudioBackground()));
//            liveStudioList.setStudioThumbImage(AWSS3Util.getAbsoluteUrl(liveStudioList.getStudioThumbImage()));
            liveStudioList.setViewsNumber(onlineUsersCount(liveStudioList.getStudioNum()));
        }
        return liveFocusRecommendList;
    }

    @Override
    public List<LiveStudioList> getByQueryWrapper(QueryWrapper<LiveStudioList> queryWrapper) {
        return slaveLiveStudioListMapper.selectList(queryWrapper);
    }

    /**
     * 推荐区的固定四个推荐直播
     *
     * @return
     */
    @Override
    public List<LiveStudioListForIndexVO> getRecommend(String countryCode) {
        List<LiveStudioListForIndexVO> cacheData = ApiBusinessRedisUtils.get(RedisCacheableKeys.ROOM_RECOMMEND + "countryCode_" + countryCode);
        if (!CollectionUtils.isEmpty(cacheData)) {
            return cacheData;
        }
        // 获取推荐的房间号
        List<LiveStudioList> getRecommendStudioNum = this.getRecommendStudioNum(countryCode);
        List<LiveStudioListForIndexVO> liveStudioListVoList = this.studioListByOrder(getRecommendStudioNum, null, 4, 3);
        ApiBusinessRedisUtils.set(RedisCacheableKeys.ROOM_RECOMMEND + "countryCode_" + countryCode, liveStudioListVoList);
        return liveStudioListVoList;
    }

    /**
     * 获取推荐的房间号,推荐区不够四个，获取最新开播的房间号
     *
     * @return
     */
    @Override
    public List<LiveStudioList> getRecommendStudioNum(String countryCode) {
        List<LiveStudioList> cacheData = ApiBusinessRedisUtils.get(RedisCacheableKeys.ROOM_RECOMMEND_STUDIONUM + "countryCode_" + countryCode);
        if (!CollectionUtils.isEmpty(cacheData)) {
            return cacheData;
        }

        QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<LiveStudioList>();
        // 推荐排序字段默认 0:一般排序 -1:推荐优先 2:置底
        queryWrapper.lambda().eq(LiveStudioList::getIsFirst, -1);
        queryWrapper.lambda().eq(LiveStudioList::getStudioStatus, 1);
        queryWrapper.lambda().eq(LiveStudioList::getCountryCode, countryCode);
        queryWrapper.lambda().eq(LiveStudioList::getMerchantCode, LoginInfoUtil.getMerchantCode());
        queryWrapper.lambda().orderByDesc(LiveStudioList::getSortNum).last("LIMIT 4");
        List<LiveStudioList> list = slaveLiveStudioListMapper.selectList(queryWrapper);
        // 四个推荐，如果不足，展示时间最新的直播间填充
        int fishCount = 4 - list.size();
        // 如果没有四个，获取本国家最新开播的 4 - list.size个
        if (fishCount > 0) {
            Set<String> existingStudioNum = list.stream().map(t -> t.getStudioNum()).collect(Collectors.toSet());
            QueryWrapper<LiveStudioList> queryWrapperFish = new QueryWrapper<LiveStudioList>();
            queryWrapperFish.lambda().eq(LiveStudioList::getStudioStatus, 1);
            queryWrapperFish.lambda().eq(LiveStudioList::getCountryCode, countryCode);
            if (!CollectionUtils.isEmpty(existingStudioNum)) {
            	queryWrapperFish.lambda().notIn(LiveStudioList::getStudioNum, existingStudioNum);
            }
            queryWrapperFish.lambda().eq(LiveStudioList::getMerchantCode, LoginInfoUtil.getMerchantCode());
            queryWrapperFish.lambda().orderByDesc(LiveStudioList::getStartTime).last("LIMIT " + fishCount);
            list.addAll(slaveLiveStudioListMapper.selectList(queryWrapperFish));
            int thisCountryFishCount = 4 - list.size();
            // 如果当前国家不够 ,再用其他国家的
            if (thisCountryFishCount > 0) {
                existingStudioNum = list.stream().map(t -> t.getStudioNum()).collect(Collectors.toSet());
                QueryWrapper<LiveStudioList> queryWrapperFishOtherCountry = new QueryWrapper<LiveStudioList>();
                queryWrapperFishOtherCountry.lambda().eq(LiveStudioList::getStudioStatus, 1);
                queryWrapperFishOtherCountry.lambda().eq(LiveStudioList::getMerchantCode,
                        LoginInfoUtil.getMerchantCode());
                if (!CollectionUtils.isEmpty(existingStudioNum)) {
                	queryWrapperFish.lambda().notIn(LiveStudioList::getStudioNum, existingStudioNum);
                }
                queryWrapperFish.lambda().notIn(LiveStudioList::getStudioNum, existingStudioNum);
                queryWrapperFishOtherCountry.lambda().orderByDesc(LiveStudioList::getStartTime)
                        .last("LIMIT " + thisCountryFishCount);
                list.addAll(slaveLiveStudioListMapper.selectList(queryWrapperFishOtherCountry));
            }
        }

        ApiBusinessRedisUtils.set(RedisCacheableKeys.ROOM_RECOMMEND_STUDIONUM + "countryCode_" + countryCode, list);
        return list;
    }

    /**
     * 推荐列表查询
     * @param req
     * @return
     */
    @Override
    public List<LiveStudioListForIndexVO> getRecommendByHeatNew(LiveListReq req) {
        //1.查询所有直播数据
//        QueryWrapper<LiveStudioList> queryWrapper=new QueryWrapper<>();
//        queryWrapper.lambda().eq(LiveStudioList::getStudioStatus,1);
//        List<LiveStudioList> liveList = slaveLiveStudioListMapper.selectList(queryWrapper);
        List<LiveStudioListForIndexDTO> list = slaveLiveStudioListMapper.getRecommendByHeatNew(LoginInfoUtil.getLang());
        if(CollectionUtil.isNotEmpty(list)){
//            List<LiveStudioListForIndexDTO> list = BeanCopyUtil.copyCollection(liveList,LiveStudioListForIndexDTO.class);
            List<LiveStudioListForIndexDTO> thisCountryList = new LinkedList<>();
            List<LiveStudioListForIndexDTO> otherCountryList = new LinkedList<>();
            String countryCode = LoginInfoUtil.getCountryCode();
            for(LiveStudioListForIndexDTO live:list){
                // 火力值
                Object studioNum_Heat = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.studioNum_Heat, live.getStudioNum());
                int heat = studioNum_Heat == null ? 0 : (Integer) studioNum_Heat;
                //2.填充热度数据
                live.setStudioHeat(heat);
                // 获取每个直播间的真实在线人数
                int viewsNumber = ApiBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + live.getStudioNum()).intValue();
                live.setViewsNumber(viewsNumber);
                // 查询当前直播间的收礼金额
                Object giftTotalObject = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.live_StudioLog_gift, live.getStudioNum());
                int scoreGift = giftTotalObject == null ? 0 : new BigDecimal(giftTotalObject.toString()).intValue();
                //3.填充礼物数量数据
                live.setGiftAmount(scoreGift);

                //4.数据区分本国和外国
                if(countryCode.equals(live.getCountryCode())){
                    //如果是推荐的, 排除掉
                    if(live.getIsFirst()!=-1){
                        //5.过滤推荐和光年推荐4个
                        thisCountryList.add(live);
                    }
                }else{
                    otherCountryList.add(live);
                }
            }
            //对本国数据进行处理
            if(CollectionUtil.isNotEmpty(thisCountryList)){
                thisCountryList = sortByConfig(thisCountryList,true);
            }
            //对外国数据进行处理
            if(CollectionUtil.isNotEmpty(otherCountryList)){
                otherCountryList = sortByConfig(otherCountryList,false);
            }

            //合并数据
            thisCountryList.addAll(otherCountryList);

             //8.根据参数截取数据
            if(CollectionUtil.isNotEmpty(thisCountryList) && StringUtils.isNotBlank(req.getStudioNum())){
                int index = -1;
                for(int i=0;i<thisCountryList.size();i++){
                    LiveStudioListForIndexDTO dto = thisCountryList.get(i);
                    if(dto.getStudioNum().equals(req.getStudioNum())){
                        index = i+1;
                        break;
                    }
                }
                if(index>-1){
                    if(index==thisCountryList.size()){
                        thisCountryList = new LinkedList<>();
                    }else{
                    // 如果传入条数为空，默认查询所有studioNums的直播间
                    int pageSize = (req.getPageSize() == null || req.getPageSize() == 0) ? thisCountryList.size() : req.getPageSize();
                    // 截取结束角标
                    int end = (index + pageSize) > thisCountryList.size() ? thisCountryList.size() : (index + pageSize);
                        thisCountryList=thisCountryList.subList(index,end);
                    }
                }
            }
            return BeanCopyUtil.copyCollection(thisCountryList,LiveStudioListForIndexVO.class);
        }
        return new LinkedList<>();
    }

    /**
     * 根据系统配置, 进行排序
     * @param list
     */
    private List<LiveStudioListForIndexDTO> sortByConfig(List<LiveStudioListForIndexDTO> list, boolean isThisCountry) {
        //结果集
        List<LiveStudioListForIndexDTO> resultList = new LinkedList<>();
        if(CollectionUtil.isEmpty(list)){
            return resultList;
        }
        // 1 在线人数，2礼物金额 ， 3随机排序， 4开播时间
        Integer sortType = Integer.parseInt(sysParameterService.getByCode(SysParameterConstants.LIVE_STUDIO_SORT).getParamValue());
        //推荐数据
        List<LiveStudioListForIndexDTO> firstList = new LinkedList<>();
        //固定数据
        List<LiveStudioListForIndexDTO> fixedList = new LinkedList<>();
        //置底数据
        List<LiveStudioListForIndexDTO> bottomList = new LinkedList<>();
        //光年数据
        List<LiveStudioListForIndexDTO> gnList = new LinkedList<>();
        //其他数据
        List<LiveStudioListForIndexDTO> otherList = new LinkedList<>();
        for(LiveStudioListForIndexDTO dto : list){
            if(dto.getIsFixed()){
                fixedList.add(dto);
            }else if(dto.getIsFirst()==-1){
                firstList.add(dto);
            }else if(dto.getIsFirst()==2){
                bottomList.add(dto);
            }else if(dto.getGameId()!=null){
                gnList.add(dto);
            }else{
                otherList.add(dto);
            }
        }


        //如果是本国数据, 推荐数据不要, 光年数据前4不要
        if(!isThisCountry){
            resultList.addAll(firstList);
            otherList.addAll(gnList);
        }else{
            if(CollectionUtil.isNotEmpty(gnList) && gnList.size()>4){
                //排序 人数降序
                gnList.sort(Comparator.comparing(LiveStudioListForIndexDTO::getViewsNumber,Comparator.reverseOrder()));
                gnList = gnList.subList(0,4);
                otherList.addAll(gnList);
            }
        }


        //6.根据后台配置对其他数据进行排序: 1 在线人数，2礼物金额 ， 3随机排序， 4开播时间
        if(sortType==1){
            otherList.sort(Comparator.comparing(LiveStudioListForIndexDTO::getViewsNumber, Comparator.reverseOrder()));
        }else if(sortType==2){
            otherList.sort(Comparator.comparing(LiveStudioListForIndexDTO::getGiftAmount, Comparator.reverseOrder()));
        }else if(sortType==3){
            Collections.shuffle(otherList);
        }else if(sortType==4){
            otherList.sort(Comparator.comparing(LiveStudioListForIndexDTO::getStartTime, Comparator.reverseOrder()));
        }
        resultList.addAll(otherList);
        resultList.addAll(bottomList);
        //7.插入固定位置的直播数据
        if(CollectionUtil.isNotEmpty(fixedList)){
            fixedList.sort(Comparator.comparing(LiveStudioListForIndexDTO::getSortNum));
            for(LiveStudioListForIndexDTO live:fixedList){
                Integer index = live.getSortNum()-1;
                int addIndex = index > resultList.size() ? resultList.size() : index;
                resultList.add(addIndex,live);
            }
        }

        //设置假数据
        if(CollectionUtil.isNotEmpty(resultList)){
            for (LiveStudioListForIndexDTO dto : resultList){
                dto.setViewsNumber(onlineUsersCount(dto.getStudioNum()));
            }
        }
        return resultList;
    }

}
