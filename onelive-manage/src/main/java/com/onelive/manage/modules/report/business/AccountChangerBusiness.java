package com.onelive.manage.modules.report.business;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.vo.mem.AccountChangeTypeVO;
import com.onelive.common.model.vo.report.MemGoldChangeBackVO;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.manage.service.mem.MemGoldchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class AccountChangerBusiness {


    @Resource
    private MemGoldchangeService memGoldchangeService;

    public PageInfo<MemGoldChangeBackVO> listPage(Integer pageNum, Integer pageSize, Date startTime, Date endTime, String nickName, String account, Integer changeType) {

        if (startTime == null) {
            startTime = DateInnerUtil.beginOfDay(new Date());
        } else {
            startTime = DateInnerUtil.endOfDay(endTime);
        }
        if (endTime == null) {
            endTime = DateInnerUtil.endOfDay(new Date());
        } else {
            endTime = DateInnerUtil.beginOfDay(startTime);
        }
        if (endTime.compareTo(startTime) < 0) {
            throw new BusinessException("开始日期大于结束日期！");
        }
        if (DateInnerUtil.betweenDay(startTime, DateInnerUtil.parse(DateInnerUtil.today())) > 30) {
            throw new BusinessException("查询日期范围超过30天！");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<MemGoldChangeBackVO> list = memGoldchangeService.listPage(startTime, endTime, nickName, account, changeType);
        return new PageInfo<MemGoldChangeBackVO>(list);
    }

    public List<AccountChangeTypeVO> getAccountChangeTypeList() {
        List<AccountChangeTypeVO> list = new ArrayList<>();
        for (PayConstants.AccountChangTypeEnum accountChangTypeEnum : PayConstants.AccountChangTypeEnum.values()) {
            AccountChangeTypeVO a = new AccountChangeTypeVO();
            a.setCode(accountChangTypeEnum.getPayTypeCode());
            a.setName(accountChangTypeEnum.getMsg());
            list.add(a);
        }
        return list;
    }
}
