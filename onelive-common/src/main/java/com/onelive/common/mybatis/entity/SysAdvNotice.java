package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 广告公告表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAdvNotice implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 广告设置id
     */
    private Integer areaId;
    
    /**
     * 	循环公告:用户进入直播后多久开始播放
     */
	private Integer intervalTime;
	
	/**
	 * 循环公告的循环次数,默认1
	 */
	private Integer loopCount;
	
    /**
     * 	循环公告:用户进入直播后多久开始播放
     */
	private Integer waitTime;
	
    /**
     * 	1：直播间公告  2：开场公告（一个语言只有一条）"3：循环公告  4：首页公告 5：推荐栏目路马灯公告  6：游戏列表路马灯公告 7:兑换银豆公告 8：充值中心
     */
    private Integer type;

    /**
     * 公告名称
     */
    private String noticeName;

    /**
     * 展示开始时间
     */
    private Date startDate;

    /**
     * 展示结束时间
     */
    private Date endDate;

    /**
     * 	适用国家id,多个逗号分隔,空即:所有
     */
    private  String useCountry;

    /**
     * 状态 0显示1隐藏
     */
    private Integer isHide;

    /**
     * 公告内容
     */
    private String noticeContent;

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
