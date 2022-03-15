package com.onelive.manage.service.lottery.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.enums.LotteryTypeEnum;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.model.dto.lottery.*;
import com.onelive.common.model.vo.lottery.LotteryPlayOddsExVo;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryMapper;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryMapper;
import com.onelive.common.utils.lottery.CaipiaoUtils;
import com.onelive.manage.service.lottery.*;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.redis.LotteryBusinessRedisUtils;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 彩种 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
@Slf4j
public class LotteryServiceImpl extends ServiceImpl<LotteryMapper, Lottery> implements LotteryService {
    @Resource
    private SlaveLotteryMapper slaveLotteryMapper;
    @Resource
    private LotteryCategoryService lotteryCategoryService;
    @Resource
    private LotteryPlayService lotteryPlayService;
    @Resource
    private LotteryPlaySettingService lotteryPlaySettingService;
    @Resource
    private LotteryPlayOddsService lotteryPlayOddsService;
    @Resource
    private SysParameterService sysParameterService;

    @Override
    public SlaveLotteryMapper getSlave() {
        return slaveLotteryMapper;
    }

    @Override
    public List<LotteryInfoDTO> queryLotteryAllInfo(String lang) {
        List<LotteryInfoDTO> list = LotteryBusinessRedisUtils.getLotteryAllInfoWithLang(LotteryTypeEnum.LOTTERY.name(),lang);
        if (CollectionUtil.isNotEmpty(list)) {
            return list;
        }
        long start = System.currentTimeMillis();
        //result
        List<LotteryInfoDTO> lotteryInfoList = new ArrayList<>();
        //彩票分类，cache 后续使用
        Map<Integer, LotteryAllDTO> lotteryAllDTOMap = new HashMap<>();
        //查询所有彩种
        List<LotteryCategory> categoryList = lotteryCategoryService.queryAllCategoryWithLang(lang); //lotteryCategoryService.queryAllCategory(type);
        //查询彩票 结构:[categoryId,LotteryList]
        Map<Integer, List<Lottery>> lotteryMap = this.queryLotteryGroupByCategoryIdWithLang(lang);
        //封装结果：填充彩种，彩票信息
        assembleLotteryInfo(categoryList, lotteryMap, lotteryInfoList, lotteryAllDTOMap);

        //查询玩法，一级玩法；二级玩法
        List<Integer> categoryIdList = categoryList.stream().map(item -> item.getCategoryId()).collect(Collectors.toList());
        Map<Integer, List<LotteryPlayAllDTO>> lotteryPlayAllDTOMap = lotteryPlayService.selectAllLotteryPlayDTOByCategoryIdsWithLang(categoryIdList,lang);
        //查询玩法设置
        List<Integer> playIdList = getPlayIdList(lotteryPlayAllDTOMap);
        Map<Integer, LotteryPlaySetting> playSettingMap = lotteryPlaySettingService.queryLotteryPlaySettingMapWithLang(playIdList,lang);
        //查询赔率
        List<Integer> playSettingIdList = playSettingMap.values().stream().map(item -> item.getId()).collect(Collectors.toList());
        Map<Integer, List<LotteryPlayOddsExVo>> playOddsMap = lotteryPlayOddsService.selectOddsListBySettingIdListWithLang(playSettingIdList,lang);
        //封装结果：填充玩法，玩法设置，赔率等信息
        assembleLotteryPlayInfo(lotteryInfoList, lotteryPlayAllDTOMap, playSettingMap, playOddsMap, lotteryAllDTOMap);

        //sort 彩种/彩票 按 sort 倒序排序
        lotteryInfoList.sort(Comparator.comparing(LotteryInfoDTO::getSort).reversed());
        //彩票，玩法，子玩法倒序排序
        lotteryInfoList.forEach(item -> {
                    if (CollectionUtil.isNotEmpty(item.getLotterys())) {
                        item.getLotterys().sort(Comparator.comparing(LotteryAllDTO::getSort).reversed());
                        item.getLotterys().forEach(play -> {
                            if (null != play.getPlays() && play.getPlays().size() > 1) {
                                play.getPlays().sort(Comparator.comparing(LotteryPlayAllDTO::getSort).reversed());
                                play.getPlays().forEach(parent -> {
                                    if (null != parent.getPlayChildren() && parent.getPlayChildren().size() > 1) {
                                        parent.getPlayChildren().sort(Comparator.comparing(LotteryPlayAllDTO::getSort).reversed());
                                    }
                                });
                            }
                        });
                    }
                }
        );

        long end = System.currentTimeMillis();
        log.info("method queryLotteryAllInfoNew used times:{} ms", end - start);
        //缓存信息
        LotteryBusinessRedisUtils.setLotteryAllInfoWithLang(LotteryTypeEnum.LOTTERY.name(),lotteryInfoList,lang);
        return lotteryInfoList;
    }

