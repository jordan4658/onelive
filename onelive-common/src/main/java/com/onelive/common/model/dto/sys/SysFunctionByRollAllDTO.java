package com.onelive.common.model.dto.sys;

import lombok.Data;

/**
 * @author lorenzo
 * @Description:
 * @date 2021/4/6
 */
@Data
public class SysFunctionByRollAllDTO {
    /**
     * 是否选中
     */
    private Integer checkbox;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 功能模块id
     */
    private Long funcId;

    /**
     * 父级功能模块id
     */
    private Long parentFuncId;

    /**
     * 模块名称
     */
    private String funcName;

    /**
     * 功能模块类型
     */
    private String funcType;

    /**
     * 模块参数
     */
    private String funcUrl;
}
