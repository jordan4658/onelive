package com.onelive.manage.service.mem.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
import com.onelive.common.model.req.mem.family.MemFamilyListReq;
import com.onelive.common.model.req.mem.family.MemFamilySaveReq;
import com.onelive.common.model.vo.mem.MemFamilyListVO;
import com.onelive.common.mybatis.entity.MemFamily;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.mapper.master.mem.MemFamilyMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemFamilyMapper;
import com.onelive.common.utils.others.SecurityUtils;
import com.onelive.manage.service.mem.MemFamilyService;
import com.onelive.manage.service.mem.MemUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 家族表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
public class MemFamilyServiceImpl extends ServiceImpl<MemFamilyMapper, MemFamily> implements MemFamilyService {

	@Resource
	private MemFamilyMapper memFamilyMapper;
	
	@Resource
	private SlaveMemFamilyMapper slaveMemFamilyMapper;

	@Resource
	private MemUserService memUserService;

	/**
	 * 后台多条件查询 
	 */
	@Override
	public PageInfo<MemFamilyListVO> getList(MemFamilyListReq req) {
		return PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(()->slaveMemFamilyMapper.getList(req));
	}

	@Override
	@Transactional
	public int saveFamily(MemFamilySaveReq req) throws Exception {
		// 1.调用注册用户接口,保存到mem_user表
		UserAnchorDTO userAnchorDTO = new UserAnchorDTO();
		BeanUtil.copyProperties(req, userAnchorDTO);
		userAnchorDTO.setUserType(3);
		MemUser memUser = memUserService.saveUserAnchor(userAnchorDTO);

		MemFamily memUserAnchor = new MemFamily();
		// 2.保存到mem_user_anchor表
		BeanUtil.copyProperties(req, memUserAnchor);
		memUserAnchor.setUserId(memUser.getId());
		this.save(memUserAnchor);
		return 1;
	}

	@Override
	@Transactional
	public int updateFamily(MemFamilySaveReq family) {
		// 1.更新mem_user表
		MemUser memUser = new MemUser();
		BeanUtil.copyProperties(family, memUser);
		memUser.setId(family.getUserId());
		memUser.setUserType(3);
		// 更新密码
		MemUser dbUser = memUserService.getById(family.getUserId());
		memUser.setPassword(SecurityUtils.MD5SaltEncrypt(family.getPassword(), dbUser.getSalt()));
		memUserService.updateById(memUser);

		// 2.更新mem_user_anchor表
		MemFamily memFamily = new MemFamily();
		BeanUtil.copyProperties(family, memFamily);
		this.updateById(memFamily);
		return 1;
	}

}
