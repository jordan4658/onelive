package com.onelive.common.model.req.mem.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("主播信息保存请求参数")
public class MemUserAnchorSaveReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty("用户id")
	private Long id;

	/**
	 * 用户唯一标识
	 */
	@ApiModelProperty("用户唯一标识")
	private String accno;

	@ApiModelProperty("创建人")
	private String createdBy;

	/**
	 * 注册的手机号
	 */
	@ApiModelProperty("注册的手机号")
	private String mobilePhone;
	
	@ApiModelProperty("被关注后可以奖励钱,默认关闭")
    private Boolean isFocusAward;
	
	/**
	 * 更新人
	 */
	@ApiModelProperty("更新人")
	private String updateBy;

	/**
	 * 注册的区号
	 */
	@ApiModelProperty("注册的区号")
	private String registerAreaCode;

	/**
	 * 性别 0保密 1男 2女
	 */
	@ApiModelProperty("性别 0保密 1男 2女")
	private Integer sex;

	/**
	 * 登录密码
	 */
	@ApiModelProperty("登录密码")
	private String password;

	/**
	 * 	用户登录账号/主播账号
	 */
	@ApiModelProperty("用户登录账号/主播账号")
	private String userAccount;

	/**
	 * 主播昵称
	 */
	@ApiModelProperty("主播昵称")
	private String nickName;

	@ApiModelProperty("所属地区code")
    private String countryCode;

	/**
	 * 用户层级id
	 */
	@ApiModelProperty("用户层级id")
	private Long groupId;

	/**
	 * 用户等级
	 */
	@ApiModelProperty("用户等级")
	private Integer userLevel;

	/**
	 * 生日日期
	 */
	@ApiModelProperty("生日日期")
	private String birthday;

	/**
	 * 用户头像（相对路径）
	 */
	@ApiModelProperty("用户头像（相对路径）")
	private String avatar;

	@ApiModelProperty("家族名")
	private String familyName;
	
	@ApiModelProperty("家族账号")
	private String familyUserAccount;

	@ApiModelProperty("是否冻结 0否1是")
	private Boolean isFrozen;

	@ApiModelProperty("是否返点 0否1是")
	private Boolean isCommission;

	@ApiModelProperty("是否允许投注 0否1是")
	private Boolean isBet;

	@ApiModelProperty("ApiModelProperty")
	private Boolean isDispensing;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("是否在线 0否1是")
	private Boolean isOnline;

	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("商户code值，默认值为0")
	private String merchantCode;

	private String salt;

	@ApiModelProperty("关联用户表自增id")
	private Long userId;

	@ApiModelProperty("家族id")
	private Long familyId;

	@ApiModelProperty("直播时长(每次直播结束时统计)")
	private Integer liveTime;

	@ApiModelProperty("直播次数")
	private Integer liveCount;
	
	@ApiModelProperty("查询类型1:总资产 2:下级会员 3:登录次数")
	private Integer queryType;

	@ApiModelProperty("特殊查询条件开始 1:总资产 2:下级会员 3:登录次数")
	private Integer queryTypeParamStrat;
	
	@ApiModelProperty("特殊查询条件结束 1:总资产 2:下级会员 3:登录次数")
	private Integer queryTypeParamEnd;

	@ApiModelProperty("礼物分成 例如:3 得到礼物总金额的30%")
	private BigDecimal giftRatio;

	@ApiModelProperty("收到的礼物金额(未提现)")
	private BigDecimal giftMoney;

	@ApiModelProperty("收到的礼物总额")
	private BigDecimal giftMoneyTotal;

	@ApiModelProperty("已经提现的钱")
	private BigDecimal withdrawal;

	@ApiModelProperty("主播标签:针对主播个人的特色标签")
	private String label;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;
    
    @ApiModelProperty("被关注后奖励的金额")
  	private BigDecimal focusAward;
}
