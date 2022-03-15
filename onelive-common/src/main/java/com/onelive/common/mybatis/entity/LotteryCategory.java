package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 彩种分类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryCategory implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    /**
     * 玩法级别数
     */
    private Integer level;

    /**
     * 是否开售
     */
    private Integer isWork;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 大类ID
     */
    private Integer categoryId;

    /**
     * 彩种分类排序
     */
    private Integer sort;

    /**
     * 彩种类别：LOTTERY(彩票类); QIPAI(棋牌类); ZRSX(真人视讯类); ZUCAI(足彩类);
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
