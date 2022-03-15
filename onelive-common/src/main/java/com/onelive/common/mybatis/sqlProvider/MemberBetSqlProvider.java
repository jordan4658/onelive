package com.onelive.common.mybatis.sqlProvider;

import cn.hutool.core.date.DateUtil;
import com.onelive.common.model.req.lottery.MemberBetReq;
import com.onelive.common.utils.others.DateInnerUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


public class MemberBetSqlProvider {

    /**
     * 注单订单列表
     * @return
     */
    public String getListOrderBet(MemberBetReq req){
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT bb.id,  ");
        sql.append("    aa.user_id as userId,  ");
        sql.append("    aa.create_time as betTime,  ");
        sql.append("    aa.order_sn as  orderNo,  ");
        sql.append("    cc.accno,  ");
        sql.append("    cc.nick_name as nickName,  ");
        sql.append("    sc.zh_name AS countryName,  ");
        sql.append("    aa.lottery_id as lotteryId,  ");
        sql.append("    aa.issue, ");
        sql.append("    bb.pid, ");
        sql.append("    bb.play_id as lotteryPlayId, ");
        sql.append("    aa.open_number as openNumber, ");
        sql.append("    bb.bet_number as betNumber, ");
        sql.append("    bb.bet_amount as betAmount, ");
        sql.append("    bb.tb_status as tbStatus, ");
        sql.append("    bb.win_amount as winAmount, ");
        sql.append("    bb.update_time as updateTime, ");
        sql.append("    (IFNULL( bb.win_amount, 0 )- IFNULL( bb.back_amount, 0 )- IFNULL( bb.bet_amount, 0 )) AS changeMoney  ");
        sql.append("  from  lottery_order_record aa  ");
        sql.append("    INNER JOIN lottery_order_bet_record bb on aa.id = bb.order_id  ");
        sql.append("    LEFT JOIN mem_user cc on cc.id = aa.user_id  ");
        sql.append("    LEFT JOIN sys_country sc ON sc.id = aa.country_id ");
        sql.append("  where aa.is_delete = 0 ");

        if(req != null){
            //是否只传入了分页,其他条件没有传入
            Boolean isDefault = false;

            //用户账号
            String accno = req.getAccno();
            if(StringUtils.isNotBlank(accno)){
                sql.append("  and cc.accno like '%").append(accno).append("%'  ");
            }
            //地区
            Integer countryId = req.getCountryId();
            if(countryId != null){
                isDefault = true;
                sql.append("  and sc.id =  '").append(countryId).append("' ");
            }
            //期号
            String issue = req.getIssue();
            if(StringUtils.isNotBlank(issue)){
                isDefault = true;
                sql.append("  and aa.issue = '").append(issue).append("' ");
            }
            //订单号
            String orderNo = req.getOrderNo();
            if(StringUtils.isNotBlank(orderNo)){
                isDefault = true;
                sql.append("  and aa.order_sn = '").append(orderNo).append("' ");
            }
            //彩种id
            Integer lotteryId = req.getLotteryId();
            if(lotteryId != null){
                isDefault = true;
                sql.append("  and aa.lottery_id = '").append(lotteryId).append("' ");
            }
            //玩法id
            Integer playId = req.getPlayId();
            if(playId != null){
                isDefault = true;
                sql.append("  and bb.pid  = '").append(playId).append("' ");
            }
            //注单状态
            String tbStatus = req.getTbStatus();
            if(StringUtils.isNotBlank(tbStatus)){
                isDefault = true;
                sql.append("  and bb.tb_status  = '").append(tbStatus).append("' ");
            }
            //开始时间 和 结束时间
            String startDate = req.getStartDate();
            String endDate = req.getEndDate();
            if(StringUtils.isNotBlank(startDate) &&StringUtils.isNotBlank(endDate)){
                isDefault = true;
                sql.append("  and aa.create_time >= '").append(startDate)
                        .append("'  and aa.create_time <= '").append(endDate).append("' ");
            }

            //如果只是传入分页数据，则默认查询当天
            if(!isDefault){
                Date now = new Date();
                Date beginOfDay = DateUtil.beginOfDay(now);
                Date endOfDay = DateUtil.endOfDay(now);
                sql.append("  and aa.create_time >= '"+DateInnerUtil.formatDateTime(beginOfDay)+"'  ");
                sql.append("  and aa.create_time <= '"+DateInnerUtil.formatDateTime(endOfDay)+"'  ");
            }
        }
        sql.append(" order by aa.create_time desc ");
        return sql.toString();
    }
}
