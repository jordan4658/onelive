package com.onelive.api.service.mem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.onelive.api.service.mem.MemUserBagService;
import com.onelive.common.model.dto.common.LangDTO;
import com.onelive.common.model.vo.live.AppUserLiveBagVO;
import com.onelive.common.mybatis.entity.MemUserBag;
import com.onelive.common.mybatis.mapper.master.mem.MemUserBagMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserBagMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-24
 */
@Service
public class MemUserBagServiceImpl extends ServiceImpl<MemUserBagMapper, MemUserBag> implements MemUserBagService {

    @Resource
    private SlaveMemUserBagMapper slaveMemUserBagMapper;


    @Override
    public PageInfo<AppUserLiveBagVO> getUserBagList() {
        LangDTO dto = new LangDTO();
        dto.setUserId(LoginInfoUtil.getUserId());
        dto.setLang(LoginInfoUtil.getLang());
        List<AppUserLiveBagVO> list = slaveMemUserBagMapper.getUserBagList(dto);
        return new PageInfo<>(list);
    }
}
