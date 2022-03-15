package com.onelive.manage.service.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.lottery.LotteryPlayAllDTO;
import com.onelive.common.model.vo.lottery.LotteryPlayExVo;
import com.onelive.common.mybatis.entity.LotteryPlay;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryPlayMapper;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryPlayMapper;
import com.onelive.manage.service.lottery.LotteryPlayService;
import com.onelive.manage.utils.redis.LotteryBusinessRedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 彩种玩法 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Service
public class LotteryPlayServiceImpl extends ServiceImpl<LotteryPlayMapper, LotteryPlay> implements LotteryPlayService {

    @Resource
    private SlaveLotteryPlayMapper slaveLotteryPlayMapper;

    @Override
    public SlaveLotteryPlayMapper getSlave() {
        return slaveLotteryPlayMapper;
    }

    @Override
    public Map<Integer, Map<Integer, Map<Integer, LotteryPlayExVo>>> selectAllLotteryPlayByCategoryIdsWithLang(
            List<Integer> categoryIds, String lang) {
        List<LotteryPlayExVo> lotteryPlayList = getLotteryPlaysFromCacheWithLang(lang);
        if (!CollectionUtils.isEmpty(categoryIds)) {
            lotteryPlayList = lotteryPlayList.parallelStream()
                    .filter(item -> categoryIds.contains(item.getCategoryId())).collect(Collectors.toList());
        }

        // Map<lotteryId,Map<parentId,Map<lotteryPlayId,LotteryPlayExVo>>
        Map<Integer, Map<Integer, Map<Integer, LotteryPlayExVo>>> lotteryPlayMap = new HashMap<>();
        for (LotteryPlayExVo lotteryPlay : lotteryPlayList) {
            Map<Integer, Map<Integer, LotteryPlayExVo>> parentPlayMap;
            Map<Integer, LotteryPlayExVo> playMap;
            int parentId = lotteryPlay.getParentId();
            int id = lotteryPlay.getId();
            boolean isParent = true;//所有玩法都移到第一层, 不嵌套  // parentId == 0;
            int keyId = isParent ? id : parentId;

            if (lotteryPlayMap.containsKey(lotteryPlay.getLotteryId())) {
                parentPlayMap = lotteryPlayMap.get(lotteryPlay.getLotteryId());
            } else {
                parentPlayMap = new HashMap<>();
            }
            if (parentPlayMap.containsKey(keyId)) {
                playMap = parentPlayMap.get(keyId);
            } else {
                playMap = new HashMap<>();
            }
            playMap.put(id, lotteryPlay);
            parentPlayMap.put(keyId, playMap);
            lotteryPlayMap.put(lotteryPlay.getLotteryId(), parentPlayMap);
        }
        return lotteryPlayMap;
    }


    @Override
    public Map<Integer, List<LotteryPlayAllDTO>> selectAllLotteryPlayDTOByCategoryIdsWithLang(List<Integer> categoryIds, String lang) {

        Map<Integer, List<LotteryPlayAllDTO>> lotteryPlayAllDTOMap = new HashMap<>();
        Map<Integer, Map<Integer, Map<Integer, LotteryPlayExVo>>> lotteryPlayMap = this
                .selectAllLotteryPlayByCategoryIdsWithLang(categoryIds,lang);

       // Map<lotteryId,Map<parentId,Map<lotteryPlayId,LotteryPlayExVo>>
        for (Map.Entry<Integer, Map<Integer, Map<Integer, LotteryPlayExVo>>> entry : lotteryPlayMap.entrySet()) {
            // 彩种下的所有玩法
            List<LotteryPlayAllDTO> lotteryPlays = new ArrayList<>();
            for (Map.Entry<Integer, Map<Integer, LotteryPlayExVo>> parent : entry.getValue().entrySet()) {
                LotteryPlayAllDTO parentDTO = null;
                List<LotteryPlayAllDTO> childrenDTOList = new ArrayList<>();
                for (Map.Entry<Integer, LotteryPlayExVo> child : parent.getValue().entrySet()) {
                    if (parent.getKey().equals(child.getKey())) {
                        parentDTO = new LotteryPlayAllDTO();
                        BeanUtils.copyProperties(child.getValue(), parentDTO);
                        continue;
                    }
                    LotteryPlayAllDTO dto = new LotteryPlayAllDTO();
                    BeanUtils.copyProperties(child.getValue(), dto);
                    childrenDTOList.add(dto);
                }
                if (null != parentDTO) {
                    parentDTO.setPlayChildren(childrenDTOList);
                    lotteryPlays.add(parentDTO);
                }
            }
            lotteryPlayAllDTOMap.put(entry.getKey(), lotteryPlays);
        }

        return lotteryPlayAllDTOMap;
    }

    @Override
    public LotteryPlay queryLotteryPlayByPlayId(Integer playId) {
        QueryWrapper<LotteryPlay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryPlay::getPlayTagId,playId);
        queryWrapper.lambda().eq(LotteryPlay::getIsDelete,false);
        return  this.slaveLotteryPlayMapper.selectOne(queryWrapper);
    }

    @Override
    public List<LotteryPlayExVo> listLotteryPlayWithLang(Integer lotteryId, String lang) {
        return slaveLotteryPlayMapper.listLotteryPlayWithLang(lang,lotteryId);
    }

    /**
     * 获取所有彩种玩法的设置信息
     * @return
     * @param lang
     */
    private List<LotteryPlayExVo> getLotteryPlaysFromCacheWithLang(String lang) {
        List<LotteryPlayExVo> lotteryPlayList = LotteryBusinessRedisUtils.getLotteryPlayListWithLang(lang);// SystemRedisUtils.getLotteryPlayList();
        if (!CollectionUtils.isEmpty(lotteryPlayList)) {
            return lotteryPlayList;
        }
        lotteryPlayList = this.slaveLotteryPlayMapper.listLotteryPlayWithLang(lang,null);
        LotteryBusinessRedisUtils.setLotteryPlayListWithLang(lotteryPlayList,lang);
        return lotteryPlayList;
    }

}
