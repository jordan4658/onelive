package com.onelive.manage.modules.sys.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.MsgBusinessDto;
import com.onelive.common.model.req.sys.SysShortMsgConfigReq;
import com.onelive.common.model.req.sys.SysShortMsgConfigUpdateReq;
import com.onelive.common.model.req.sys.SysShortMsgSwitchReq;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.model.vo.sys.SysShortMsgConfigVO;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.mybatis.entity.SysShortMsgConfig;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.service.sys.SysShortMsgConfigService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SysShortMsgConfigBusiness {

	@Resource
	private SysShortMsgConfigService service;

	@Resource
	private SysParameterService sysParameterService;

	/**
	 * 分页查询
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<SysShortMsgConfigVO> getList(Integer pageNum, Integer pageSize) {
		PageInfo<SysShortMsgConfig> pageInfo = service.getList(pageNum, pageSize);
		return PageInfoUtil.pageInfo2DTO(pageInfo, SysShortMsgConfigVO.class);
	}

	public void save(SysShortMsgConfigReq req, LoginUser loginUser) {
		if (loginUser == null) {
			throw new BusinessException(401, "没有权限操作");
		}
		if (StrUtil.isBlank(req.getMsgPlatform())) {
			throw new BusinessException("短信平台不能为空");
		}
		if (req.getIsFrozen() == null) {
			throw new BusinessException("状态不能为空");
		}
		SysShortMsgConfig p = new SysShortMsgConfig();
		BeanUtil.copyProperties(req, p);
		p.setCreateUser(loginUser.getAccLogin());
		service.save(p);
	}

	/**
	 * 编辑
	 * 
	 * @param req
	 * @return
	 * @Note
	 */
	public void update(SysShortMsgConfigUpdateReq req, LoginUser loginUser) {
		if (loginUser == null) {
			throw new BusinessException(401, "没有权限操作");
		}
		if (req.getId() == null) {
			throw new BusinessException("主键不能为空");
		}
		SysShortMsgConfig sw = service.getById(req.getId());
		if (sw == null) {
			throw new BusinessException("找不到对应的信息");
		}
		if (req.getIsFrozen() == null) {
			throw new BusinessException("状态不能为空");
		}
		SysShortMsgConfig p = new SysShortMsgConfig();
		p.setUpdateUser(loginUser.getAccLogin());
		BeanUtil.copyProperties(req, p);
		service.updateById(p);
	}

	/**
	 * 根据id获取信息 <b>Description:</b><br>
	 * 
	 * @param id
	 * @return
	 * @Note
	 */
	public SysShortMsgConfigVO getById(Long id) {
		if (id == null) {
			throw new BusinessException("主键id不能为空");
		}
		SysShortMsgConfig sw = service.getById(id);
		if (sw == null) {
			return null;
		}
		SysShortMsgConfigVO vo = new SysShortMsgConfigVO();
		BeanUtils.copyProperties(sw, vo);
		return vo;
	}

	/**
	 * 删除
	 *
	 * @param id
	 */
	public void delete(Long id, String account) {
		service.delete(id, account);
	}

	/**
	 * 		短信开关更新
	 * 
	 * @param sysShortMsgSwitchReq
	 */
	public void switchStatus(SysShortMsgSwitchReq sysShortMsgSwitchReq) {
		SysParameter byCode = sysParameterService.getByCode(SysParameterConstants.MSG_SWITCH);
		byCode.setParamValue(String.valueOf(sysShortMsgSwitchReq.getIsOpen()));
		sysParameterService.updateById(byCode);
	}

	/**
	 * 
	 * 		查询短信服务商
	 * @return
	 */
	public List<MsgBusinessDto> getBusiness() {
		List<SysParameterListVO> listByType = sysParameterService.getListByType(SysParameterConstants.MSG_SERVER);
		List<MsgBusinessDto> list = new ArrayList<MsgBusinessDto>(listByType.size());
		for (SysParameterListVO sysParameterListVO : listByType) {
			MsgBusinessDto msgBusinessDto = new MsgBusinessDto();
			msgBusinessDto.setName(sysParameterListVO.getParamName());
			msgBusinessDto.setCode(sysParameterListVO.getParamCode());
			list.add(msgBusinessDto);
		}
		return list;
	}
}
