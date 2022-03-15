package com.onelive.common.mybatis.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 购彩返水
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryBetOrderRecord implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 彩种名称
     */
    private String lotteryName;

    private Integer lotteryCategoryId;

    /**
     * 彩种ID
     */
    private Integer lotteryId;

    /**
     * 返水比例
     */
    private Integer waterAmout;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 修改人
     */
    private String editUser;

    /**
     * 修改时间
     */
    private Date editTime;


}
