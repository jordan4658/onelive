package com.onelive.api.service.live.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.live.LiveGameTagService;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;
import com.onelive.common.mybatis.entity.LiveGameTag;
import com.onelive.common.mybatis.mapper.master.live.LiveGameTagMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveGameTagMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
@Service
public class LiveGameTagServiceImpl extends ServiceImpl<LiveGameTagMapper, LiveGameTag> implements LiveGameTagService {
    @Resource
    private SlaveLiveGameTagMapper slaveLiveGameTagMapper;

    @Override
    public List<LiveGameTag> listWithLang() {
        CurrentUserCountryLangDTO dto = new CurrentUserCountryLangDTO();
        List<LiveGameTag> list = slaveLiveGameTagMapper.listWithLang(dto);
        if(CollectionUtils.isEmpty(list) && !LangConstants.LANG_CN.equals(dto.getLang()))  {
          dto.setLang(LangConstants.LANG_CN);
          //中文再查一次
            list = slaveLiveGameTagMapper.listWithLang(dto);
        }
        return list;
    }
}
