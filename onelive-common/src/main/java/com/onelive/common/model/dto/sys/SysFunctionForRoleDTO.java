package com.onelive.common.model.dto.sys;

import lombok.Data;

@Data
public class SysFunctionForRoleDTO{

    private Integer checkbox;

    private Long roleId;

    private Long funcId;

    private Long parentFuncId;

    private String funcName;

    private String funcType;

    private String funcUrl;
}