    @Override
    public Map<Integer, List<Lottery>> queryLotteryGroupByCategoryIdWithLang(String lang) {
        List<Lottery> lotteries = queryAllLotteryFromCacheWithLang(false,lang);
        Map<Integer, List<Lottery>> lotteryMap = new HashMap<>();
        for (Lottery lottery : lotteries) {
            List<Lottery> lotteryList;
            if (lotteryMap.containsKey(lottery.getCategoryId())) {
                lotteryList = lotteryMap.get(lottery.getCategoryId());
            } else {
                lotteryList = new ArrayList<>();
            }
            lotteryList.add(lottery);
            lotteryMap.put(lottery.getCategoryId(), lotteryList);
        }
        return lotteryMap;
    }


    @Override
    public void updateLotteryVersionZIP(String zipDownURL, String lang) {
        String VERSION = "1";
        String paramCode = SysParamEnum.LOTTERY_VERSION_ZIP_URL.getCode() + "_" + lang;
        SysParameter sysParameter = sysParameterService.getByCode(paramCode);
        if(sysParameter==null){
            sysParameter = new SysParameter();
            sysParameter.setRemark(VERSION);
            sysParameter.setParamCode(paramCode);
            sysParameter.setParamName(lang+"彩种压缩包路径");
        }else{
            sysParameter.setRemark(String.valueOf(Integer.parseInt(sysParameter.getRemark()) + 1));
        }
        //更新URL+版本号
        sysParameter.setParamValue(zipDownURL);
        if(VERSION.equals(sysParameter.getRemark())){
            sysParameterService.save(sysParameter);
        }else {
            sysParameterService.updateSysParameter(sysParameter);
        }
        SystemRedisUtils.deleteLotteryCaches();
    }

