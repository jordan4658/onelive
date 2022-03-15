package com.onelive.manage.service.mem.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.mem.MemUserLoginReq;
import com.onelive.common.model.vo.mem.MemUserLoginVO;
import com.onelive.common.mybatis.entity.MemLoginRecord;
import com.onelive.common.mybatis.mapper.master.mem.MemLoginRecordMapper;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.mem.MemLoginRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录记录表 服务实现类
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-07
 */
@Service
public class MemLoginRecordServiceImpl extends ServiceImpl<MemLoginRecordMapper, MemLoginRecord> implements MemLoginRecordService {

    @Override
    public PageInfo<MemUserLoginVO> queryLoginInfo(MemUserLoginReq req) {
        QueryWrapper<MemLoginRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemLoginRecord::getAccount,req.getAccno());
        PageInfo<MemLoginRecord> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> this.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo,MemUserLoginVO.class);
    }
}
