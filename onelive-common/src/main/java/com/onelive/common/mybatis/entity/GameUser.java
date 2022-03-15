package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 第三方游戏用户账号
 * </p>
 *
 * @author ${author}
 * @since 2022-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameUser implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID,关联mem_user.id
     */
    private Long userId;

    /**
     * 第三方游戏用户账号, 用于关联第三方数据
     */
    private String playerName;

    /**
     * 用户账号,关联mem_user.accno
     */
    private String accno;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 游戏类型(不同类型的游戏账户不通用)
     */
    private String gameType;

    /**
     * 商户号
     */
    private String merchantCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
