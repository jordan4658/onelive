package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 主播管理员配置表
 * </p>
 *
 * @author ${author}
 * @since 2021-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveAnchorRoomAdmin implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 主播用户表ID
     */
    private Long anchorId;

    /**
     * 主播的管理员用户ID
     */
    private Long adminId;


}
