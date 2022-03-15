package com.onelive.common.service.accountChange.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.service.accountChange.GoldChangeService;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.mybatis.entity.MemGoldchange;
import com.onelive.common.mybatis.mapper.master.mem.MemGoldchangeMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemGoldchangeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
public class GoldChangeServiceImpl extends ServiceImpl<MemGoldchangeMapper, MemGoldchange> implements GoldChangeService {


    @Resource
    private SlaveMemGoldchangeMapper slaveMemGoldchangeMapper;
    @Resource
    private MemGoldchangeMapper memGoldchangeMapper;

    @Override
    public Integer insetGoldChange(MemGoldchange memGoldchange) {
        return memGoldchangeMapper.insert(memGoldchange);
    }

    @Override
    public List<MemGoldchangeVO> rechargeRecord(Date startDate, Date endDate, String account) {
        return slaveMemGoldchangeMapper.rechargeRecord(startDate, endDate, account);
    }
}
