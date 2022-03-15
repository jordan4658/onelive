package com.onelive.manage.service.lottery.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.LotteryCategory;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryCategoryMapper;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryCategoryMapper;
import com.onelive.manage.service.lottery.LotteryCategoryService;
import com.onelive.manage.utils.redis.LotteryBusinessRedisUtils;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 彩种分类 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
public class LotteryCategoryServiceImpl extends ServiceImpl<LotteryCategoryMapper, LotteryCategory> implements LotteryCategoryService {

    @Resource
    private SlaveLotteryCategoryMapper slaveLotteryCategoryMapper;

    @Override
    public LotteryCategory getByCategoryId(Integer categoryId) {
        QueryWrapper<LotteryCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryCategory::getCategoryId,categoryId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<LotteryCategory> queryAllCategory(String type) {
        List<LotteryCategory> lotteryCategoryList = getLotteryCategorysFromCache();
        if (lotteryCategoryList != null && lotteryCategoryList.size() > 0) {
            if (StringUtils.isNotEmpty(type)) {
                lotteryCategoryList = lotteryCategoryList
                        .parallelStream()
                        .filter(item -> type.equals(item.getType()))
                        .collect(Collectors.toList());
            }
            return lotteryCategoryList;
        }
        return new ArrayList<>();
    }




    @Override
    public List<LotteryCategory> getLotteryCategorysFromCache() {
        List<LotteryCategory> lotteryCategoryList = SystemRedisUtils.getLotteryCategoryList();
        if (CollectionUtils.isEmpty(lotteryCategoryList)) {
            QueryWrapper<LotteryCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(LotteryCategory::getIsDelete,false);
            queryWrapper.lambda().orderByDesc(LotteryCategory::getSort);
            lotteryCategoryList = this.list(queryWrapper);
            SystemRedisUtils.addLotteryCategoryList(lotteryCategoryList);
            SystemRedisUtils.addLotteryCategoryMap(lotteryCategoryList);
        } else {
            Map<Integer, LotteryCategory> lotteryCategoryMap = SystemRedisUtils.getLotteryCategoryMap();
            if (CollectionUtil.isEmpty(lotteryCategoryMap)) {
                SystemRedisUtils.addLotteryCategoryMap(lotteryCategoryList);
            }
        }
        return lotteryCategoryList;
    }


    /**
     * 查询所有分类信息, 带国际化内容
     * @param lang
     * @return
     */
    @Override
    public List<LotteryCategory> queryAllCategoryWithLang(String lang) {
        List<LotteryCategory> lotteryCategoryList = LotteryBusinessRedisUtils.getLotteryCategoryListByLang(lang);
        if (CollectionUtils.isEmpty(lotteryCategoryList)) {
            lotteryCategoryList = slaveLotteryCategoryMapper.listAllCategoryWithLang(lang);
            LotteryBusinessRedisUtils.setLotteryCategoryListWithLang(lotteryCategoryList,lang);
            LotteryBusinessRedisUtils.setLotteryCategoryMap(lotteryCategoryList,lang);
        } else {
            Map<Integer, LotteryCategory> lotteryCategoryMap = LotteryBusinessRedisUtils.getLotteryCategoryMapWithLang(lang);
            if (CollectionUtil.isEmpty(lotteryCategoryMap)) {
                LotteryBusinessRedisUtils.setLotteryCategoryMap(lotteryCategoryList,lang);
            }
        }
        return lotteryCategoryList;
    }



}
