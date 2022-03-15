package com.onelive.api.service.game.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.game.GameUserService;
import com.onelive.common.mybatis.entity.GameUser;
import com.onelive.common.mybatis.mapper.master.game.GameUserMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-22
 */
@Service
public class GameUserServiceImpl extends ServiceImpl<GameUserMapper, GameUser> implements GameUserService {

}
