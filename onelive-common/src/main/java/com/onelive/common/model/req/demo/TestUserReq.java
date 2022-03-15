package com.onelive.common.model.req.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author aguang
 * @version V1.0
 * @ClassName: TestUser
 * @Description: (这里用一句话描述这个类的作用)
 * @date 创建时间：2021/3/13 16:16
 */
@ApiModel
@Data
public class TestUserReq {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户年龄")
    private Integer age;
}
