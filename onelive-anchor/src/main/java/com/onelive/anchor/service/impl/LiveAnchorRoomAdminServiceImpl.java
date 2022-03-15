package com.onelive.anchor.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.LiveAnchorRoomAdminService;
import com.onelive.common.mybatis.entity.LiveAnchorRoomAdmin;
import com.onelive.common.mybatis.mapper.master.live.LiveAnchorRoomAdminMapper;

/**
 * <p>
 * 主播管理员配置表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-16
 */
@Service
public class LiveAnchorRoomAdminServiceImpl extends ServiceImpl<LiveAnchorRoomAdminMapper, LiveAnchorRoomAdmin>
		implements LiveAnchorRoomAdminService {

}
