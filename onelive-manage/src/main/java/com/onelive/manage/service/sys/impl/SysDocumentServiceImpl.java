package com.onelive.manage.service.sys.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysDocument;
import com.onelive.common.mybatis.entity.SysHelpInfo;
import com.onelive.common.mybatis.mapper.master.sys.SysDocumentMapper;
import com.onelive.manage.service.sys.SysDocumentService;

/**
 * <p>
 * 文章管理表 服务实现类
 * </p>
 *
 */
@Service
public class SysDocumentServiceImpl extends ServiceImpl<SysDocumentMapper, SysDocument> implements SysDocumentService {

	@Override
	public void updateStatus(Long id, String account) {
		// TODO 
		 UpdateWrapper<SysDocument> wrapper = new UpdateWrapper<>();
	        wrapper.lambda().set(SysDocument::getIsDelete, true)
	                .set(SysDocument::getUpdateUser, account)
	                .eq(SysDocument::getId, id);
	        this.update(null, wrapper);
	}
}
