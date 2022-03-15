package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 	短信服务商配置
 * </p>
 */
@Data
@ApiModel(value = "短信服务商")
public class MsgBusiness {

	/**
	 * 阿里
	 */
	@Data
	@ApiModel(value = "阿里短信服务商")
	public class AliMsg {

		@ApiModelProperty("AccessKey ID")
		private String accessKeyId;

		@ApiModelProperty("AccessKeySecret")
		private String accessKeySecret;

		@ApiModelProperty("短信签名SignName")
		private String signName;

		@ApiModelProperty("短信模板IDTemplateCode")
		private String templateCode;

		@ApiModelProperty("平台商id，不需赋值，有默认值")
		private Integer platform = 1;
	}

	/**
	 * Mtcvn
	 */
	@Data
	@ApiModel(value = "Mtcvn短信服务商")
	public class MtcvnMsg {
		@ApiModelProperty("MTCVN账号")
		private String mtcvnUsername;

		@ApiModelProperty("MTCVN密码")
		private String mtcvnPassword;

		@ApiModelProperty("Brandname")
		private String mtcvnBrandname;

		@ApiModelProperty("MTCVN URL")
		private String mtcvnUrl;

		@ApiModelProperty("平台商id，不需赋值，有默认值")
		private Integer platform = 4;
	}

	/**
	 * Abenla
	 */
	@ApiModel(value = "Abenla短信服务商")
	@Data
	public class AbenlaMsg {
		@ApiModelProperty("Abenla URL")
		private String abenlaUrl;

		@ApiModelProperty("注册品牌名称")
		private String abenlaBrandName;

		@ApiModelProperty("短信平台账号")
		private String abenlaUsername;

		@ApiModelProperty("注册服务类型ID")
		private String abenlaServicetypeId;

		@ApiModelProperty("短信平台密码")
		private String abenlaSendSmsPassword;

		@ApiModelProperty("平台商id，不需赋值，有默认值")
		private Integer platform = 3;
	}

	/**
	 * 第三方
	 */
	@Data
	@ApiModel(value = "第三方短信服务商")
	public class OtherMsg {

		@ApiModelProperty("密码")
		private String password;

		@ApiModelProperty("端口")
		private String port;

		@ApiModelProperty("用户名")
		private String ani;

		@ApiModelProperty("url")
		private String url;

		@ApiModelProperty("平台商id，不需赋值，有默认值")
		private Integer platform = 2;
	}
}
