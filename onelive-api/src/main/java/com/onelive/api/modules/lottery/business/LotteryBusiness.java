package com.onelive.api.modules.lottery.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.api.service.live.LiveGameService;
import com.onelive.api.service.live.LiveGameTagService;
import com.onelive.api.service.lottery.LotteryService;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.live.AppLiveGameListReq;
import com.onelive.common.model.vo.live.game.AppLiveGameListVO;
import com.onelive.common.model.vo.lottery.*;
import com.onelive.common.mybatis.entity.LiveGame;
import com.onelive.common.mybatis.entity.LiveGameTag;
import com.onelive.common.mybatis.entity.Lottery;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.mongodb.base.BaseMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class LotteryBusiness extends BaseMongoService {

    @Resource
    private LotteryService lotteryService;
    @Resource
    private SysParameterService sysParameterService;
    @Resource
    private LiveGameTagService liveGameTagService;
    @Resource
    private LiveGameService liveGameService;

    /**
     * 获取开房间时候的彩票列表
     * @return
     */
    public List<LotteryRoomVO> getLotteryRoomList(){
        //查询彩票数据
        List<LotteryRoomVO> voList = new LinkedList<>();
        QueryWrapper<Lottery> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(Lottery::getIsDelete,false).eq(Lottery::getIsWork,true);
        List<Lottery> list = lotteryService.listWithCurrentLang();//.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            list.stream().forEach(lottery -> {
                LotteryRoomVO vo = new LotteryRoomVO();
                vo.setIcon(lottery.getIcon());
                vo.setName(lottery.getName());
                vo.setId(lottery.getLotteryId());
                voList.add(vo);
            });
        }
        return voList;
    }

    /**
     * 查询生成彩种赔率玩法
     * @return
     */
    public LotteryZIPVO queryLotteryVersionZIP() {
        LotteryZIPVO zipvo = new LotteryZIPVO();
        String lang = LoginInfoUtil.getLang();
        //获取多语言列表
        List<String> langList = ApiBusinessRedisUtils.getLangList();
        if(StrUtil.isBlank(lang) || !langList.contains(lang)){
            lang = LangConstants.LANG_CN;
        }
        String paramCode = SysParamEnum.LOTTERY_VERSION_ZIP_URL.getCode()+ "_" + lang;
        SysParameter zipInfo = sysParameterService.getByCode(paramCode);
        zipvo.setLang(lang);
        //如果没找到数据, 再根据中文语言查询一次
        if(zipInfo==null){
            paramCode = SysParamEnum.LOTTERY_VERSION_ZIP_URL.getCode()+ "_" + LangConstants.LANG_CN;
            zipInfo = sysParameterService.getByCode(paramCode);
            if(zipInfo!=null){
                zipvo.setLang(LangConstants.LANG_CN);
            }
        }
        zipvo.setDownURL(zipInfo.getParamValue().split(SymbolConstant.COMMA)[1]);
        zipvo.setVersion( zipInfo.getRemark());
        return  zipvo;
    }

    /**
     * 直播间游戏code值列表
     * @return
     */
    public List<LotteryGameCodeListVO> getGameCodeList(){
       /* List<LotteryGameCodeListVO> voList = new ArrayList<>();
        GameCodeEnums[] enums =  GameCodeEnums.values();
        for(int i =0 ; i < enums.length; i++){
            LotteryGameCodeListVO vo = new LotteryGameCodeListVO();
            GameCodeEnums bo = enums[i];
            vo.setCode(bo.getCode());
            vo.setName(localeMessageSourceService.getMessage(bo.getMsg()));
            voList.add(vo);
        }*/
        List<LiveGameTag> list = liveGameTagService.listWithLang();
        if(CollectionUtil.isNotEmpty(list)){
            return  BeanCopyUtil.copyCollection(list,LotteryGameCodeListVO.class);
        }
        return new LinkedList<>();
    }

    /**
     * 直播间游戏列表
     * @param code
     * @return
     */
    public List<LotteryGameListVO> getLiveGameList(String code){
//        List<LotteryGameListVO> voList = new ArrayList<>();
        /*GameCodeEnums getCode = GameCodeEnums.getByCode(code);
        switch (getCode){
            case LOTTERY:
                voList = lotteryCountryService.getLotteryGameList(null);
                break;

            case CHESS:
                voList = getThirdGameList(GameCodeEnums.CHESS.getCode());
                break;

            case FISHING:
                voList = getThirdGameList(GameCodeEnums.FISHING.getCode());
                break;

            case ELECTRONIC:
                voList = getThirdGameList(GameCodeEnums.ELECTRONIC.getCode());
                break;

            case VIDEO:
                voList = getThirdGameList(GameCodeEnums.VIDEO.getCode());
                break;

            default: //热门
                ///根据后台配置好的规则捞取热门数据 TODO
                voList = lotteryCountryService.getLotteryGameList(GameCodeEnums.HOT.getCode());
                break;
        }*/
        if(StrUtil.isBlank(code)){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        AppLiveGameListReq req=new AppLiveGameListReq();
        req.setCode(code);
        List<LiveGame> list = liveGameService.listWithLang(req);
        if(CollectionUtil.isNotEmpty(list)){
            return BeanCopyUtil.copyCollection(list,LotteryGameListVO.class);
        }
        return new LinkedList<>();
    }

    /**
     * 判断第三方游戏是否可以跳转
     * @param gameNo
     * @return
     */
    public LotteryGameCheckJumpVO checkThirdCanJump(String gameNo){
       //TODO 对接第三方时候，看是否有接口判断，没有的话，统一返回true，默认可以跳转
        LotteryGameCheckJumpVO vo = new LotteryGameCheckJumpVO();
        vo.setValue(true);
        return vo;
    }



    ////////////////////////////////////////////第三方///////////////////////////////////////////////////
    /**
     * 获取非私彩类的直播间游戏列表 TODO 接入第三方后，需要获取真实数据
     * @param code
     * @return
     */
  /*  private List<LotteryGameListVO> getThirdGameList(String code){
        List<LotteryGameListVO> voList = new ArrayList<>();
        //遍历10个
        for(int i =0; i<10; i++){
            LotteryGameListVO vo = new LotteryGameListVO();
            vo.setIconUrl("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/b9bcca24a67d4c3182dc16c853f9c350.png");
            vo.setIsThird(true);
            vo.setCode(code);
            vo.setAliasName("aliasName"+code);
            vo.setIsCanJump(true);
            vo.setGameType(GameCodeEnums.getByCode(code).getCode());
            vo.setGameNo("123");
            vo.setJumpUrl("https://www.google.com/");
            voList.add(vo);
        }
        return voList;
    }*/


    /**
     * 查询直播间游戏标签列表
     * @return
     */
    public List<LiveGameTagListVO> getGameTagList() {
        List<LiveGameTag> list = liveGameTagService.listWithLang();
        if(CollectionUtil.isEmpty(list)){
            return new LinkedList<>();
        }
        return BeanCopyUtil.copyCollection(list,LiveGameTagListVO.class);
    }

    /**
     * 根据标签查询游戏列表
     * @param req
     * @return
     */
    public List<AppLiveGameListVO> getGameListByTag(AppLiveGameListReq req) {
        List<LiveGame> list = liveGameService.listWithLang(req);
        if(CollectionUtil.isEmpty(list)){
            return new LinkedList<>();
        }
        return BeanCopyUtil.copyCollection(list,AppLiveGameListVO.class);
    }
}
