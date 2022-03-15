package com.onelive.common.mybatis.mapper.slave.lottery;

import com.onelive.common.mybatis.entity.LotteryCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 彩种分类 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface SlaveLotteryCategoryMapper extends BaseMapper<LotteryCategory> {

    List<LotteryCategory> listAllCategoryWithLang(@Param("lang") String lang);
}
