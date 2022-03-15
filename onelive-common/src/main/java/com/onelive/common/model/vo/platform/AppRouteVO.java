package com.onelive.common.model.vo.platform;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("APP内页面路由信息")
public class AppRouteVO {
    /**
     * 页面
     */
    private String name;
    /**
     * 路由
     */
    private String route;

}
