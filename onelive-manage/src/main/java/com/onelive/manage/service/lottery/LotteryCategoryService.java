package com.onelive.manage.service.lottery;

import com.onelive.common.mybatis.entity.LotteryCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 彩种分类 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
public interface LotteryCategoryService extends IService<LotteryCategory> {

    /**
     * 获取产品大类id
     * @param categoryId
     * @return
     */
    LotteryCategory getByCategoryId(Integer categoryId);

    /**
     * 查询全部分类列表
     *
     * @return
     */
    List<LotteryCategory> queryAllCategory(String type);

    /**
     * 获取彩票大类缓存
     * @return
     */
    List<LotteryCategory> getLotteryCategorysFromCache();

    /**
     * 查询所有分类,带国际化内容
     * @param lang
     * @return
     */
    List<LotteryCategory> queryAllCategoryWithLang(String lang);
}
