package com.onelive.common.mybatis.entity;

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
 * @since 2021-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFileRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件md5标识
     */
    private String md5Flag;

    /**
     * 文件地址路径（相对路径）
     */
    private String flieUrl;

    /**
     * 创建时间
     */
    private Date createTime = new Date();


}
