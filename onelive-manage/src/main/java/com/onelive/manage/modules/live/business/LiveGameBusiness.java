package com.onelive.manage.modules.live.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.live.*;
import com.onelive.common.model.vo.live.game.*;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.*;
import com.onelive.manage.service.game.GameCategoryService;
import com.onelive.manage.service.game.GameThirdService;
import com.onelive.manage.service.live.LiveGameLangService;
import com.onelive.manage.service.live.LiveGameService;
import com.onelive.manage.service.live.LiveGameTagLangService;
import com.onelive.manage.service.live.LiveGameTagService;
import com.onelive.manage.service.lottery.LotteryService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 直播游戏管理业务类
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LiveGameBusiness {
    @Resource
    private LiveGameTagService gameTagService;
    @Resource
    private LiveGameTagLangService gameTagLangService;
    @Resource
    private LiveGameService liveGameService;
    @Resource
    private LiveGameLangService liveGameLangService;
    @Resource
    private LotteryService lotteryService;
    @Resource
    private GameCategoryService gameCategoryService;
    @Resource
    private GameThirdService gameThirdService;

    /**
     * 查询直播游戏标签列表
     * @param req
     * @return
     */
    public PageInfo<LiveGameTagListVO> getTagList(LiveGameTagListReq req) {
        QueryWrapper<LiveGameTag> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveGameTag::getIsDelete,false);
        PageInfo<GameTag> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameTagService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo, LiveGameTagListVO.class);
    }


    /**
     * 保存直播游戏标签
     * @param req
     * @param loginUser
     */
    @Transactional
    public void saveTag(LiveGameTagSaveReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401,"没有操作权限!");
        }

        if(req==null || req.getCountryCodeList()==null || req.getCountryCodeList().size()==0 || req.getLangList()==null || req.getLangList().size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        String countryCodeList = StringUtils.join(req.getCountryCodeList(), ",");
        List<LiveGameTagLangSaveReq> langList = req.getLangList();
        String zhName = "";
        String enName = "";
        for(LiveGameTagLangSaveReq item : langList){
            if(LangConstants.LANG_CN.equals(item.getLang())){
                zhName = item.getName();
            }
            if(LangConstants.LANG_US.equals(item.getLang())){
                enName = item.getName();
            }
        }

        if(StringUtils.isBlank(zhName)){
            throw new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }
        if(StringUtils.isBlank(enName)){
            throw new BusinessException(StatusCode.LANG_EN_NULL_ERROR);
        }

        Long id = req.getId();
        String admin = loginUser.getAccLogin();
        LiveGameTag tag = new LiveGameTag();
        BeanCopyUtil.copyProperties(req,tag);
        tag.setName(zhName);
        tag.setCountryCodeList(countryCodeList);
        if(id==null){
            //查询一下这个code是否存在
            LiveGameTag dbTag = gameTagService.getByCode(enName);
            if(dbTag != null){
                throw new BusinessException("该标签已存在");
            }
            tag.setCreateUser(admin);
            tag.setCode(enName);
            gameTagService.save(tag);
            id = tag.getId();
        }else{
            tag.setUpdateUser(admin);
            gameTagService.updateById(tag);
        }

        List<LiveGameTagLang> addList = new LinkedList<>();
        List<LiveGameTagLang> updateList = new LinkedList<>();
        for(LiveGameTagLangSaveReq item : langList){
            LiveGameTagLang lang = new LiveGameTagLang();
            BeanCopyUtil.copyProperties(item,lang);
            lang.setTagId(id);
            if(item.getId()==null){
                addList.add(lang);
            }else{
                updateList.add(lang);
            }
        }
        if(addList.size()>0){
            MysqlMethod.batchInsert(LiveGameTagLang.class,addList);
        }
        if(updateList.size()>0){
            MysqlMethod.batchUpdate(LiveGameTagLang.class,updateList);
        }
    }

    /**
     * 获取游戏标签信息
     * @param req
     * @return
     */
    public LiveGameTagVO getTag(LongIdReq req) {
        if(req==null || req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LiveGameTag tag = gameTagService.getById(req.getId());
        if(tag == null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(tag.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }

        LiveGameTagVO vo = new LiveGameTagVO();
        BeanCopyUtil.copyProperties(tag,vo);
        String countryCodeList = tag.getCountryCodeList();
        if(StrUtil.isNotBlank(countryCodeList)){
            String[] ids = countryCodeList.split(",");
            List<String> codeList = Arrays.asList(ids);
            vo.setCountryCodeList(codeList);
        }
        //查询多语言
        QueryWrapper<LiveGameTagLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveGameTagLang::getTagId,req.getId());
        List<LiveGameTagLang> list = gameTagLangService.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            List<LiveGameTagLangVO> langList = BeanCopyUtil.copyCollection(list, LiveGameTagLangVO.class);
            vo.setLangList(langList);
        }
        return vo;
    }

    /**
     * 删除游戏标签
     * @param req
     * @param loginUser
     */
    public void delTag(LongIdReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401, "没有权限操作");
        }
        if(req==null || req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LiveGameTag tag = gameTagService.getById(req.getId());
        if(tag == null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        if(tag.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        tag.setIsDelete(true);
        tag.setUpdateUser(loginUser.getAccLogin());
        gameTagService.updateById(tag);
    }

    /**
     * 查询游戏列表
     * @param req
     * @return
     */
    public PageInfo<LiveGameListVO> getGameList(LiveGameListReq req) {
        if(req==null || StrUtil.isBlank(req.getCode())){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        QueryWrapper<LiveGame> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveGame::getIsDelete,false).eq(LiveGame::getCode,req.getCode());
        PageInfo<LiveGame> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> liveGameService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo, LiveGameListVO.class);
    }

    /**
     * 保存游戏信息
     * @param req
     * @param loginUser
     */
    @Transactional
    public void saveGame(LiveGameSaveReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401, "没有权限操作");
        }
        if(req==null || req.getLangList()==null || req.getLangList().size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String admin = loginUser.getAccLogin();
        String defIconUrl = "";
        String zhName = "";
        List<LiveGameLangSaveReq> langList = req.getLangList();
        for (LiveGameLangSaveReq item : langList) {
            if(LangConstants.LANG_CN.equals(item.getLang())){
                defIconUrl = item.getIconUrl();
                zhName = item.getName();
                break;
            }
        }

        if(StrUtil.isBlank(defIconUrl) || StrUtil.isBlank(zhName)){
            throw new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }

        Long id = req.getId();
        LiveGame game = new LiveGame();
        BeanCopyUtil.copyProperties(req,game);
        game.setIconUrl(defIconUrl);
        game.setName(zhName);
        if(id==null){
            game.setCreateUser(admin);
            liveGameService.save(game);
            id = game.getId();
        }else{
            game.setUpdateUser(admin);
            liveGameService.updateById(game);
        }

        List<LiveGameLang> addList = new LinkedList<>();
        List<LiveGameLang> updateList = new LinkedList<>();

        for (LiveGameLangSaveReq item : langList) {
            LiveGameLang lang = new LiveGameLang();
            BeanCopyUtil.copyProperties(item,lang);
            lang.setGameId(id);
            if(item.getId()==null){
                addList.add(lang);
            }else{
                updateList.add(lang);
            }
        }
        if (addList.size() > 0) {
            MysqlMethod.batchInsert(LiveGameLang.class,addList);
        }
        if (updateList.size() > 0) {
            MysqlMethod.batchUpdate(LiveGameLang.class,updateList);
        }

    }

    /**
     * 查询游戏信息
     * @param req
     * @return
     */
    public LiveGameVO getGame(LongIdReq req) {
        if(req==null || req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LiveGame game = liveGameService.getById(req.getId());
        if(game==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        if(game.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        LiveGameVO vo = new LiveGameVO();
        BeanCopyUtil.copyProperties(game,vo);
        //查询多语言
        QueryWrapper<LiveGameLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveGameLang::getGameId, game.getId());
        List<LiveGameLang> langList = liveGameLangService.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(langList)){
            List<LiveGameLangVO> langVOList = BeanCopyUtil.copyCollection(langList, LiveGameLangVO.class);
            vo.setLangList(langVOList);
        }
        return vo;
    }

    /**
     * 删除游戏信息
     * @param req
     * @param loginUser
     */
    public void delGame(LongIdReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401, "没有权限操作");
        }
        if(req==null || req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LiveGame game = liveGameService.getById(req.getId());
        if(game==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        if(game.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        game.setIsDelete(true);
        game.setUpdateUser(loginUser.getAccLogin());
        liveGameService.updateById(game);
    }

    /**
     * 查询游戏列表, 用于选择
     * @return
     */
    public List<LiveGameSelectVO> getSelectList() {
        List<LiveGameSelectVO> list = new LinkedList<>();
        //查询私彩
        LiveGameSelectVO vo = new LiveGameSelectVO();
        vo.setName("彩票游戏");
        vo.setCategoryId(8000);
        QueryWrapper<Lottery> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(Lottery::getIsDelete,false);
        List<Lottery> lotteryList = lotteryService.list(queryWrapper);
        List<LiveGameItemSelectVO> voList = new LinkedList<>();
        vo.setGameList(voList);
        if(CollectionUtil.isNotEmpty(lotteryList)){
            for (Lottery lottery : lotteryList) {
                LiveGameItemSelectVO itemVO = new LiveGameItemSelectVO();
                itemVO.setIsThird(false);
                itemVO.setGameCode(lottery.getLotteryId().toString());
                itemVO.setName(lottery.getName());
                itemVO.setCategoryId(lottery.getCategoryId());
                voList.add(itemVO);
            }
            list.add(vo);
        }
        //查询第三方游戏
        QueryWrapper<GameCategory> categoryWrapper=new QueryWrapper<>();
        categoryWrapper.lambda().eq(GameCategory::getIsDelete,false);


        List<GameCategory> categoryList = gameCategoryService.list(categoryWrapper);
        if(CollectionUtil.isNotEmpty(categoryList)){
            categoryList.stream().forEach(category -> {
                LiveGameSelectVO cvo = new LiveGameSelectVO();
                QueryWrapper<GameThird> gameWrapper=new QueryWrapper<>();
                BeanCopyUtil.copyProperties(category,cvo);
                gameWrapper.lambda().eq(GameThird::getIsDelete,false).eq(GameThird::getCategoryId,category.getCategoryId());
                List<GameThird> gameThirdList = gameThirdService.list(gameWrapper);
                List<LiveGameItemSelectVO> itemList = new LinkedList<>();
                cvo.setGameList(itemList);
                if(CollectionUtil.isNotEmpty(gameThirdList)){
                    gameThirdList.stream().forEach(gameThird -> {
                        LiveGameItemSelectVO itemVO = new LiveGameItemSelectVO();
                        itemVO.setName(gameThird.getName());
                        itemVO.setGameCode(gameThird.getGameCode());
                        itemVO.setIsThird(true);
                        itemVO.setCategoryId(category.getCategoryId());
                        itemList.add(itemVO);
                    });
                    list.add(cvo);
                }
            });
        }
        return list;
    }

    /**
     * 直播游戏状态更新
     * @param req
     * @param loginUser
     */
    public void updateGameStatus(LiveGameStatusUpdateReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401, "没有权限操作");
        }
        if(req==null || req.getId()==null || req.getIsShow()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LiveGame game = liveGameService.getById(req.getId());
        if(game==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        if(game.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        game.setIsShow(req.getIsShow());
        game.setUpdateUser(loginUser.getAccLogin());
        liveGameService.updateById(game);
    }
}
