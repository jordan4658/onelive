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
 * @since 2021-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveGiftLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增i
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 礼物表id
     */
    private Integer giftId;

    /**
     * 语言标识
     */
    private String lang;

    /**
     * 多语言名字
     */
    private String giftName;


}
