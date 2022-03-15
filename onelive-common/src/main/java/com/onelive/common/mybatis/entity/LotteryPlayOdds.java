package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 赔率配置表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryPlayOdds implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 玩法id
     */
    private Integer settingId;

    /**
     * 扩展ID, 一个新玩法需要使用老的玩法开奖时用
     */
    private Integer exSettingId;

    /**
     * 名称
     */
    private String name;

    /**
     * 总柱数
     */
    private String totalCount;

    /**
     * 中奖柱数
     */
    private String winCount;

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
    private Boolean isDelete;

    /**
     * Excel导入标识(新增或更新）
     */
    private Integer easyImportFlag;


}
