package com.onelive.manage.modules.game.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.enums.*;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.game.GameCategorySaveReq;
import com.onelive.common.model.req.game.GameThirdListReq;
import com.onelive.common.model.req.game.GameThirdSaveReq;
import com.onelive.common.model.req.game.third.GameInfoSelectReq;
import com.onelive.common.model.vo.game.*;
import com.onelive.common.model.vo.game.category.GamePlatformSelectVO;
import com.onelive.common.model.vo.game.third.GameInfoSelectVO;
import com.onelive.common.model.vo.game.third.GamePlatformAndCategoryVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.game.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 第三方游戏分类业务类
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameCategoryBusiness {

    @Resource
    private GameCategoryService gameCategoryService;
    @Resource
    private GameThirdService gameThirdService;
    @Resource
    private GameThirdPlatformService gameThirdPlatformService;
    @Resource
    private GameObgService gameObgService;
    @Resource
    private GameCenterGameService gameCenterGameService;

    @Transactional
    public void saveCategory(GameCategorySaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String admin = loginUser.getAccLogin();
        GameCategory category = new GameCategory();
        BeanCopyUtil.copyProperties(req, category);
        Long id = req.getId();
        if (id == null) {
            //查询categoryId是否存在
            GameCategory dbCategory = gameCategoryService.getByCategoryId(req.getCategoryId());
            if(dbCategory != null){
                throw new BusinessException("该分类ID已存在!");
            }

            //查询第三方分类
            QueryWrapper<GameCategory> queryWrapper=new QueryWrapper<>();
            queryWrapper.lambda().eq(GameCategory::getIsDelete,false).eq(GameCategory::getGameType,req.getGameType()).eq(GameCategory::getMerchantCode, LoginInfoUtil.getMerchantCode());
            int count = gameCategoryService.count(queryWrapper);
            if(count>0){
                throw new BusinessException("该分类已存在, 请勿重复添加!");
            }

            //查询钱包类型
            QueryWrapper<GameCategory> queryWrapper2=new QueryWrapper<>();
            queryWrapper2.lambda().eq(GameCategory::getIsDelete,false).eq(GameCategory::getGameWallet,req.getGameWallet()).eq(GameCategory::getMerchantCode, LoginInfoUtil.getMerchantCode());
            int count2 = gameCategoryService.count(queryWrapper2);
            if(count2>0){
                throw new BusinessException("同一个钱包类型的分类只能添加一个, 请勿重复添加!");
            }

            //新增
            category.setCreateUser(admin);
            gameCategoryService.save(category);
        } else {
            //查询
            GameCategory dbCategory = gameCategoryService.getById(req.getId());
            if(dbCategory==null){
                throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
            }
            if(dbCategory.getIsDelete()){
                throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
            }

            //查询钱包类型
            QueryWrapper<GameCategory> queryWrapper2=new QueryWrapper<>();
            queryWrapper2.lambda().eq(GameCategory::getIsDelete,false).eq(GameCategory::getGameWallet,req.getGameWallet()).eq(GameCategory::getMerchantCode, LoginInfoUtil.getMerchantCode());
            GameCategory walletCategory = gameCategoryService.getBaseMapper().selectOne(queryWrapper2);
            if(walletCategory!=null && !walletCategory.getId().equals(req.getId())){
                throw new BusinessException("同一个钱包类型的分类只能添加一个, 请勿重复添加!");
            }


            //如果状态发生变化, 更新下级状态
            if(!dbCategory.getIsWork().equals(category.getIsWork())){
                int status = category.getIsWork()?2:1;
                dbCategory.setStatus(status);
                gameThirdService.updateStatusByCategory(dbCategory);
            }

            //更新
            category.setUpdateUser(admin);
            gameCategoryService.updateById(category);
        }
    }

    /**
     * 查询游戏分类数据
     *
     * @param req
     * @return
     */
    public GameCategoryVO getCategory(LongIdReq req) {
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        GameCategory category = gameCategoryService.getById(req.getId());
        if (category == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }

        if (category.getIsDelete()) {
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }

        GameCategoryVO vo = new GameCategoryVO();
        BeanCopyUtil.copyProperties(category, vo);
        return vo;
    }

    /**
     * 删除游戏分类
     *
     * @param req
     * @param loginUser
     */
    public void delCategory(LongIdReq req, LoginUser loginUser) {
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        GameCategory category = gameCategoryService.getById(req.getId());
        if (category == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        category.setIsDelete(true);
        category.setUpdateUser(loginUser.getAccLogin());
        gameCategoryService.updateById(category);
    }

    /**
     * 游戏分类列表查询
     *
     * @param req
     * @return
     */
    public PageInfo<GameCategoryListVO> getCategoryList(PageReq req) {
        QueryWrapper<GameCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameCategory::getIsDelete, false);
        PageInfo<GameCategory> info = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameCategoryService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(info, GameCategoryListVO.class);
    }

    /**
     * 游戏分类所需下拉选项列表查询
     *
     * @return
     */
    public List<GameWalletSelectVO> getGameWalletSelectList() {
        List<GameWalletSelectVO> walletList = new LinkedList<>();
        WalletTypeEnum[] values = WalletTypeEnum.values();
        for (WalletTypeEnum en : values) {
            if(en.getCode()==1){
                //过滤平台钱包
                continue;
            }
            GameWalletSelectVO walletVo = new GameWalletSelectVO();
            walletVo.setName(en.getDesc());
            walletVo.setType(en.getCode());
            walletList.add(walletVo);
        }
        /*OBGGameWalletEnums[] walletEnums = OBGGameWalletEnums.values();
        for (OBGGameWalletEnums wallet : walletEnums) {
            GameWalletSelectVO walletVo = new GameWalletSelectVO();
            walletVo.setName(wallet.getDesc());
            walletVo.setType(wallet.getType());
            walletList.add(walletVo);
        }*/
        return walletList;
    }

    /**
     * 第三方游戏分类下拉选项列表
     *
     * @return
     */
    public List<GameThirdCategoryTypeSelectVO> getGameThirdCategoryTypeSelectList() {
        List<GameThirdCategoryTypeSelectVO> categoryList = new LinkedList<>();
        GameThirdTypeEnums[] typeEnums = GameThirdTypeEnums.values();
        for (GameThirdTypeEnums type : typeEnums) {
            GameThirdCategoryTypeSelectVO categoryVO = new GameThirdCategoryTypeSelectVO();
            categoryVO.setName(type.getDesc());
            categoryVO.setType(type.getType());
            categoryList.add(categoryVO);
        }
        return categoryList;
    }


    /**
     * 查询第三方游戏分类列表-用于选择
     *
     * @return
     */
    public List<GameCategorySelectListVO> getCategorySelectList() {
        QueryWrapper<GameCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameCategory::getIsDelete, false);
        List<GameCategory> list = gameCategoryService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            return BeanCopyUtil.copyCollection(list, GameCategorySelectListVO.class);
        } else {
            return new LinkedList<>();
        }
    }

    /**
     * 第三方游戏列表
     *
     * @param req
     * @return
     */
    public PageInfo<GameThirdListVO> getGameList(GameThirdListReq req) {
        QueryWrapper<GameThird> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThird::getIsDelete, false);
        Integer categoryId = req.getCategoryId();
        String name = req.getName();
        if (categoryId != null) {
            queryWrapper.lambda().eq(GameThird::getCategoryId, categoryId);
        }
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.lambda().like(GameThird::getName, name);
        }
        PageInfo<GameThird> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameThirdService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo, GameThirdListVO.class);
    }

    /**
     * 保存游戏信息
     *
     * @param req
     * @param loginUser
     */
    @Transactional
    public void saveGame(GameThirdSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        List<String> countryCodeList = req.getCountryCodeList();
        if (CollectionUtil.isEmpty(countryCodeList)) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String admin = loginUser.getAccLogin();
        StringBuilder sb = new StringBuilder();
        countryCodeList.stream().forEach(code -> sb.append(code).append(","));
        sb.setLength(sb.length() - 1);

        GameThird game = new GameThird();
        BeanCopyUtil.copyProperties(req, game);
        game.setCountryCodeList(sb.toString());
        //根据商户号, 生成一个code唯一标识
        Integer categoryId = req.getCategoryId();
        String gameCode = "";
        GameCategory category = gameCategoryService.getByCategoryId(categoryId);
        if (category != null) {
            String merchantCode = category.getMerchantCode();
            if (StringUtils.isNotBlank(merchantCode)) {
                gameCode =category.getPlatformCode() +  SymbolConstant.UNDERLINE + merchantCode +  SymbolConstant.UNDERLINE + category.getGameType() + SymbolConstant.UNDERLINE + game.getGameId();
            }
            //关联平台代码
            game.setPlatformCode(category.getPlatformCode());
        } else {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        game.setGameType(category.getGameType());
        game.setGameCode(gameCode);
        if (req.getId() == null) {
            //查询是否有这个游戏了
            GameThird dbGame = gameThirdService.getByGameCode(gameCode);
            if(dbGame!=null){
                throw new BusinessException("该游戏已经存在, 请勿重复添加!");
            }

            game.setCreateUser(admin);
            gameThirdService.save(game);
        } else {
            GameThird dbGame = gameThirdService.getById(req.getId());
            if(dbGame==null){
                throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
            }
            if(dbGame.getIsDelete()){
                throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
            }
            if(!dbGame.getIsWork().equals(game.getIsWork())){
                int status = game.getIsWork()?2:1;
                dbGame.setStatus(status);
                //更新游戏中心游戏状态
                gameCenterGameService.updateStatusByGameThird(dbGame);
            }
            game.setUpdateUser(admin);
            gameThirdService.updateById(game);
        }
    }


    /**
     * 查询第三方游戏信息
     *
     * @param req
     * @return
     */
    public GameThirdVO getGame(LongIdReq req) {
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        GameThird game = gameThirdService.getById(req.getId());
        if (game == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        GameThirdVO vo = new GameThirdVO();
        BeanCopyUtil.copyProperties(game, vo);
        String countryIdList = game.getCountryCodeList();
        if (StrUtil.isNotBlank(countryIdList)) {
            String[] codeArr = countryIdList.split(",");
            List<String> codeList = Arrays.asList(codeArr);
            vo.setCountryCodeList(codeList);
        }
        return vo;
    }

    /**
     * 删除第三方游戏信息
     *
     * @param req
     * @param loginUser
     */
    public void delGame(LongIdReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        GameThird game = gameThirdService.getById(req.getId());
        if (game == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if (game.getIsDelete()) {
            throw new BusinessException("数据已删除!");
        }
        game.setIsDelete(true);
        game.setUpdateUser(loginUser.getAccLogin());
        gameThirdService.updateById(game);
    }

    /**
     * 获取下拉列表
     * @return
     */
    public List<GamePlatformSelectVO> getGamePlatformSelectList() {
        //查询所有第三方平台
        QueryWrapper<GameThirdPlatform> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThirdPlatform::getIsDelete,false).eq(GameThirdPlatform::getIsShow,true).eq(GameThirdPlatform::getMerchantCode,LoginInfoUtil.getMerchantCode());
        List<GameThirdPlatform> list = gameThirdPlatformService.list(queryWrapper);
        List<GamePlatformSelectVO> voList = new LinkedList<>();
        if(CollectionUtil.isNotEmpty(list)){
            list.stream().forEach(platform->{
                GameThirdTypeEnums[] categoryArr = GameThirdTypeEnums.values();
                WalletTypeEnum[] walletArr = WalletTypeEnum.values();
                List<GameThirdCategoryTypeSelectVO> categoryList = new LinkedList<>();
                List<GameWalletSelectVO> walletList=new LinkedList<>();
                for(GameThirdTypeEnums category:categoryArr){
                    GameThirdCategoryTypeSelectVO cvo = new GameThirdCategoryTypeSelectVO();
                    cvo.setName(category.getDesc());
                    cvo.setType(category.getType());
                    categoryList.add(cvo);
                }
                for(WalletTypeEnum wallet : walletArr){
                    if(wallet.getCode()==1){
                        //过滤平台钱包
                        continue;
                    }
                    GameWalletSelectVO wvo = new GameWalletSelectVO();
                    wvo.setName(wallet.getDesc());
                    wvo.setType(wallet.getCode());
                    walletList.add(wvo);
                }
                GamePlatformSelectVO vo = new GamePlatformSelectVO();
                vo.setPlatformName(platform.getName());
                vo.setPlatformCode(platform.getPlatformCode());
                vo.setCategoryList(categoryList);
                vo.setWalletList(walletList);
                voList.add(vo);
            });
        }
        return voList;
    }

    /**
     * 查询第三方平台和分类列表, 用于选择
     * @return
     */
    public List<GamePlatformAndCategoryVO> getGamePlatformAndCategorySelectList() {
        List<GamePlatformAndCategoryVO> voList = new LinkedList<>();

        QueryWrapper<GameThirdPlatform> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThirdPlatform::getIsDelete,false).eq(GameThirdPlatform::getIsShow,true).eq(GameThirdPlatform::getMerchantCode,LoginInfoUtil.getMerchantCode());
        List<GameThirdPlatform> list = gameThirdPlatformService.list(queryWrapper);

        if(CollectionUtil.isNotEmpty(list)){
            //查询所有分类
            QueryWrapper<GameCategory> categoryWrapper=new QueryWrapper<>();
            categoryWrapper.lambda().eq(GameCategory::getIsDelete,false).eq(GameCategory::getIsWork,true);
            List<GameCategory> categoryList = gameCategoryService.list(categoryWrapper);
            Map<String, List<GameCategory>> categoryMap = new HashMap<>();
            if(CollectionUtil.isNotEmpty(categoryList)){
                Map<String, List<GameCategory>> map = categoryList.stream().collect(Collectors.groupingBy(GameCategory::getPlatformCode));
                categoryMap.putAll(map);
            }
            list.stream().forEach(platform->{
                GamePlatformAndCategoryVO vo = new GamePlatformAndCategoryVO();
                vo.setName(platform.getName());
                vo.setPlatformCode(platform.getPlatformCode());
                vo.setCategoryList(BeanCopyUtil.copyCollection(categoryMap.get(platform.getPlatformCode()),GameCategorySelectListVO.class));
                voList.add(vo);
            });
        }

        return voList;
    }

    /**
     * 根据分类ID查询第三方游戏列表-用于选择
     * @param req
     * @return
     */
    public List<GameInfoSelectVO> getGameSelectList(GameInfoSelectReq req) {
        if (req == null || req.getCategoryId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //查询分类
        GameCategory category = gameCategoryService.getByCategoryId(req.getCategoryId());
        if(category==null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        List<GameInfoSelectVO> voList = new LinkedList<>();
        String platformCode = category.getPlatformCode();
        //根据平台判断查询哪个数据
        if(GamePlatformEnums.OBG.getPlatformCode().equals(platformCode)){
            QueryWrapper<GameObg> queryWrapper=new QueryWrapper<>();
            queryWrapper.lambda().eq(GameObg::getStatus, 2).eq(GameObg::getPlatformCode,platformCode).eq(GameObg::getCategoryId,category.getCategoryId());
            List<GameObg> list = gameObgService.list(queryWrapper);
            if(CollectionUtil.isNotEmpty(list)){
                voList = BeanCopyUtil.copyCollection(list,GameInfoSelectVO.class);
            }
        }

        return voList;
    }
}
