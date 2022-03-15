package com.onelive.common.mybatis.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author ${author}
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SysLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 描述
     */
    private String description;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 参数
     */
    private String params;

    /**
     * 请求ip
     */
    private String requestIp;

    private Long time;

    /**
     * 操作人
     */
    private String username;

    /**
     * 地址
     */
    private String address;

    private String browser;

    /**
     * 异常原因
     */
    private String exceptionDetail;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;

    public SysLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }

    public void copyFrom(SysLog source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

}
