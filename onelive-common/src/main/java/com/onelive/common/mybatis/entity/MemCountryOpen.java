package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户开放地区表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemCountryOpen implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 所属地区id
     */
    private Long countryId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人(后台操作人登录账号)
     */
    private String createUser;

    /**
     * 修改人(后台操作人登录账号)
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
