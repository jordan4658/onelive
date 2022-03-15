package com.onelive.common.model.vo.mem;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class MemUserListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 用户唯一标识
     */
    @ApiModelProperty("用户唯一标识")
    private String accno;

    /**
     * 用户登录账号（用户与主播共用）
     */
    @ApiModelProperty("用户登录账号")
    private String userAccount;

    /**
     * 昵称（用户与主播共用）
     */
    @ApiModelProperty("昵称")
    private String nickName;

    /**
     * 注册地区
     */
    @ApiModelProperty("注册地区")
    private String registerArea;

    /**
     * 可用余额
     */
    @ApiModelProperty("金币余额")
    private BigDecimal balance;

    @ApiModelProperty("银豆余额")
    private BigDecimal silverBean;

    /**
     * 下级会员数
     */
    @ApiModelProperty("下级会员数")
    private Integer subNum;

    /**
     * 上级账号
     */
    @ApiModelProperty("上级账号")
    private String parentUserAccount;

    /**
     * 用户等级（用户与主播共用）
     */
    @ApiModelProperty("用户等级")
    private Integer userLevel;

    @ApiModelProperty("用户层级")
    private Integer groupId;

    /**
     * 登陆次数
     */
    @ApiModelProperty("登陆次数")
    private Integer loginNum;


    /**
     * 注册时间
     */
    @ApiModelProperty("注册时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    /**
     * 最后登录ip
     */
    @ApiModelProperty("最后登录ip")
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 用户状态 是否冻结 0否1是（用户与主播共用）
     */
    @ApiModelProperty("用户状态 是否冻结 0否1是")
    private Boolean isFrozen;

}
