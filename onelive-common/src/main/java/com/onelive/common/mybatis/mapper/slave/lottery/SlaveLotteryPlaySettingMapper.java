package com.onelive.common.mybatis.mapper.slave.lottery;

import com.onelive.common.mybatis.entity.LotteryPlaySetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface SlaveLotteryPlaySettingMapper extends BaseMapper<LotteryPlaySetting> {

    List<LotteryPlaySetting> listAllPlaySettingsWithLang(@Param("lang") String lang);
}
