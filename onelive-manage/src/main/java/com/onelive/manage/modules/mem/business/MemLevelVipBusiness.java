package com.onelive.manage.modules.mem.business;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.mem.MemLevelVipSaveReq;
import com.onelive.common.model.vo.mem.MemUserLevelSelectVO;
import com.onelive.common.model.vo.mem.MemUserLevelVO;
import com.onelive.common.model.vo.mem.MemUserLevelVipInfoVO;
import com.onelive.common.mybatis.entity.MemLevelVip;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.common.utils.redis.UserBusinessRedisUtils;
import com.onelive.manage.service.mem.MemLevelVipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 用户等级
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemLevelVipBusiness {

    @Resource
    private MemLevelVipService memLevelVipService;

    /**
     * 获取用户等级列表
     *
     * @param req
     * @return
     */
    public PageInfo<MemUserLevelVO> getList(PageReq req) {
        PageInfo<MemLevelVip> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> {
            memLevelVipService.list();
        });
        return PageInfoUtil.pageInfo2DTO(pageInfo, MemUserLevelVO.class);
    }

    /**
     * 获取最大用户等级
     *
     * @return
     */
    public ResultInfo<Integer> getMaxVipLevel() {
        Integer max = memLevelVipService.getMaxVipLevel();
        if (max == null) {
            max = -2;
        }
        return ResultInfo.ok(max);
    }


    /**
     * 保存用户等级
     *
     * @param req
     * @param loginUser
     * @return
     */
    public ResultInfo<Boolean> saveLevel(MemLevelVipSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        //限制金额
        BigDecimal promotionRecharge = req.getPromotionRecharge();
        if (promotionRecharge == null) {
            promotionRecharge = new BigDecimal("0");
        }
        promotionRecharge = promotionRecharge.setScale(4, RoundingMode.HALF_UP);
        String str = promotionRecharge.toString();
        String[] data = str.split("\\.");
        if (data[0].length() > 12) {
            throw new BusinessException("金额设置过大!");
        }

        Long id = req.getId();
        MemLevelVip vip = new MemLevelVip();
        BeanUtil.copyProperties(req, vip);
        if (id == null) {
            vip.setCreateUser(loginUser.getAccLogin());
            memLevelVipService.save(vip);
        } else {
            vip.setUpdateUser(loginUser.getAccLogin());
            memLevelVipService.updateById(vip);
        }
        //更新缓存
        UserBusinessRedisUtils.setVipLevelInfo(vip.getLevelWeight(), JSON.toJSONString(vip));
        return ResultInfo.ok(Boolean.TRUE);
    }


    /**
     * 用于选择的等级列表
     *
     * @return
     */
    public ResultInfo<PageInfo<MemUserLevelSelectVO>> getSelectList() {
        List<MemLevelVip> vipList = memLevelVipService.list();
        List<MemUserLevelSelectVO> list = BeanCopyUtil.copyCollection(vipList, MemUserLevelSelectVO.class);
        PageInfo<MemUserLevelSelectVO> pageInfo = new PageInfo<>(list);
        return ResultInfo.ok(pageInfo);
    }

    /**
     * 查询用户等级信息
     *
     * @param req
     * @return
     */
    public MemUserLevelVipInfoVO getLevelInfo(LongIdReq req) {
        Long id = req.getId();
        if (id == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        MemLevelVip levelVip = memLevelVipService.getById(id);
        MemUserLevelVipInfoVO vo = new MemUserLevelVipInfoVO();
        BeanCopyUtil.copyProperties(levelVip, vo);
        return vo;
    }
}
