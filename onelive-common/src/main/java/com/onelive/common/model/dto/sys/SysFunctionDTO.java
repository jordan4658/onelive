package com.onelive.common.model.dto.sys;


import lombok.Data;


@Data
public class SysFunctionDTO{

    /** 功能id */
    private Long funcId;

    /** 父级id */
    private Long parentFuncId;

    /** 功能模块名称 */
    private String funcName;

    /** 功能模块类型 */
    private String funcType;

    /** 功能模块参数 */
    private String funcUrl;

    /** 模块状态 */
    private Integer funcStatus;

}
