package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 彩种玩法
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryPlay implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 玩法名称
     */
    private String name;

    /**
     * 彩种分类id
     */
    private Integer categoryId;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 取值区间
     */
    private String section;

    /**
     * 玩法节点
     */
    private String tree;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDelete;

    /**
     * 彩种ID
     */
    private Integer lotteryId;

    /**
     * 玩法规则Tag编号
     */
    private Integer playTagId;


}
