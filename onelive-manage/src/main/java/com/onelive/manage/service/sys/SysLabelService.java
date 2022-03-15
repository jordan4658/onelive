package com.onelive.manage.service.sys;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.mybatis.entity.SysLabel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
public interface SysLabelService extends IService<SysLabel> {

    List<SelectStringVO> queryLabelList();
}
