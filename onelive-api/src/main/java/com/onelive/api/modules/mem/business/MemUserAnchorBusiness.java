package com.onelive.api.modules.mem.business;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.onelive.api.service.mem.MemFocusUserService;
import com.onelive.api.service.mem.MemLevelVipService;
import com.onelive.api.service.mem.MemUserAnchorService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.api.service.platform.LiveGiftLogService;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.constants.webSocket.WebSocketRedisKeys;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.live.LiveAnchorDetailVO;
import com.onelive.common.model.vo.live.LiveAnchorVO;
import com.onelive.common.mybatis.entity.MemLevelVip;
import com.onelive.common.utils.upload.AWSS3Util;

@Component
public class MemUserAnchorBusiness {

    @Resource
    private MemUserAnchorService memUserAnchorService;
    
    @Resource
    private MemUserService memUserService;
    
    @Resource
    private MemFocusUserService memFocusUserService;
    
    @Resource
    private MemLevelVipService memLevelVipService;
    
    @Resource
    private LocaleMessageSourceService localeMessageSourceService;
    
    @Resource
	private SysParameterService sysParameterService;
    
	@Resource
	private LiveGiftLogService liveGiftLogService;


    /**
     *  获取直播间头部主播信息
      * @param studioId
     * @return
     */
	public LiveAnchorVO getAnchorInfo(String studioNum) {
		// 根据直播间num查询主播的简单信息
		LiveAnchorVO vo = memUserAnchorService.getAnchorInfoByStudioNum(studioNum);
		return vo;
	}

    /**
     * 		获取主播名片
     * @param anchorId
     * @return
     */
    public ResultInfo<LiveAnchorDetailVO> getAnchorDetailInfo(String studioNum){
    	LiveAnchorDetailVO vo = memUserAnchorService.getAnchorInfoDetailByStudioNum(studioNum);
//    	BigDecimal targetMoney = new BigDecimal(sysParameterService.getByCode(SysParameterConstants.LIVE_CONFIG_ANCHOR_CARD).getParamValue());
    	// 查询当前用户对主播的总送礼金额
//		BigDecimal realMoney = liveGiftLogService.selectUserSumByhostId(LoginInfoUtil.getUserId(), vo.getUserId());
		
		// 如果条件值大于实际赠送金额 TODO 二期
//		if (targetMoney.compareTo(realMoney) == 1) {
//			return ResultInfo.getInstance(StatusCode.UNSATISFY_GET_ANCHOR);
//		}
		
        //获取主播用户信息
        if(StringUtils.isBlank(vo.getRemark())){
            vo.setRemark(localeMessageSourceService.getMessage("LIVE_REMARK_BLANK"));
        }else{
            vo.setRemark(vo.getRemark());
        }
        
        //获取等级信息
        MemLevelVip vip = memLevelVipService.getVipLevelInfo(vo.getLevel());
        if(vip != null){
            vo.setLevelName(vip.getLevelName());
            vo.setLevelIcon(AWSS3Util.getAbsoluteUrl(vip.getLevelIcon()));
        }
        Integer studioNum_Heat = (Integer) ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.studioNum_Heat, studioNum);
        vo.setFirepower(studioNum_Heat);
        return ResultInfo.ok(vo);
    }

}
