package com.onelive.manage.service.mem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.model.req.report.UserReportDetailReq;
import com.onelive.common.model.vo.report.MemGoldChangeBackVO;
import com.onelive.common.model.vo.report.UserReportDetailAllVO;
import com.onelive.common.model.vo.report.UserReportDetailVO;
import com.onelive.common.mybatis.entity.MemGoldchange;
import com.onelive.common.mybatis.mapper.master.mem.MemGoldchangeMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemGoldchangeMapper;
import com.onelive.manage.service.mem.MemGoldchangeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * 会员金额变动明细 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
@Service
public class MemGoldchangeServiceImpl extends ServiceImpl<MemGoldchangeMapper, MemGoldchange> implements MemGoldchangeService {

    @Resource
    private SlaveMemGoldchangeMapper slaveMemGoldchangeMapper;


    @Override
    public UserReportDetailAllVO queryUserDetail(UserReportDetailReq req) {
        //查询分页数据
        PageInfo<UserReportDetailVO> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> slaveMemGoldchangeMapper.queryUserDetail(req));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            Iterator<UserReportDetailVO> iterator = pageInfo.getList().iterator();
            while (iterator.hasNext()) {
                UserReportDetailVO vo = iterator.next();
                vo.setChangeTypeName(PayConstants.AccountChangTypeEnum.getMsgByCode(vo.getChangeType()));
            }
        }
        //总金额
        BigDecimal sumAmount = slaveMemGoldchangeMapper.queryUserDetailSum(req);
        //返回结果
        UserReportDetailAllVO resultVO = new UserReportDetailAllVO();
        resultVO.setData(pageInfo);
        resultVO.setSumAmount(sumAmount);
        return resultVO;
    }

    @Override
    public List<MemGoldChangeBackVO> listPage(Date startTime, Date endTime, String nickName, String account, Integer changeType) {
        return slaveMemGoldchangeMapper.listPage(startTime,endTime,nickName,account, null, changeType);
    }
}
