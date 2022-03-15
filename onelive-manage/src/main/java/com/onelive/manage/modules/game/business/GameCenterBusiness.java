package com.onelive.manage.modules.game.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdListReq;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.game.*;
import com.onelive.common.model.req.game.gamecenter.GameThirdSelectListReq;
import com.onelive.common.model.req.game.tag.GameTagUpdateStatusReq;
import com.onelive.common.model.req.game.third.GameTagSelectListReq;
import com.onelive.common.model.vo.game.*;
import com.onelive.common.model.vo.game.gamecenter.GameThirdSelectListVO;
import com.onelive.common.model.vo.game.third.GameTagSelectListVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.game.*;
import com.onelive.manage.service.sys.SysCountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 第三方游戏-游戏中心管理业务类
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameCenterBusiness {

    @Resource
    private GameTagService gameTagService;
    @Resource
    private GameTagLangService gameTagLangService;
    @Resource
    private GameCategoryService gameCategoryService;
    @Resource
    private GameThirdService gameThirdService;
    @Resource
    private GameCenterGameService gameCenterGameService;
    @Resource
    private GameCenterGameLangService gameCenterGameLangService;
    @Resource
    private SysCountryService sysCountryService;


    /**
     * 查询标签列表
     *
     * @param req
     * @return
     */
    public PageInfo<GameTagListVO> getTagList(GameTagListReq req) {
        QueryWrapper<GameTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameTag::getIsDelete, false).eq(GameTag::getCountryCode, req.getCountryCode());
        PageInfo<GameTag> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameTagService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo, GameTagListVO.class);
    }

    /**
     * 保存标签
     *
     * @param req
     * @param loginUser
     */
    @Transactional
    public void saveTag(GameTagSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req == null || StrUtil.isBlank(req.getCountryCode()) || req.getLangList() == null || req.getLangList().size() == 0) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        String admin = loginUser.getAccLogin();

        String name = "";
        String enName = "";
        List<GameTagLangSaveReq> langList = req.getLangList();
        for (GameTagLangSaveReq item : langList) {
            if (LangConstants.LANG_CN.equals(item.getLang())) {
                name = item.getName();
            }
            if (LangConstants.LANG_US.equals(item.getLang())) {
                enName = item.getName();
            }
        }

        if (StrUtil.isBlank(name)) {
            throw new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }
        if (StrUtil.isBlank(enName)) {
            throw new BusinessException(StatusCode.LANG_EN_NULL_ERROR);
        }

        String countryCode = req.getCountryCode();
        String code = countryCode + SymbolConstant.UNDERLINE + enName;

        Long id = req.getId();
        GameTag tag = new GameTag();
        BeanCopyUtil.copyProperties(req, tag);
        tag.setName(name);
        tag.setCode(code);
        if (id == null) {
            GameTag dbTag = gameTagService.getByCode(code);
            if (dbTag != null) {
                throw new BusinessException("该英文标签已存在,请勿重复添加!");
            }
            tag.setCreateUser(admin);
            gameTagService.save(tag);
            id = tag.getId();
        } else {

            GameTag dbTag = gameTagService.getByCode(code);
            if (dbTag != null && !dbTag.getId().equals(id)) {
                throw new BusinessException("该英文标签已存在,请勿重复添加!");
            }

            tag.setUpdateUser(admin);
            gameTagService.updateById(tag);
        }

        List<GameTagLang> addList = new LinkedList<>();
        List<GameTagLang> updateList = new LinkedList<>();
        for (GameTagLangSaveReq item : langList) {
            GameTagLang lang = new GameTagLang();
            BeanCopyUtil.copyProperties(item, lang);
            lang.setTagId(id);
            if (item.getId() == null) {
                addList.add(lang);
            } else {
                updateList.add(lang);
            }
        }

        if (addList.size() > 0) {
            MysqlMethod.batchInsert(GameTagLang.class, addList);
        }
        if (updateList.size() > 0) {
            MysqlMethod.batchUpdate(GameTagLang.class, updateList);
        }
    }

    /**
     * 查询标签信息
     *
     * @param req
     * @return
     */
    public GameTagVO getTag(LongIdReq req) {
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        GameTag tag = gameTagService.getById(req.getId());
        if (tag == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        GameTagVO vo = new GameTagVO();
        BeanCopyUtil.copyProperties(tag, vo);
        //查询多语言
        QueryWrapper<GameTagLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameTagLang::getTagId, req.getId());
        List<GameTagLang> list = gameTagLangService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            List<GameTagLangVO> langList = BeanCopyUtil.copyCollection(list, GameTagLangVO.class);
            vo.setLangList(langList);
        }
        return vo;
    }

    /**
     * 删除标签信息
     *
     * @param req
     * @param loginUser
     */
    public void delTag(LongIdReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        GameTag tag = gameTagService.getById(req.getId());
        if (tag == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if (tag.getIsDelete()) {
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        tag.setIsDelete(true);
        tag.setUpdateUser(loginUser.getAccLogin());
        gameTagService.updateById(tag);
    }

    /**
     * 获取第三方游戏列表-用于选择
     *
     * @return
     */
    public List<GameSelectListVO> getSelectList() {
        List<GameSelectListVO> list = new LinkedList<>();
        //1.查询所有分类
        QueryWrapper<GameCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameCategory::getIsDelete, false).eq(GameCategory::getIsWork, true);
        List<GameCategory> categoryList = gameCategoryService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(categoryList)) {
            for (GameCategory category : categoryList) {
                GameSelectListVO vo = new GameSelectListVO();
                BeanCopyUtil.copyProperties(category, vo);
                list.add(vo);
            }
            //查询所有第三方游戏
            QueryWrapper<GameThird> thirdQueryWrapper = new QueryWrapper<>();
            thirdQueryWrapper.lambda().eq(GameThird::getIsDelete, false).eq(GameThird::getIsWork, true).eq(GameThird::getStatus,2);
            List<GameThird> gameList = gameThirdService.list(thirdQueryWrapper);
            if (CollectionUtil.isNotEmpty(gameList)) {
                Map<Integer, List<GameThird>> gameMap = new HashMap<>();
                for (GameThird game : gameList) {
                    Integer categoryId = game.getCategoryId();
                    List<GameThird> thirdList = gameMap.get(categoryId);
                    if (thirdList == null) {
                        thirdList = new LinkedList<>();
                    }
                    thirdList.add(game);
                    gameMap.put(categoryId, thirdList);
                }

                if (CollectionUtil.isNotEmpty(gameMap)) {
                    for (GameSelectListVO vo : list) {
                        List<GameThird> gameThirds = gameMap.get(vo.getCategoryId());
                        List<GameSelectGameVO> gameVOList = BeanCopyUtil.copyCollection(gameThirds, GameSelectGameVO.class);
                        vo.setGameList(gameVOList);
                    }
                }
            }
        }
        return list;
    }


    /**
     * 查询分类游戏列表
     *
     * @param req
     * @return
     */
    public PageInfo<GameCenterGameListVO> getGameList(GameCenterGameListReq req) {
        if (req == null || StringUtils.isBlank(req.getCode())) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
//        Integer categoryId = req.getCategoryId();
        QueryWrapper<GameCenterGame> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameCenterGame::getIsDelete, false).eq(GameCenterGame::getCode, req.getCode()).eq(GameCenterGame::getStatus,2);//.eq(categoryId!=null,GameCenterGame::getCategoryId,categoryId);
        PageInfo<GameCenterGame> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameCenterGameService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo, GameCenterGameListVO.class);
    }

    /**
     * 分类游戏信息保存
     *
     * @param req
     * @param loginUser
     */
    public void saveGame(GameCenterGameSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req == null || req.getLangList() == null || req.getLangList().size() == 0) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String admin = loginUser.getAccLogin();
        String name = "";
        List<GameCenterGameLangSaveReq> langList = req.getLangList();
        for (GameCenterGameLangSaveReq item : langList) {
            if (LangConstants.LANG_CN.equals(item.getLang())) {
                name = item.getName();
                break;
            }
        }

        if (StrUtil.isBlank(name)) {
            throw new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }

        Long id = req.getId();
        GameCenterGame game = new GameCenterGame();
        BeanCopyUtil.copyProperties(req, game);
        game.setName(name);

        //查询标签
        GameTag tag = gameTagService.getByCode(req.getCode());
        if (tag == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        game.setCode(tag.getCode());
        if (id == null) {
            game.setCreateUser(admin);
            gameCenterGameService.save(game);
            id = game.getId();
        } else {
            game.setUpdateUser(admin);
            gameCenterGameService.updateById(game);
        }

        List<GameCenterGameLang> addList = new LinkedList<>();
        List<GameCenterGameLang> updateList = new LinkedList<>();
        for (GameCenterGameLangSaveReq item : langList) {
            GameCenterGameLang lang = new GameCenterGameLang();
            BeanCopyUtil.copyProperties(item, lang);
            lang.setGameId(id);
            if (item.getId() == null) {
                addList.add(lang);
            } else {
                updateList.add(lang);
            }
        }

        if (addList.size() > 0) {
            MysqlMethod.batchInsert(GameCenterGameLang.class, addList);
        }
        if (updateList.size() > 0) {
            MysqlMethod.batchUpdate(GameCenterGameLang.class, updateList);
        }

    }

    /**
     * 分类游戏信息查询
     *
     * @param req
     * @return
     */
    public GameCenterGameVO getGame(LongIdReq req) {
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        GameCenterGame game = gameCenterGameService.getById(req.getId());
        if (game == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if (game.getIsDelete()) {
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        GameCenterGameVO vo = new GameCenterGameVO();
        BeanCopyUtil.copyProperties(game, vo);
        //查询多语言
        QueryWrapper<GameCenterGameLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameCenterGameLang::getGameId, game.getId());
        List<GameCenterGameLang> langList = gameCenterGameLangService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(langList)) {
            List<GameCenterGameLangVO> langVOList = BeanCopyUtil.copyCollection(langList, GameCenterGameLangVO.class);
            vo.setLangList(langVOList);
        }
        return vo;
    }

    /**
     * 删除分类游戏信息
     *
     * @param req
     * @param loginUser
     */
    public void delGame(LongIdListReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req == null || req.getIds() == null || req.getIds().size() == 0) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        List<Long> ids = req.getIds();
        UpdateWrapper<GameCenterGame> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(GameCenterGame::getIsDelete, true).in(GameCenterGame::getId, ids);
        gameCenterGameService.update(updateWrapper);
    }


    /**
     * 更新标签状态
     * @param req
     * @param loginUser
     * @return
     */
    public Boolean updateTagStatus(GameTagUpdateStatusReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req == null || req.getId() == null ) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        GameTag tag = gameTagService.getById(req.getId());
        if(tag == null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(tag.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }

        tag.setIsShow(req.getIsShow());
        tag.setUpdateUser(loginUser.getAccLogin());
        tag.setUpdateTime(new Date());
        return gameTagService.updateById(tag);
    }


    /**
     * 查询第三方所有分类, 用于下拉选择
     * @return
     */
    public List<GameCategorySelectListVO> getCategorySelectList() {
        //1.查询所有分类
        QueryWrapper<GameCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameCategory::getIsDelete, false).eq(GameCategory::getIsWork, true);
        List<GameCategory> categoryList = gameCategoryService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(categoryList)) {
            return BeanCopyUtil.copyCollection(categoryList,GameCategorySelectListVO.class);
        }
        return new LinkedList<>();
    }


    /**
     * 查询标签列表-用于下拉选择
     * @return
     * @param req
     */
    public List<GameTagSelectListVO> getTagSelectList(GameTagSelectListReq req) {
        QueryWrapper<GameTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameTag::getIsDelete, false).eq(GameTag::getIsShow,true).eq(GameTag::getCountryCode, req.getCountryCode());
        List<GameTag> list = gameTagService.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            return BeanCopyUtil.copyCollection(list,GameTagSelectListVO.class);
        }
        return new LinkedList<>();
    }

    /**
     * 第三方游戏列表-用于选择
     * @param req
     * @return
     */
    public List<GameThirdSelectListVO> getGameSelectList(GameThirdSelectListReq req) {
        if(req==null || req.getCategoryId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        QueryWrapper<GameThird> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThird::getIsDelete,false).eq(GameThird::getCategoryId,req.getCategoryId())
                .eq(GameThird::getStatus,2).eq(GameThird::getIsWork,true);
        List<GameThird> list = gameThirdService.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
           return BeanCopyUtil.copyCollection(list,GameThirdSelectListVO.class);
        }
        return new LinkedList<>();
    }
}
