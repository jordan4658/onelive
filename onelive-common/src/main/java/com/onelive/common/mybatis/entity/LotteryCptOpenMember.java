package com.onelive.common.mybatis.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryCptOpenMember implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * CPT用户uid
     */
    private Integer userId;

    /**
     * 第三方登入账号(随机生成)
     */
    private String username;

    /**
     * 第三方登入密码(随机生成)
     */
    private String password;

    /**
     * 第三方余额（元）
     */
    private BigDecimal balance;

    /**
     * 账号类型()
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 登入时间
     */
    private Date loginTime;

    /**
     * 用户分层编号（代理扩展）
     */
    private String layerNo;


}
