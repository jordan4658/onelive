package com.onelive.manage.service.index.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.LiveColumn;
import com.onelive.common.mybatis.mapper.master.index.LiveColumnMapper;
import com.onelive.manage.service.index.LiveColumnService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-20
 */
@Service
public class LiveColumnServiceImpl extends ServiceImpl<LiveColumnMapper, LiveColumn> implements LiveColumnService {

}
