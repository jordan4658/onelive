package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.model.vo.common.SelectVO;
import com.onelive.common.mybatis.entity.SysLabel;
import com.onelive.common.mybatis.mapper.master.sys.SysLabelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysLabelMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.sys.SysLabelService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
public class SysLabelServiceImpl extends ServiceImpl<SysLabelMapper, SysLabel> implements SysLabelService {

    @Resource
    private SlaveSysLabelMapper slaveSysLabelMapper;

    @Override
    public List<SelectStringVO> queryLabelList() {
        List<SelectStringVO> voList = new ArrayList<>();
        QueryWrapper<SysLabel> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysLabel::getIsDelete,false);
        queryWrapper.lambda().eq(SysLabel::getMerchantCode, LoginInfoUtil.getMerchantCode());
        queryWrapper.lambda().eq(SysLabel::getLang,LoginInfoUtil.getLang());
        List<SysLabel> list = slaveSysLabelMapper.selectList(queryWrapper);

        if(CollectionUtils.isNotEmpty(list)){
            voList = list.stream().map(a->{
                SelectStringVO vo = new SelectStringVO();
                vo.setValue(a.getLabelCode());
                vo.setMsg(a.getName());
                return vo;
            }).collect(Collectors.toList());
        }
        return voList;
    }
}