    @Override
    public Lottery getLotteryByLotteryId(Integer lotteryId) {
        QueryWrapper<Lottery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Lottery::getLotteryId,lotteryId);
        queryWrapper.lambda().eq(Lottery::getIsDelete,false);
        return this.slaveLotteryMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Lottery> getLotteryByCategoryId(Integer categoryId) {
        QueryWrapper<Lottery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Lottery::getCategoryId,categoryId);
        queryWrapper.lambda().eq(Lottery::getIsDelete,false);
        return this.slaveLotteryMapper.selectList(queryWrapper);
    }

    @Override
    public List<Lottery> queryLotteryWithLang(String lang) {
        return slaveLotteryMapper.queryLotteryWithLang(lang);
    }


    private List<Lottery> queryAllLotteryFromCacheWithLang(boolean fetchDeletedRecord,String lang) {
        List<Lottery> list = LotteryBusinessRedisUtils.getAllLotteryWithLang(lang);
        if (CollectionUtils.isEmpty(list)) {
            LotteryQueryDTO dto = new LotteryQueryDTO();
            if(!fetchDeletedRecord){
                dto.setIsDelete(false);
            }
            dto.setLang(lang);
            list =  this.slaveLotteryMapper.listAllLotteryWithLang(dto);
            LotteryBusinessRedisUtils.setAllLotteryWithLang(list,lang);

            //cache as map
            Map<Integer, Lottery> lotteryMap = new HashMap<>();
            for (Lottery lottery : list) {
                lotteryMap.put(lottery.getId(), lottery);
                lotteryMap.put(lottery.getLotteryId(), lottery);
            }
            LotteryBusinessRedisUtils.setLotteryMapWithLang(lotteryMap,lang);
        }
        return list;
    }


    private void assembleLotteryInfo(List<LotteryCategory> categoryList,
                                     Map<Integer, List<Lottery>> lotteryMap,
                                     List<LotteryInfoDTO> lotteryInfoList,
                                     Map<Integer, LotteryAllDTO> lotteryAllDTOMap) {

        for (LotteryCategory category : categoryList) {
            LotteryInfoDTO lotteryInfo = new LotteryInfoDTO();
            lotteryInfo.setCateID(String.valueOf(category.getCategoryId()));
            lotteryInfo.setCateName(category.getName());
            lotteryInfo.setIntro(category.getAlias());
            lotteryInfo.setSort(category.getSort());
            lotteryInfo.setIsWork(category.getIsWork());
            List<Lottery> lotteries = lotteryMap.get(category.getCategoryId());
            if (null == lotteries || lotteries.size() == 0) {
                continue;
            }
            List<LotteryAllDTO> lotteryAllDTOList = new ArrayList<>();
            for (Lottery lottery : lotteries) {
                LotteryAllDTO lotteryAllDTO = new LotteryAllDTO();
                lotteryAllDTO.setId(lottery.getId());
                lotteryAllDTO.setName(lottery.getName());
                lotteryAllDTO.setCategoryId(lottery.getCategoryId());
                lotteryAllDTO.setLotteryId(lottery.getLotteryId());
                lotteryAllDTO.setIsWork(lottery.getIsWork());
                lotteryAllDTO.setEndTime(lottery.getEndTime());
                lotteryAllDTO.setMaxOdds(lottery.getMaxOdds());
                lotteryAllDTO.setMinOdds(lottery.getMinOdds());
                lotteryAllDTO.setSort(lottery.getSort());
                lotteryAllDTOList.add(lotteryAllDTO);
                lotteryAllDTOMap.put(lottery.getLotteryId(), lotteryAllDTO);
            }
            lotteryInfo.setLotterys(lotteryAllDTOList);
            lotteryInfoList.add(lotteryInfo);
        }
    }


    private void getAllPlayId(LotteryPlayAllDTO dto, List<Integer> playIdList) {
        playIdList.add(dto.getId());
        List<LotteryPlayAllDTO> playChildren = dto.getPlayChildren();
        if (null != playChildren && playChildren.size() > 0) {
            for (LotteryPlayAllDTO child : playChildren) {
                getAllPlayId(child, playIdList);
            }
        }
    }



    private List<Integer> getPlayIdList(Map<Integer, List<LotteryPlayAllDTO>> lotteryPlayAllDTOMap) {
        List<Integer> playIdList = new ArrayList<>();
        for (Map.Entry<Integer, List<LotteryPlayAllDTO>> entry : lotteryPlayAllDTOMap.entrySet()) {
            for (LotteryPlayAllDTO dto : entry.getValue()) {
                getAllPlayId(dto, playIdList);
            }
        }
        playIdList = playIdList.stream().distinct().collect(Collectors.toList());
        log.info("getPlayIdList size:{}.", playIdList.size());
        return playIdList;
    }


    public void assembleLotterySettingInfo(LotteryPlayAllDTO playAllDTO,
                                           Map<Integer, LotteryPlaySetting>  playSettingMap,
                                           Map<Integer, List<LotteryPlayOddsExVo>> playOddsMap,
                                           Map<Integer, LotteryAllDTO> lotteryAllDTOMap,
                                           Double defaultDivisor) {
        LotteryPlaySetting playSetting = playSettingMap.get(playAllDTO.getId());
        if (null == playSetting) {
            log.info("lotteryPlay have no playSetting by lotteryPlayId:{}", playAllDTO.getId());
            return;
        }
        LotteryPlaySettingDTO playSettingDTO = new LotteryPlaySettingDTO();
        BeanUtils.copyProperties(playSettingMap.get(playAllDTO.getId()), playSettingDTO);
        playAllDTO.setSetting(playSettingDTO);

        //assembleLotteryOddsInfo
        List<LotteryPlayOddsExVo> playOdds = playOddsMap.get(playSettingDTO.getId());
        if (null == playOdds || playOdds.size() == 0) {
            log.info("lotterySetting have no playOdds by lotteryPlayId:{},settingId:{}", playAllDTO.getId(), playSettingDTO.getId());
            return;
        }

        List<LotteryPlayAllOddsDTO> oddsList = new ArrayList<>();
        for (LotteryPlayOddsExVo odds : playOdds) {
            //如果扩展ID不为空, 使用扩展ID作为settingId
            Integer settingId = odds.getSettingId();
            Integer exSettingId = odds.getExSettingId();
            if(exSettingId!=null){
                settingId = exSettingId;
            }
            LotteryPlayAllOddsDTO playAllOdds = LotteryPlayAllOddsDTO.newInstance(odds.getId(), settingId, odds.getName(), odds.getShowName(),null);
            LotteryAllDTO lotteryAllDTO = lotteryAllDTOMap.get(playAllDTO.getLotteryId());
            Double divisor = lotteryAllDTO.getMaxOdds();
            divisor = null == divisor || divisor <= 0 ? defaultDivisor : divisor;
            // 获取总注数/中奖注数
            String winCount = odds.getWinCount();
            String totalCount = odds.getTotalCount();

            // 判断是否设置赔率
            if (!(StringUtils.isBlank(winCount) || StringUtils.isBlank(totalCount))) {
                playAllOdds.setOdds(CaipiaoUtils.getLotteryPlayOdds(totalCount, winCount, divisor));
            }
            oddsList.add(playAllOdds);
        }

        playAllDTO.setOddsList(oddsList);
    }



    private void assembleLotteryPlayInfo(List<LotteryInfoDTO> lotteryInfoList,
                                         Map<Integer, List<LotteryPlayAllDTO>> lotteryPlayAllDTOMap,
                                         Map<Integer, LotteryPlaySetting>  playSettingMap,
                                         Map<Integer, List<LotteryPlayOddsExVo>> playOddsMap,
                                         Map<Integer, LotteryAllDTO> lotteryAllDTOMap) {
        SysParameter systemInfo = sysParameterService.getByCode(SysParamEnum.REGISTER_MEMBER_ODDS);
        Double defaultDivisor = Double.parseDouble(systemInfo.getParamValue());
        for (LotteryInfoDTO lotteryInfo : lotteryInfoList) {
            for (LotteryAllDTO lotteryAllDTO : lotteryInfo.getLotterys()) {
                lotteryAllDTO.setPlays(lotteryPlayAllDTOMap.get(lotteryAllDTO.getLotteryId()));
                if (CollectionUtils.isEmpty(lotteryAllDTO.getPlays())) {
                    continue;
                }
                for (LotteryPlayAllDTO playAllDTO : lotteryAllDTO.getPlays()) {
                    if (CollectionUtils.isEmpty(playAllDTO.getPlayChildren())) {
                        assembleLotterySettingInfo(playAllDTO, playSettingMap, playOddsMap, lotteryAllDTOMap, defaultDivisor);
                        continue;
                    }
                    for (LotteryPlayAllDTO childPlayDTO : playAllDTO.getPlayChildren()) {
                        assembleLotterySettingInfo(childPlayDTO, playSettingMap, playOddsMap, lotteryAllDTOMap, defaultDivisor);
                    }
                }
            }
        }
    }


}
