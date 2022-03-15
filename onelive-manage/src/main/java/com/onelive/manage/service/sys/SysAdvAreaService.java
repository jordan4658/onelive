package com.onelive.manage.service.sys;

import com.onelive.common.model.vo.sys.SysAdvAreaListVO;
import com.onelive.common.mybatis.entity.SysAdvArea;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 广告设置表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface SysAdvAreaService extends IService<SysAdvArea> {

	List<SysAdvAreaListVO> getList();
}
