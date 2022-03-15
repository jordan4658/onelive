package com.onelive.api.service.live.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.live.LiveBagService;
import com.onelive.common.mybatis.entity.LiveBag;
import com.onelive.common.mybatis.mapper.master.live.LiveBagMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveBagMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-23
 */
@Service
public class LiveBagServiceImpl extends ServiceImpl<LiveBagMapper, LiveBag> implements LiveBagService {

    @Resource
    private SlaveLiveBagMapper slaveLiveBagMapper;

    @Override
    public List<LiveBag> getListWithLang() {
        String lang = LoginInfoUtil.getLang();
        return slaveLiveBagMapper.getListWithLang(lang);
    }
}
