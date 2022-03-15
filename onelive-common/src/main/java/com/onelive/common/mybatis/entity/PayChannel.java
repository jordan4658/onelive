package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 第三方支付通道
 * </p>
 *
 * @author ${author}
 * @since 2021-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayChannel implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 支付通道ID
     */
      @TableId(value = "pay_channel_id", type = IdType.AUTO)
    private Long payChannelId;

    /**
     * 支付方式ID（支付通道绑定支付方式）
     */
    private Long payWayId;

    /**
     * 支付通道名称
     */
    private String channelName;

    /**
     * 支付通道code
     */
    private String channelCode;

    /**
     * 状态：0-禁用，1-启用
     */
    private Boolean status;

    /**
     * 是否删除：0-否，1-是
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 最后更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
