package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoomFastWords implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 快捷回复语句
     */
    private String context;
    
    /**
     * 	排序字段,越大越前面
     */
	private Integer sortNum;

    /**
     * 是否展示，默认是
     */
    private Boolean isShow;

    /**
     * 语言标识
     */
    private String lang;
    
    
    /**
     * 创建时间
     */
    private String createTime;


}
