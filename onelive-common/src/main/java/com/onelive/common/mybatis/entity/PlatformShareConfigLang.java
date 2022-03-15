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
 * @since 2021-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlatformShareConfigLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分享类型的id
     */
    private Long shareId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 分享内容
     */
    private String shareContent;

    /**
     * 分享的标题(Messenger专有)
     */
    private String shareTitle;

    /**
     * 分享的图片相对路径(Messenger专有)
     */
    private String sharePic;


}
