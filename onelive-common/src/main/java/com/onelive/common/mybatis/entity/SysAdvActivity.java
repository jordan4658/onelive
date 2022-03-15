package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 广告活动管理表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAdvActivity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗
     */
    private Integer activityType;

    /**
     * 活动开始时间
     */
    private Date startDate;

    /**
     * 活动结束时间
     */
    private Date endDate;

    /**
     * 活动地区国家code列表
     */
    private String countryCodeList;

    /**
     * 地区名称列表
     */
    private String countryNameList;

    /**
     * 跳转模块 0无 1链接 2活动 3游戏
     */
    private String skipModel;

    /**
     * 跳转路径
     */
    private String skipUrl;

    /**
     * 关联活动配置ID operate_activity_config.id
     */
    private Long configId;

    /**
     * 游戏分类ID,关联lotter_category.id
     */
    private Integer lotteryCategoryId;

    /**
     * 游戏ID，关联lottery.id
     */
    private Integer lotteryId;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 活动排序
     */
    private Integer sort;

    /**
     * 状态 0启用1禁用
     */
    private Boolean isFrozen;

    /**
     * 是否删除 0否1是
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人(后台操作人登录账号)
     */
    private String createUser;

    /**
     * 修改人(后台操作人登录账号)
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
