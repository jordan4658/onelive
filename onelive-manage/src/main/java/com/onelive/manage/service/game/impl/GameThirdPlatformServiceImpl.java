package com.onelive.manage.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.GameThirdPlatform;
import com.onelive.common.mybatis.mapper.master.game.GameThirdPlatformMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameThirdPlatformMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.game.GameThirdPlatformService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 第三方游戏平台 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-20
 */
@Service
public class GameThirdPlatformServiceImpl extends ServiceImpl<GameThirdPlatformMapper, GameThirdPlatform> implements GameThirdPlatformService {

    @Resource
    private SlaveGameThirdPlatformMapper slaveGameThirdPlatformMapper;

    @Override
    public GameThirdPlatform getByCode(String platformCode) {
        QueryWrapper<GameThirdPlatform> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThirdPlatform::getIsDelete, false)
                .eq(GameThirdPlatform::getPlatformCode, platformCode)
                .eq(GameThirdPlatform::getMerchantCode, LoginInfoUtil.getMerchantCode());
        return slaveGameThirdPlatformMapper.selectOne(queryWrapper);
    }
}
