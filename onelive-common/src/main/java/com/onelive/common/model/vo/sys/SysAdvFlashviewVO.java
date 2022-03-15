package com.onelive.common.model.vo.sys;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 	广告首页轮播ListVO类
 * </p>
 * 
 * @since 2021-10-19
 */
@Data
@ApiModel(value = "轮播图,新增,修改,查询传输类")
public class SysAdvFlashviewVO {

	@ApiModelProperty("主键")
	private Long id;
	
	@ApiModelProperty("多语言对象新增,修改时用")
	private List<SysAdvFlashviewLangVO> sysAdvFlashviewLangList;
	
	@ApiModelProperty("轮播名称")
	private String flashviewName;

	@ApiModelProperty("适用国家id,多个逗号分隔,空即:所有")
	private String useCountry;

	@ApiModelProperty("类型code，显示在app不同的页面")
	private String typeCode;

	@ApiModelProperty("展示开始时间，系统时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startDate;

	@ApiModelProperty("展示结束时间，系统时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endDate;

	@ApiModelProperty("是否展示：1展示 0不展示")
	private Boolean isShow;

	@ApiModelProperty("创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@ApiModelProperty("创建人(后台操作人登录账号)")
	private String createUser;

	@ApiModelProperty("修改人(后台操作人登录账号)")
	private String updateUser;

	@ApiModelProperty("修改时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;

	@ApiModelProperty("商户code值，默认值为0")
	private String merchantCode;
	
	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;


}
