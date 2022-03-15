package com.onelive.manage.modules.game.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.GamePlatformEnums;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.game.platform.GamePlatformSaveReq;
import com.onelive.common.model.req.game.platform.GamePlatformUpdateStatusReq;
import com.onelive.common.model.vo.game.platform.GamePlatformSelectListVO;
import com.onelive.common.model.vo.game.platform.GamePlatformVO;
import com.onelive.common.model.vo.game.platform.GameThirdPlatformListVO;
import com.onelive.common.model.vo.game.platform.GameThirdPlatformSelectListVO;
import com.onelive.common.mybatis.entity.GameThirdPlatform;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.game.GameCategoryService;
import com.onelive.manage.service.game.GameThirdPlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 第三方游戏平台管理业务类
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameThirdPlatformBusiness {

    @Resource
    private GameThirdPlatformService gameThirdPlatformService;
    @Resource
    private GameCategoryService gameCategoryService;


    /**
     * 查询第三方游戏平台列表
     *
     * @param req
     * @return
     */
    public PageInfo<GameThirdPlatformListVO> getPlatformList(PageReq req) {
        QueryWrapper<GameThirdPlatform> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThirdPlatform::getIsDelete, false).eq(GameThirdPlatform::getMerchantCode, LoginInfoUtil.getMerchantCode());
        PageInfo<GameThirdPlatform> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameThirdPlatformService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo, GameThirdPlatformListVO.class);
    }


    /**
     * 保存第三方游戏平台的信息
     *
     * @param req
     * @param loginUser
     */
    @Transactional
    public void savePlatform(GamePlatformSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有操作权限");
        }
        if (req == null || StrUtil.isBlank(req.getName()) || StrUtil.isBlank(req.getPlatformCode())) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        GameThirdPlatform platform = new GameThirdPlatform();
        BeanCopyUtil.copyProperties(req, platform);

        Long id = req.getId();
        String admin = loginUser.getAccLogin();
        if (id == null) {

            //查询平台是否已存在
            GameThirdPlatform dbPlatform = gameThirdPlatformService.getByCode(req.getPlatformCode());
            if(dbPlatform!=null){
                throw new BusinessException("该平台已存在, 请勿重复添加!");
            }
            platform.setCreateUser(admin);
            gameThirdPlatformService.save(platform);
        } else {
            GameThirdPlatform dbData = gameThirdPlatformService.getById(id);
            if (dbData == null) {
                throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
            }
            if (dbData.getIsDelete()) {
                throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
            }

            GameThirdPlatform dbPlatform = gameThirdPlatformService.getByCode(req.getPlatformCode());
            if(dbPlatform!=null && !dbPlatform.getId().equals(id)){
                throw new BusinessException("该平台已存在, 请勿重复添加!");
            }

            //如果状态发生变化, 更新下级状态
            if(!dbPlatform.getIsShow().equals(platform.getIsShow())){
                gameCategoryService.updateStatusByPlatform(platform);
            }

            platform.setUpdateUser(admin);
            gameThirdPlatformService.updateById(platform);
        }
    }

    /**
     * 查询平台信息
     *
     * @param req
     * @return
     */
    public GamePlatformVO getPlatform(LongIdReq req) {
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        GameThirdPlatform dbData = gameThirdPlatformService.getById(req.getId());
        if (dbData == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if (dbData.getIsDelete()) {
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        GamePlatformVO vo = new GamePlatformVO();
        BeanCopyUtil.copyProperties(dbData, vo);
        return vo;
    }

    /**
     * 删除第三方游戏平台
     *
     * @param req
     * @param loginUser
     */
    public void delPlatform(LongIdReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有操作权限");
        }
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        GameThirdPlatform dbData = gameThirdPlatformService.getById(req.getId());
        if (dbData == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if (dbData.getIsDelete()) {
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        dbData.setIsDelete(true);
        dbData.setUpdateUser(loginUser.getAccLogin());
        gameThirdPlatformService.updateById(dbData);
    }

    /**
     * 查询列表, 用于选择
     *
     * @return
     */
    public List<GamePlatformSelectListVO> getSelectList() {
        QueryWrapper<GameThirdPlatform> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThirdPlatform::getIsDelete, false).eq(GameThirdPlatform::getMerchantCode, LoginInfoUtil.getMerchantCode());
        List<GameThirdPlatform> list = gameThirdPlatformService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            return BeanCopyUtil.copyCollection(list, GamePlatformSelectListVO.class);
        }
        return new LinkedList<>();
    }

    /**
     * 更新状态
     *
     * @param req
     * @param loginUser
     * @return
     */
    @Transactional
    public Boolean updatePlatformStatus(GamePlatformUpdateStatusReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401,"没有操作权限");
        }
        if (req == null || req.getId() == null || req.getIsShow() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        Long id = req.getId();
        GameThirdPlatform platform = gameThirdPlatformService.getById(id);
        if (platform == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(platform.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        if(platform.getIsShow().equals(req.getIsShow())){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        platform.setIsShow(req.getIsShow());
        platform.setUpdateUser(loginUser.getAccLogin());
        gameCategoryService.updateStatusByPlatform(platform);
        return gameThirdPlatformService.updateById(platform);
    }

    /**
     * 获取已接入的平台
     * @return
     */
    public List<GameThirdPlatformSelectListVO> getPlatformSelectList() {
        List<GameThirdPlatformSelectListVO> voList = new LinkedList<>();
        GamePlatformEnums[] values = GamePlatformEnums.values();
        for (GamePlatformEnums en : values) {
            GameThirdPlatformSelectListVO vo = new GameThirdPlatformSelectListVO();
            vo.setName(en.getName());
            vo.setPlatformCode(en.getPlatformCode());
            voList.add(vo);
        }
        return voList;
    }
}
