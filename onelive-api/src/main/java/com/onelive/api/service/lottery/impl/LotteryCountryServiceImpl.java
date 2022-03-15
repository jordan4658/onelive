package com.onelive.api.service.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.lottery.LotteryCountryService;
import com.onelive.common.model.vo.lottery.LotteryGameListVO;
import com.onelive.common.model.vo.lottery.LotteryRoomVO;
import com.onelive.common.mybatis.entity.LotteryCountry;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryCountryMapper;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryCountryMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.upload.AWSS3Util;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 彩票-国家对应表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
public class LotteryCountryServiceImpl extends ServiceImpl<LotteryCountryMapper, LotteryCountry> implements LotteryCountryService {

    @Resource
    private SlaveLotteryCountryMapper slaveLotteryCountryMapper;

    @Override
    public List<LotteryRoomVO> getLotteryRoomList() {

        List<LotteryRoomVO> voList  = new ArrayList<>();
        QueryWrapper<LotteryCountry> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(LotteryCountry::getCountryId, LoginInfoUtil.getCountryId());
        List<LotteryCountry> list = slaveLotteryCountryMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            Iterator<LotteryCountry> iterator = list.iterator();
            while (iterator.hasNext()){
                LotteryRoomVO vo = new LotteryRoomVO();
                LotteryCountry entity =  iterator.next();
                vo.setId(entity.getLotteryId());
                vo.setName(entity.getAliasName());
                vo.setIcon(AWSS3Util.getAbsoluteUrl(entity.getIconUrl()));
                voList.add(vo);
            }
        }
        return voList;
    }

    @Override
    public List<LotteryGameListVO> getLotteryGameList(String code) {
        List<LotteryGameListVO> voList = new ArrayList<>();
       /* QueryWrapper<LotteryCountry> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(LotteryCountry::getCountryId, LoginInfoUtil.getCountryId()).eq(LotteryCountry::getIsForbid,false);
        List<LotteryCountry> list = slaveLotteryCountryMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            Iterator<LotteryCountry> iterator = list.iterator();
            while (iterator.hasNext()){
                LotteryGameListVO vo = new LotteryGameListVO();
                LotteryCountry entity =  iterator.next();
                vo.setCateId(entity.getLotteryCateId());
                vo.setGameNo(entity.getLotteryId().toString());
                if(StringUtils.isBlank(code)){
                    vo.setCode(GameCodeEnums.LOTTERY.getCode());
                }else{
                    vo.setCode(code);
                }
                vo.setAliasName(entity.getAliasName());
                vo.setGameType(GameCodeEnums.LOTTERY.getCode());
                vo.setIsCanJump(false);
                vo.setIsThird(false);
                vo.setIconUrl(AWSS3Util.getAbsoluteUrl(entity.getIconUrl()));
                voList.add(vo);
            }
        }*/

        return voList;
    }

    @Override
    public LotteryCountry getLotteryByCode(Integer gameId) {
        QueryWrapper<LotteryCountry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryCountry::getId, gameId);
        return slaveLotteryCountryMapper.selectOne(queryWrapper);
    }
}
