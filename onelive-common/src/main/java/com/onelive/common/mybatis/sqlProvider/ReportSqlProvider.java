package com.onelive.common.mybatis.sqlProvider;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.onelive.common.constants.business.PayConstants;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ReportSqlProvider
 * @Description: 报表查询
 */
public class ReportSqlProvider {

    /**
     * 资金报表充值信息查询
     */
    public String queryFundsRecharge(@Param("dateList") List<DateTime> dateList) {
        StringBuilder sql = new StringBuilder();

        Integer length = dateList.size();
        for (int i = 0; i < length; i++) {
            Date date = dateList.get(i);
            String beginDate = DateUtil.formatDate(date);
            String endDate = DateUtil.formatDateTime(DateUtil.endOfDay(date));

            sql.append(" select '" + beginDate + "'  reportDate ,  ");
            sql.append(" IFNULL(sum(sum_amt), 0) sumRecharge ,  ");
            sql.append(" IFNULL(sum(case when is_first = 1 then 1 else 0 end),0)  sumFirstNum   ");
            sql.append("  from pay_order_recharge ");
            sql.append("  where create_time between '" + beginDate + "' and '" + endDate + "' ");
            sql.append("  and order_status = " + PayConstants.PayOrderStatusEnum.SUCCESS.getCode() + " ");

            if (i < length - 1) {
                sql.append(" union all ");
            }
        }
        return sql.toString();
    }

    /**
     * 资金报表提现信息查询
     */
    public String queryFundsWithdraw(@Param("dateList") List<DateTime> dateList) {
        StringBuilder sql = new StringBuilder();

        Integer length = dateList.size();
        for (int i = 0; i < length; i++) {
            Date date = dateList.get(i);
            String beginDate = DateUtil.formatDate(date);
            String endDate = DateUtil.formatDateTime(DateUtil.endOfDay(date));

            sql.append(" select '" + beginDate + "'  reportDate ,  ");
            sql.append(" IFNULL(sum(withdraw_amt), 0) sumWithdraw   ");
            sql.append("  from pay_order_withdraw ");
            sql.append("  where create_time between '" + beginDate + "' and '" + endDate + "' ");
            sql.append("  and withdraw_status = " + PayConstants.PayOrderStatusEnum.SUCCESS.getCode() + " ");

            if (i < length - 1) {
                sql.append(" union all ");
            }
        }
        return sql.toString();
    }


    /**
     * 资金报表im体育信息查询
     */
    public String queryFundsIm(@Param("dateList") List<DateTime> dateList) {
        StringBuilder sql = new StringBuilder();

        Integer length = dateList.size();
        for (int i = 0; i < length; i++) {
            Date date = dateList.get(i);
            String beginDate = DateUtil.formatDate(date);
            String endDate = DateUtil.formatDateTime(DateUtil.endOfDay(date));

            sql.append(" select '" + beginDate + "'  reportDate ,  ");
            sql.append(" IFNULL(sum(amount), 0) totalComsumer,   ");
            sql.append(" IFNULL(sum(win_loss), 0) playWinAmount   ");
            sql.append(" from bet_im_order ");
            sql.append("  where bet_time between '" + beginDate + "' and '" + endDate + "' ");
            sql.append("  and win_loss is not null ");

            if (i < length - 1) {
                sql.append(" union all ");
            }
        }
        return sql.toString();
    }


}
