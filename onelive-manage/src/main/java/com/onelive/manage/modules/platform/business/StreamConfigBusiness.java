package com.onelive.manage.modules.platform.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.CDNDto;
import com.onelive.common.model.dto.platform.SysStreamConfigDto;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.SysStreamConfig;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.platform.SysStreamConfigService;
import com.onelive.manage.service.sys.SysParameterService;


@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StreamConfigBusiness {

    @Resource
    private SysStreamConfigService sysStreamConfigService;
    
    @Resource
    private SysParameterService sysParameterService;

    /**
     * 	简单查询流配置
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<SysStreamConfigDto> getList(Integer pageNum, Integer pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
    	List<SysStreamConfig> list = sysStreamConfigService.list();
    	return PageInfoUtil.pageInfo2DTO(new PageInfo<>(list), SysStreamConfigDto.class);
    }

    /**
     * 	保存流
     * @param req
     * @param loginUser
     * @throws Exception
     */
    public void save(SysStreamConfigDto req,LoginUser loginUser) {
    	SysStreamConfig sysStreamConfig = new SysStreamConfig();
    	BeanUtils.copyProperties(req, sysStreamConfig);
    	sysStreamConfigService.save(sysStreamConfig);
    }
    
    /**
     * 	流编辑
     */
    public Boolean update(SysStreamConfigDto req,LoginUser loginUser) {
    	 if (req.getId() == null) {
             throw new BusinessException("流id不能为空");
         }
    	 
    	 SysStreamConfig sysStreamConfig = new SysStreamConfig();
    	 BeanUtils.copyProperties(req, sysStreamConfig);
    	 SysStreamConfig already =  sysStreamConfigService.getById(req.getId());  
    	 if (already == null) {
             throw new BusinessException("找不到对应的流信息");
         }
    	return sysStreamConfigService.saveOrUpdate(sysStreamConfig);
    }
    
    /**
     * 	流删除
     */
    public Boolean delete(Integer id) {
    	return sysStreamConfigService.removeById(id);
    }

	/**
	 * 	系统变量表中查询cdn服务商list
	 * @return
	 */
	public List<CDNDto> getCdnBusiness() {
		List<SysParameterListVO> byType = sysParameterService.getListByType(SysParameterConstants.CDN);
		List<CDNDto> result = new ArrayList<CDNDto>(byType.size());
		for (SysParameterListVO sysParameterListVO : byType) {
			CDNDto cdnDto = new CDNDto();
			cdnDto.setCode(sysParameterListVO.getParamCode());
			cdnDto.setName(sysParameterListVO.getParamName());
			result.add(cdnDto);
		}
		return result;
	}
    
}
    