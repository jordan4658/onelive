package com.onelive.manage.modules.mem.business;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorListReq;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSaveReq;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSearchReq;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorStatusReq;
import com.onelive.common.model.vo.mem.AnchorForAccVO;
import com.onelive.common.model.vo.mem.MemUserAnchorVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserAnchor;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.live.LiveStudioListService;
import com.onelive.manage.service.mem.MemUserAnchorService;
import com.onelive.manage.service.mem.MemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;


@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemAnchorBusiness {


	@Resource
	private MemUserService memUserService;
	
    @Resource
    private MemUserAnchorService memUserAnchorService;
    
    @Autowired
	private LiveStudioListService liveStudioListService;

    public PageInfo<MemUserAnchorVO> getList(MemUserAnchorListReq req) {
        return memUserAnchorService.getList(req);
    }

    public void save(MemUserAnchorSaveReq req, LoginUser loginUser) throws Exception {
        if (StrUtil.isBlank(req.getNickName())) {
            throw new BusinessException("昵称不能为空");
        }
        if (StrUtil.isBlank(req.getPassword())) {
            throw new BusinessException("登录密码不能为空");
        }
        if (req.getGiftRatio() == null) {
            throw new BusinessException("礼物抽成比例不能为空");
        }
        if (StringUtils.isEmpty(req.getCountryCode())) {
        	throw new BusinessException("所属地区不能为空");
        }
        if (req.getFocusAward() != null && req.getFocusAward().compareTo(new BigDecimal("10")) == 1) {
        	throw new BusinessException("关注奖励不能大于10银豆");
        }
        if (req.getGiftRatio() != null && req.getGiftRatio().compareTo(new BigDecimal("100")) == 1) {
   		 	throw new BusinessException("礼物分成比例不能大于100");
   	 	}
        
        req.setCreatedBy(loginUser.getAccLogin());
        memUserAnchorService.save(req);
    }
    
    /**
     * 	主播编辑
    * @param req
    * @return
    * @Note
     */
    public void update(MemUserAnchorSaveReq req, LoginUser loginUser) {
    	 if (req.getId() == null) {
             throw new BusinessException("主键不能为空");
         }
    	 MemUserAnchor memUserAnchor =  memUserAnchorService.getById(req.getId());  
    	 if (memUserAnchor == null) {
             throw new BusinessException("找不到对应的主播信息");
         }
    	 if (req.getFocusAward() != null && req.getFocusAward().compareTo(new BigDecimal("10")) == 1) {
         	 throw new BusinessException("关注奖励不能大于10银豆");
         }
    	 if (req.getGiftRatio() != null && req.getGiftRatio().compareTo(new BigDecimal("100")) == 1) {
    		 throw new BusinessException("礼物分成比例不能大于100");
    	 }
    	 req.setUpdateBy(loginUser.getAccLogin());
         memUserAnchorService.update(req);
    }

	/**
	 * 	启用 禁用 主播
	 * @param req
	 * @param loginUser
	 */
	public void enableOrDisable(MemUserAnchorStatusReq req, LoginUser loginUser) {
    	 if (req.getId() == null) {
             throw new BusinessException("主键不能为空");
         }
    	 MemUserAnchor memUserAnchor =  memUserAnchorService.getById(req.getId());  
    	 if (memUserAnchor == null) {
             throw new BusinessException("找不到对应的主播信息");
         }
    	 MemUser memUser = memUserService.getById(memUserAnchor.getUserId());
    	 String countryCode = LoginInfoUtil.getCountryCode();
 		 if (loginUser.getRoleId() != 1 && !memUser.getRegisterCountryCode().equals(countryCode)) {
 			 throw new BusinessException("只能操作当前登录用户国家对应的主播");
 		 }
    	 MemUser memUserUpdate = new MemUser();
    	 memUserUpdate.setId(memUser.getId());
    	 memUserUpdate.setIsFrozen(req.getIsFrozen());
    	 memUserUpdate.setUserType(2);
    	 memUserService.updateById(memUserUpdate);
    	 if (req.getIsFrozen()) {
    		 liveStudioListService.breakShow(memUser.getId());
    	 }
	}

	/**
	 * 根据用户账号查询主播信息
	 * @param req
	 * @return
	 */
	public AnchorForAccVO getByAcc(MemUserAnchorSearchReq req) {
		if(req==null || StrUtil.isBlank(req.getUserAccount())){
			throw new BusinessException(StatusCode.PARAM_ERROR);
		}

		MemUser queryByAccount = memUserService.queryByAccount(req.getUserAccount());
		if (queryByAccount == null || queryByAccount.getUserType() != 2) {
			throw new BusinessException("找不到对应的主播信息");
		}
		if (queryByAccount.getIsFrozen()) {
			throw new BusinessException("当前主播账号被冻结！");
		}
		
		LiveStudioList liveStudioList = liveStudioListService.getByUserId(queryByAccount.getId());
		Integer studioStatus = liveStudioList == null ? 0: liveStudioList.getStudioStatus();
		if(studioStatus==1){
			throw new BusinessException("该主播已经在直播了!");
		}
		AnchorForAccVO result = new AnchorForAccVO();
		result.setUserId(queryByAccount.getId());
		result.setStudioStatus(studioStatus);
		return result;
	}
    
}
    