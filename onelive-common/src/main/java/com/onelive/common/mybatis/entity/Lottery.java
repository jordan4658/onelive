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
 * 彩种表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Lottery implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 彩票名称
     */
    private String name;

    /**
     * 彩票图标
     */
    private String icon;

    /**
     * 彩票分类id
     */
    private Integer categoryId;

    /**
     * 开奖号码源彩种
     */
    private Integer parentId;

    /**
     * 开奖号码表
     */
    private String startlottoTable;

    /**
     * 每天/年开奖期数
     */
    private Integer startlottoTimes;

    /**
     * 清算标识
     */
    private String clearingTag;

    /**
     * 缓存标识
     */
    private String cacheTag;

    /**
     * 最大赔率
     */
    private Double maxOdds;

    /**
     * 最小赔率
     */
    private Double minOdds;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 推送号码来源
     */
    private String pushSource;

    /**
     * 推送状态;0不推送;1,推送
     */
    private Integer pushStatus;

    /**
     * 开奖视频链接
     */
    private String videoUrl;

    /**
     * 视频打开方式
     */
    private Integer videoStart;

    /**
     * 是否开售
     */
    private Integer isWork;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 彩种编号
     */
    private Integer lotteryId;

    /**
     * 封盘时间-app端使用
     */
    private Integer endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
