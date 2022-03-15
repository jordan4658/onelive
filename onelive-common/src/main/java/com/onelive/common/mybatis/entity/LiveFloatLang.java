package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveFloatLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 悬浮窗
     */
    private Long floatId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 图片地址
     */
    private String imgUrl;


}
