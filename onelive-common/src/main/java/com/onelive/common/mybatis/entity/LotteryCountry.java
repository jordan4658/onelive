package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 彩票-国家对应表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryCountry implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 彩票分类id
      */
    private Integer lotteryCateId;

    /**
     * 彩票id
     */
    private Integer lotteryId;

    /**
     * 国家id
     */
    private Long countryId;

    /**
     * 别名
     */
    private String aliasName;

    /**
     * 图片地址
     */
    private String iconUrl;

    /**
     * 是否禁用 0否1是
     */
    private Boolean isForbid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 后台创建人账号
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 后台更新人账号
     */
    private String updateUser;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
