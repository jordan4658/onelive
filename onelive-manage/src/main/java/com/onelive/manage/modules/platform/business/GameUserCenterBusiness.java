package com.onelive.manage.modules.platform.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.platform.GameUserCenterListReq;
import com.onelive.common.model.req.platform.GameUserCenterSaveReq;
import com.onelive.common.model.vo.platform.GameUserCenterListVO;
import com.onelive.common.model.vo.platform.GameUserCenterVO;
import com.onelive.common.mybatis.entity.GameUserCenter;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.game.GameUserCenterService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 首页游戏配置业务类
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameUserCenterBusiness {

    @Resource
    private GameUserCenterService gameService;

    /**
     * 首页游戏列表
     * @param req
     * @return
     */
    public PageInfo<GameUserCenterListVO> getGameList(GameUserCenterListReq req) {
        QueryWrapper<GameUserCenter> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameUserCenter::getIsDelete,false).eq(GameUserCenter::getCountryCode,req.getCountryCode());
        PageInfo<GameUserCenter> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo, GameUserCenterListVO.class);
    }

    /**
     * 首页游戏保存信息
     * @param req
     * @param loginUser
     */
    public void saveGame(GameUserCenterSaveReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401, "没有权限操作");
        }
        if(req==null || StrUtil.isBlank(req.getName()) || StringUtils.isBlank(req.getGameCode()) || req.getCategoryId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String admin = loginUser.getAccLogin();
        GameUserCenter game = new GameUserCenter();
        BeanCopyUtil.copyProperties(req,game);
        Long id = req.getId();
        if(id==null){
            game.setCreateUser(admin);
            gameService.save(game);
            id = game.getId();
        }else{
            game.setUpdateUser(admin);
            gameService.updateById(game);
        }
    }

    /**
     * 首页游戏信息查询
     * @param req
     * @return
     */
    public GameUserCenterVO getGame(LongIdReq req) {
        if(req==null || req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        GameUserCenter game = gameService.getById(req.getId());
        if(game==null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(game.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        GameUserCenterVO vo = new GameUserCenterVO();
        BeanCopyUtil.copyProperties(game,vo);
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
        GameUserCenter game = gameService.getById(req.getId());
        if(game==null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(game.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        game.setIsDelete(true);
        game.setUpdateUser(loginUser.getAccLogin());
        gameService.updateById(game);
    }

}
