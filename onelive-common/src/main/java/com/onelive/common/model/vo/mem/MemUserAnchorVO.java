package com.onelive.common.model.vo.mem;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class MemUserAnchorVO implements Serializable {

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
	 * 用户登录账号/主播账号
	 */
	@ApiModelProperty("用户登录账号/主播账号")
	private String userAccount;

	/**
	 * 主播昵称
	 */
	@ApiModelProperty("主播昵称")
	private String nickName;

	@ApiModelProperty("被关注后可以奖励钱,默认关闭")
	private Boolean isFocusAward;
	
    @ApiModelProperty("用户头像")
    private String avatar;

	@ApiModelProperty("所属地区Code")
	private String countryCode;

	/**
	 * 最后登录地区
	 */
	@ApiModelProperty("最后登录地区")
	private String lastLoginArea;

	@ApiModelProperty("家族名")
	private String familyName;

	@ApiModelProperty("家族账号")
	private String familyUserAccount;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("是否在线 0否1是")
	private Boolean isOnline;

	@ApiModelProperty("商户code值，默认值为0")
	private String merchantCode;

	@ApiModelProperty("关联用户表自增id")
	private Long userId;

	@ApiModelProperty("家族id")
	private Integer familyId;

	@ApiModelProperty("直播时长(每次直播结束时统计)")
	private Integer liveTime;

	@ApiModelProperty("下级会员数")
	private Integer subordinateCount;

	@ApiModelProperty("直播次数/登录次数")
	private Integer liveCount;

	@ApiModelProperty("查询类型1:总资产 2:下级会员 3:登录次数")
	private Integer queryType;

	@ApiModelProperty("状态:是否冻结 0否1是（用户与主播共用）")
	private Boolean isFrozen;

	@ApiModelProperty("特殊查询条件开始 1:总资产 2:下级会员 3:登录次数")
	private Integer queryTypeParamStrat;

	@ApiModelProperty("特殊查询条件结束 1:总资产 2:下级会员 3:登录次数")
	private Integer queryTypeParamEnd;

	@ApiModelProperty("礼物分成 例如:3 得到礼物总金额的30%")
	private BigDecimal giftRatio;

	@ApiModelProperty("总资产：钱包金币余额")
    private BigDecimal balance;

    @ApiModelProperty("被关注的奖金总额")
    private BigDecimal focusTotal;
    
    @ApiModelProperty("收到的礼物总额")
    private BigDecimal giftTotal;
    
    @ApiModelProperty("弹幕收到的总金额")
    private BigDecimal barrageTotal;
    
    @ApiModelProperty("代理收益的总金额")
    private BigDecimal rebatesTotal;

	@ApiModelProperty("已经提现的钱")
	private BigDecimal withdrawal;

	@ApiModelProperty("主播标签:针对主播个人的特色标签")
	private String label;

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty("被关注后奖励的金额，主播有家族长时，取家族表的focus_award")
	private BigDecimal focusAward;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;

	public void setAvatar(String avatar) {
		this.avatar = AWSS3Util.getAbsoluteUrl(avatar);
	}
}
