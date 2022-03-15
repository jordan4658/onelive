package com.onelive.manage.modules.platform.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.AppRouteEnums;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.platform.GameIndexLangSaveReq;
import com.onelive.common.model.req.platform.GameIndexListReq;
import com.onelive.common.model.req.platform.GameIndexSaveReq;
import com.onelive.common.model.vo.platform.AppRouteVO;
import com.onelive.common.model.vo.platform.GameIndexLangVO;
import com.onelive.common.model.vo.platform.GameIndexListVO;
import com.onelive.common.model.vo.platform.GameIndexVO;
import com.onelive.common.mybatis.entity.GameIndex;
import com.onelive.common.mybatis.entity.GameIndexLang;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.game.GameIndexLangService;
import com.onelive.manage.service.game.GameIndexService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 首页游戏配置业务类
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameIndexBusiness {

    @Resource
    private GameIndexService gameIndexService;
    @Resource
    private GameIndexLangService gameIndexLangService;

    /**
     * 首页游戏列表
     * @param req
     * @return
     */
    public PageInfo<GameIndexListVO> getGameList(GameIndexListReq req) {
        QueryWrapper<GameIndex> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameIndex::getIsDelete,false).eq(GameIndex::getCountryCode,req.getCountryCode());
        PageInfo<GameIndex> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameIndexService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo, GameIndexListVO.class);
    }

    /**
     * 首页游戏保存信息
     * @param req
     * @param loginUser
     */
    @Transactional
    public void saveGame(GameIndexSaveReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401, "没有权限操作");
        }
        if(req==null || req.getLangList()==null || req.getLangList().size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //参数校验
//        跳转模式 1.链接 2.游戏 3.APP内
        Integer skipModel = req.getSkipModel();
        switch (skipModel){
            case 1://链接
                if(StrUtil.isBlank(req.getSkipUrl()) || req.getSkipType()==null){
                    throw new BusinessException(StatusCode.PARAM_ERROR);
                }
                break;
            case 2://游戏
                if(req.getCategoryId()==null || StrUtil.isBlank(req.getGameCode())){
                    throw new BusinessException(StatusCode.PARAM_ERROR);
                }
                break;
            case 3://APP内
                if(StrUtil.isBlank(req.getRoute())){
                    throw new BusinessException(StatusCode.PARAM_ERROR);
                }

                //判断页面参数
                if(AppRouteEnums.LIVE.getRoute().equals(req.getRoute())){
                    //直播参数
                    if(StrUtil.isBlank(req.getStudioNum()) || StrUtil.isBlank(req.getSource())){
                        throw new BusinessException(StatusCode.PARAM_ERROR);
                    }
                    req.setParams("studioNum="+req.getStudioNum()+"&source="+req.getSource());
                }else if(AppRouteEnums.ACTIVITY.getRoute().equals(req.getRoute())){
                    if(req.getActId()==null){
                        throw new BusinessException(StatusCode.PARAM_ERROR);
                    }
                    req.setParams("act="+req.getActId());
                }
                break;
        }

        String name = "";
        String defaultIcon = "";
        List<GameIndexLangSaveReq> langList = req.getLangList();
        for (GameIndexLangSaveReq item : langList) {
            if(LangConstants.LANG_CN.equals(item.getLang())){
                name = item.getName();
                defaultIcon = item.getIconUrl();
                break;
            }
        }
        if(StrUtil.isBlank(name)){
            throw new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }
        String admin = loginUser.getAccLogin();
        GameIndex game = new GameIndex();
        BeanCopyUtil.copyProperties(req,game);
        game.setName(name);
        game.setIconUrl(defaultIcon);
        Long id = req.getId();
        if(id==null){
            game.setCreateUser(admin);
            gameIndexService.save(game);
            id = game.getId();
        }else{
            game.setUpdateUser(admin);
            gameIndexService.updateById(game);
        }

        //处理多语言
        List<GameIndexLang> addList = new LinkedList<>();
        List<GameIndexLang> updateList = new LinkedList<>();
        for (GameIndexLangSaveReq item : langList) {
            GameIndexLang lang = new GameIndexLang();
            BeanCopyUtil.copyProperties(item,lang);
            lang.setGameIndexId(id);
            if(item.getId()==null){
                addList.add(lang);
            }else{
                updateList.add(lang);
            }
        }

        if (addList.size() > 0) {
            MysqlMethod.batchInsert(GameIndexLang.class,addList);
        }
        if (updateList.size() > 0) {
            MysqlMethod.batchUpdate(GameIndexLang.class,updateList);
        }

    }

    /**
     * 首页游戏信息查询
     * @param req
     * @return
     */
    public GameIndexVO getGame(LongIdReq req) {
        if(req==null || req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        GameIndex game = gameIndexService.getById(req.getId());
        if(game==null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(game.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        GameIndexVO vo = new GameIndexVO();
        BeanCopyUtil.copyProperties(game,vo);
        //查询多语言数据
        QueryWrapper<GameIndexLang> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameIndexLang::getGameIndexId,game.getId());
        List<GameIndexLang> list = gameIndexLangService.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            List<GameIndexLangVO> langVOList = BeanCopyUtil.copyCollection(list, GameIndexLangVO.class);
            vo.setLangList(langVOList);
        }
        return vo;
    }

    /**
     * 首页游戏信息删除
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
        GameIndex game = gameIndexService.getById(req.getId());
        if(game==null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(game.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        game.setIsDelete(true);
        game.setUpdateUser(loginUser.getAccLogin());
        gameIndexService.updateById(game);
    }


    /**
     * APP内页面-用于选择
     * @return
     */
    public List<AppRouteVO> listAppPage() {
        AppRouteEnums[] values = AppRouteEnums.values();
        List<AppRouteVO> voList = new LinkedList<>();
        for (AppRouteEnums en : values) {
            AppRouteVO vo = new AppRouteVO();
            vo.setName(en.getName());
            vo.setRoute(en.getRoute());
            voList.add(vo);
        }
        return voList;
    }
}
