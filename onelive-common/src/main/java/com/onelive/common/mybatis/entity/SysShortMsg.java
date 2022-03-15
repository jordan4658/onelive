package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 短信记录表
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysShortMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 	用户账号
     */
    private String userAccount;
    
    /**
     * 	服务商名
     */
    private String businessName;
 
    /**
     * 	   设备名
     */
    private String deviceName;
    
    /**
     * 	   设备码
     */
    private String deviceId;

    
    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 区号
     */
    private String areaCode;

    /**
     * 发送时间
     */
    private Date sendDate;

    /**
     * 短信类型 0注册登录 1绑定银行卡 2找回密码 3绑定手机号
     */
    private Integer msgType;

    /**
     * 失效时间
     */
    private Date validDate;

    /**
     * 验证码
     */
    private String masCode;

    /**
     * 短信状态码 0发送成功 8已使用 9发送失败
     */
    private Integer masStatus;

    /**
     * 发送的ip
     */
    private String sendIp;

    /**
     * 短信内容
     */
    private String masBody;

    /**
     * 短信备注
     */
    private String masRemark;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
