package com.onelive.manage.modules.operate.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.operate.FirstRechargeActivityConfigDTO;
import com.onelive.common.model.dto.operate.PopularizeActivityConfigDTO;
import com.onelive.common.model.dto.operate.RedPacketActivityConfigDTO;
import com.onelive.common.model.dto.operate.RegisterReturnActivityConfigDTO;
import com.onelive.common.model.req.operate.*;
import com.onelive.common.model.vo.operate.ActivityConfigLangVO;
import com.onelive.common.model.vo.operate.ActivityConfigListVo;
import com.onelive.common.model.vo.operate.ActivityConfigSelectListVo;
import com.onelive.common.model.vo.operate.ActivityConfigVo;
import com.onelive.common.mybatis.entity.OperateActivityConfig;
import com.onelive.common.mybatis.entity.OperateActivityConfigLang;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.JacksonUtil;
import com.onelive.manage.service.operate.OperateActivityConfigLangService;
import com.onelive.manage.service.operate.OperateActivityConfigService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动管理业务类
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ActivityConfigBusiness {

    @Resource
    private OperateActivityConfigService operateActivityConfigService;
    @Resource
    private OperateActivityConfigLangService operateActivityConfigLangService;

    public PageInfo<ActivityConfigListVo> getConfigList() {
        List<OperateActivityConfig> list = operateActivityConfigService.list();
        List<ActivityConfigListVo> voList = BeanCopyUtil.copyCollection(list, ActivityConfigListVo.class);
        return new PageInfo<>(voList);
    }

    /**
     * 获取指定活动的详细配置信息
     *
     * @param id
     * @return
     */
    public ActivityConfigVo getConfig(Long id) {
        OperateActivityConfig config = operateActivityConfigService.getById(id);
        ActivityConfigVo vo = new ActivityConfigVo();
        BeanCopyUtil.copyProperties(config, vo);

        String configJson = config.getConfig();

        if (StrUtil.isNotBlank(configJson)) {
            switch (config.getActivityType()) {
                case 1: //推广返现活动
                    PopularizeActivityConfigDTO pdto = JacksonUtil.fromJson(configJson, PopularizeActivityConfigDTO.class);
                    vo.setConfig(pdto);
                    checkLangList(vo);
                    break;
                case 2: //签到活动
                    break;
                case 3: //首充返现
                    FirstRechargeActivityConfigDTO fdto = JacksonUtil.fromJson(configJson, FirstRechargeActivityConfigDTO.class);
                    vo.setConfig(fdto);
                    checkLangList(vo);
                    break;
                case 4: //红包活动
                    RedPacketActivityConfigDTO rdto = JacksonUtil.fromJson(configJson, RedPacketActivityConfigDTO.class);
                    vo.setConfig(rdto);
                    break;
                case 5: //注册返现
                    RegisterReturnActivityConfigDTO rrdto = JacksonUtil.fromJson(configJson, RegisterReturnActivityConfigDTO.class);
                    vo.setConfig(rrdto);
                    checkLangList(vo);
                    break;
            }
        }
        return vo;
    }

    /**
     * 查询多语言
     * @param vo
     */
    private void checkLangList(ActivityConfigVo vo) {
        QueryWrapper<OperateActivityConfigLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperateActivityConfigLang::getActivityId,vo.getId());
        List<OperateActivityConfigLang> list = operateActivityConfigLangService.list(queryWrapper);
        List<ActivityConfigLangVO> voList = BeanCopyUtil.copyCollection(list, ActivityConfigLangVO.class);
        vo.setLangList(voList);
    }

    /**
     * 保存推广返现活动配置
     *
     * @param req
     * @param loginUser
     */
    @Transactional
    public void saveConfig1(PopularizeActivityConfigSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        OperateActivityConfig config = new OperateActivityConfig();
        BeanCopyUtil.copyProperties(req, config);
        PopularizeActivityConfigDTO dto = new PopularizeActivityConfigDTO();
        BeanCopyUtil.copyProperties(req, dto);

        //活动配置内容转换成json格式
        String configJson = JacksonUtil.toJson(dto);
        config.setConfig(configJson);

        if (config.getId() == null) {
           // config.setCreateUser(loginUser.getAccLogin());
            throw new BusinessException(StatusCode.PARAM_ERROR);
        } else {
            config.setUpdateUser(loginUser.getAccLogin());
        }
        operateActivityConfigService.updateById(config);
        //处理多语言
        dealwithLang(config.getId(), req.getLangList(), loginUser.getAccLogin());
    }

    /**
     * 处理多语言
     */
    private void dealwithLang(Long activityId, List<ActivityConfigLangSaveReq> langList, String operateUser) {
        if (langList == null || langList.size() == 0) {
            throw new BusinessException("语言列表不能为空");
        }

        String activityTitle = "";

        for (ActivityConfigLangSaveReq item : langList) {
            if (item.getLang().equals("zh_CN")) {
                activityTitle = item.getActivityTitle();
                break;
            }
        }

        if (StrUtil.isBlank(activityTitle)) {
            throw new BusinessException("请设置中文内容");
        }

        //判断是新增还是更新
        List<OperateActivityConfigLang> itemAddList = new ArrayList<>();
        List<OperateActivityConfigLang> itemUpdateList = new ArrayList<>();

        for (ActivityConfigLangSaveReq item : langList) {
            OperateActivityConfigLang langItem = new OperateActivityConfigLang();
            BeanUtil.copyProperties(item, langItem);
            langItem.setActivityId(activityId);
            if (item.getId() == null) {
                //需要新增的内容
                langItem.setCreateUser(operateUser);
                itemAddList.add(langItem);
            } else {
                //需要更新的内容
                langItem.setUpdateUser(operateUser);
                itemUpdateList.add(langItem);
            }
        }

        if (itemAddList.size() > 0) {
            MysqlMethod.batchInsert(OperateActivityConfigLang.class, itemAddList);
        }
        if (itemUpdateList.size() > 0) {
            MysqlMethod.batchUpdate(OperateActivityConfigLang.class, itemUpdateList);
        }
    }

    /**
     * 保存红包活动配置内容
     *
     * @param req
     * @param loginUser
     */
    public void saveConfig2(RedPacketActivityConfigSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        OperateActivityConfig config = new OperateActivityConfig();
        BeanCopyUtil.copyProperties(req, config);

        if (config.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        RedPacketActivityConfigDTO dto = new RedPacketActivityConfigDTO();
        BeanCopyUtil.copyProperties(req, dto);

        //活动配置内容转换成json格式
        String configJson = JacksonUtil.toJson(dto);
        config.setConfig(configJson);
        operateActivityConfigService.updateById(config);
    }


    /**
     * 查询用于选择的活动配置列表
     *
     * @return
     */
    public List<ActivityConfigSelectListVo> getConfigSelectList() {
        QueryWrapper<OperateActivityConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperateActivityConfig::getIsActive, 1);
        List<OperateActivityConfig> list = operateActivityConfigService.list(queryWrapper);
        List<ActivityConfigSelectListVo> voList = BeanCopyUtil.copyCollection(list, ActivityConfigSelectListVo.class);
        return voList;
    }

    /**
     * 保存首充返现配置
     * @param req
     * @param loginUser
     */
    public void saveConfig3(FirstRechargeActivityConfigSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        OperateActivityConfig config = new OperateActivityConfig();
        BeanCopyUtil.copyProperties(req, config);
        FirstRechargeActivityConfigDTO dto = new FirstRechargeActivityConfigDTO();
        BeanCopyUtil.copyProperties(req, dto);

        //活动配置内容转换成json格式
        String configJson = JacksonUtil.toJson(dto);
        config.setConfig(configJson);

        if (config.getId() == null) {
           // config.setCreateUser(loginUser.getAccLogin());
            throw new BusinessException(StatusCode.PARAM_ERROR);
        } else {
            config.setUpdateUser(loginUser.getAccLogin());
        }
        operateActivityConfigService.updateById(config);
        //处理多语言
        dealwithLang(config.getId(), req.getLangList(), loginUser.getAccLogin());
    }

    /**
     * 保存注册返现配置
     * @param req
     * @param loginUser
     */
    public void saveConfig5(RegisterReturnActivityConfigSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        OperateActivityConfig config = new OperateActivityConfig();
        BeanCopyUtil.copyProperties(req, config);
        RegisterReturnActivityConfigDTO dto = new RegisterReturnActivityConfigDTO();
        BeanCopyUtil.copyProperties(req, dto);

        //活动配置内容转换成json格式
        String configJson = JacksonUtil.toJson(dto);
        config.setConfig(configJson);

        if (config.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        } else {
            config.setUpdateUser(loginUser.getAccLogin());
        }
        operateActivityConfigService.updateById(config);
        //处理多语言
        dealwithLang(config.getId(), req.getLangList(), loginUser.getAccLogin());
    }
}
