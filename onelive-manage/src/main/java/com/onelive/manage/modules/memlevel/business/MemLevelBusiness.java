package com.onelive.manage.modules.memlevel.business;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.finance.memlevel.MemLevelDTO;
import com.onelive.common.model.req.finance.memlevel.MemLevelListReq;
import com.onelive.common.model.req.finance.memlevel.MemLevelSaveReq;
import com.onelive.common.model.req.finance.memlevel.MemLevelUpdateReq;
import com.onelive.common.model.vo.finance.memlevel.MemLevelVO;
import com.onelive.common.mybatis.entity.MemLevel;
import com.onelive.manage.converter.finance.MemLevelConverter;
import com.onelive.manage.service.finance.MemLevelService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * MemLevelBusiness
 *
 * @author kevin
 * @version 1.0.0
 * @since 2021年10月26日 下午11:23:31
 */
@Component
public class MemLevelBusiness {

    @Resource
    private MemLevelService memLevelService;
    @Resource
    private MemLevelConverter memLevelConverter;

    /**
     * getList
     *
     * @param memLevelListReq
     * @return PageInfo
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午4:55:42
     */
    public PageInfo<MemLevelVO> getList(MemLevelListReq memLevelListReq) {
        PageInfo<MemLevelDTO> pageInfo = memLevelService.getPage(memLevelListReq);
        return memLevelConverter.toVOPage(pageInfo);
    }


    /**
     * getInfo
     *
     * @param id
     * @return MemLevelVo
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午5:07:45
     */
    public MemLevelVO getInfo(Long id) {
        return memLevelConverter.toVO(memLevelService.getById(id));
    }


    /**
     * save
     *
     * @param memLevelSaveReq
     * @return Boolean
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午7:28:15
     */
    public Boolean save(MemLevelSaveReq memLevelSaveReq) {
        nameValid(memLevelSaveReq.getName());
        return memLevelService.save(memLevelConverter.saveReqToEntity(memLevelSaveReq));
    }

    /**
     * update
     *
     * @param memLevelUpdateReq
     * @return Boolean
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午7:28:15
     */
    public Boolean update(MemLevelUpdateReq memLevelUpdateReq) {
        nameValid(memLevelUpdateReq.getName());
        return memLevelService.updateById(memLevelConverter.updateReqToEntity(memLevelUpdateReq));
    }


    /**
     * delete
     *
     * @param ids
     * @return Boolean
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午7:30:31
     */
    public Boolean delete(Long[] ids) {
        return memLevelService.removeByIds(Arrays.asList(ids));
    }

    private void nameValid(String name) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        List<MemLevel> result = memLevelService.list(new QueryWrapper<>(new MemLevel().setName(name)));
        if (!CollectionUtils.isEmpty(result)) {
            throw new BusinessException(StatusCode.NAME_REPEAT);
        }

    }
}