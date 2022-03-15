package com.onelive.manage.modules.live.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.lottery.LiveNoticeTextLangSaveReq;
import com.onelive.common.model.req.lottery.LiveNoticeTextSaveReq;
import com.onelive.common.model.vo.live.LiveConfigVo;
import com.onelive.common.model.vo.live.notice.LiveNoticeTextLangVO;
import com.onelive.common.model.vo.live.notice.LiveNoticeTextListVO;
import com.onelive.common.model.vo.live.notice.LiveNoticeTextVO;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.LiveNoticeText;
import com.onelive.common.mybatis.entity.LiveNoticeTextLang;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.manage.service.live.LiveNoticeTextLangService;
import com.onelive.manage.service.live.LiveNoticeTextService;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Component
public class LiveConfigBusiness {

    @Resource
    private SysParameterService sysParameterService;
    @Resource
    private LiveNoticeTextService liveNoticeTextService;
    @Resource
    private LiveNoticeTextLangService liveNoticeTextLangService;

    /**
     * 查询配置列表
     *
     * @return
     */
    public List<LiveConfigVo> getList() {
        List<SysParameterListVO> byType = sysParameterService.getListByType(SysParameterConstants.LIVE_CONFIG);
        return BeanCopyUtil.copyCollection(byType, LiveConfigVo.class);
    }

    /**
     * 编辑配置列表
     *
     * @param liveConfigVos
     */
    public void update(List<LiveConfigVo> liveConfigVos) {
        List<SysParameter> copyCollection = BeanCopyUtil.copyCollection(liveConfigVos, SysParameter.class);
        for (SysParameter sysParameter : copyCollection) {
            sysParameterService.updateById(sysParameter);
             //更新缓存
            SystemRedisUtils.addSysParameter(sysParameter);
        }
    }

    /**
     * 查询中奖公告配置列表
     *
     * @return
     */
    public List<LiveNoticeTextListVO> getNoticeList() {
        QueryWrapper<LiveNoticeText> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveNoticeText::getIsDelete, false);
        List<LiveNoticeText> list = liveNoticeTextService.list(queryWrapper);
        if(CollectionUtil.isEmpty(list)){
            return new LinkedList<>();
        }
        return BeanCopyUtil.copyCollection(list, LiveNoticeTextListVO.class);
    }

    /**
     * 保存公告配置
     *
     * @param req
     * @param loginUser
     */
    public void saveNotice(LiveNoticeTextSaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有操作权限!");
        }

        if (req.getIsShow() == null || req.getMinAmount() == null || req.getMaxAmount() == null
                || req.getLangList() == null || req.getLangList().size() == 0) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        BigDecimal minAmount = req.getMinAmount();
        BigDecimal maxAmount = req.getMaxAmount();
        if(!req.getIsShow() && minAmount.compareTo(BigDecimal.ZERO)==0 && maxAmount.compareTo(BigDecimal.ZERO)==0){
            throw new BusinessException("默认数据不可隐藏!");
        }

        QueryWrapper<LiveNoticeText> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveNoticeText::getIsShow,true).eq(LiveNoticeText::getIsDelete, false);
        List<LiveNoticeText> list = liveNoticeTextService.list(queryWrapper);

        Long id = req.getId();

        if (CollectionUtil.isEmpty(list)) {
            if (minAmount.compareTo(BigDecimal.ZERO) != 0 || maxAmount.compareTo(BigDecimal.ZERO) != 0) {
                throw new BusinessException("请先设置一条0-0的数据作为默认数据!");
            }
        } else {
            for (LiveNoticeText config : list) {
                if(id!=null && config.getId().equals(id)){
                    continue;
                }
                BigDecimal itemMinAmount = config.getMinAmount();
                BigDecimal itemMaxAmount = config.getMaxAmount();
                if (minAmount.compareTo(itemMinAmount) >= 0 && minAmount.compareTo(itemMaxAmount) <= 0) {
                    throw new BusinessException("起始金额在其他数据的区间范围中!");
                }
                if (maxAmount.compareTo(itemMinAmount) >= 0 && maxAmount.compareTo(itemMaxAmount) <= 0) {
                    throw new BusinessException("结束金额在其他数据的区间范围中!");
                }
            }
        }

        String text = "";
        List<LiveNoticeTextLangSaveReq> langList = req.getLangList();
        for (LiveNoticeTextLangSaveReq item : langList) {
            if(LangConstants.LANG_CN.equals(item.getLang())){
                text = item.getText();
                break;
            }
        }

        if(StrUtil.isBlank(text)){
            throw new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }

        String admin = loginUser.getAccLogin();
        LiveNoticeText config = new LiveNoticeText();
        BeanCopyUtil.copyProperties(req,config);
        config.setText(text);
        if(id==null){
            //判断是否重复添加默认数据
            if(minAmount.compareTo(BigDecimal.ZERO)==0 && maxAmount.compareTo(BigDecimal.ZERO)==0){
                QueryWrapper<LiveNoticeText> defQueryWrapper = new QueryWrapper<>();
                defQueryWrapper.lambda().eq(LiveNoticeText::getIsShow,true).eq(LiveNoticeText::getIsDelete, false).eq(LiveNoticeText::getMinAmount,0).eq(LiveNoticeText::getMaxAmount,0);
                int count = liveNoticeTextService.count(defQueryWrapper);
                if(count>0) {
                    throw new BusinessException("默认数据不可重复添加!");
                }
            }
            config.setCreateUser(admin);
            liveNoticeTextService.save(config);
            id = config.getId();
        }else {
            LiveNoticeText dbConfig = liveNoticeTextService.getById(id);
            if(dbConfig==null){
                throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
            }
            if(dbConfig.getIsDelete()){
                throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
            }
            if(dbConfig.getMinAmount().compareTo(BigDecimal.ZERO)==0
                    && dbConfig.getMaxAmount().compareTo(BigDecimal.ZERO)==0
                    && (minAmount.compareTo(BigDecimal.ZERO)!=0 || maxAmount.compareTo(BigDecimal.ZERO)!=0)){
                throw new BusinessException("默认数据的范围不可更改!");
            }
            config.setUpdateUser(admin);
            liveNoticeTextService.updateById(config);
        }

        List<LiveNoticeTextLang> addList = new LinkedList<>();
        List<LiveNoticeTextLang> updateList = new LinkedList<>();
        for (LiveNoticeTextLangSaveReq item : langList) {
            LiveNoticeTextLang lang = new LiveNoticeTextLang();
            BeanCopyUtil.copyProperties(item,lang);
            lang.setConfigId(id);
            if(item.getId()==null){
                addList.add(lang);
            }else {
                updateList.add(lang);
            }
        }
        if(addList.size()>0){
            MysqlMethod.batchInsert(LiveNoticeTextLang.class,addList);
        }
        if(updateList.size()>0){
            MysqlMethod.batchUpdate(LiveNoticeTextLang.class,updateList);
        }

        //清除文案缓存数据
        SystemRedisUtils.deleteLiveNoticeTextCaches();
    }

    /**
     *
     * @param req
     * @return
     */
    public LiveNoticeTextVO getNotice(LongIdReq req) {
        if(req==null || req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LiveNoticeText config = liveNoticeTextService.getById(req.getId());
        if(config==null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(config.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        LiveNoticeTextVO vo = new LiveNoticeTextVO();
        BeanCopyUtil.copyProperties(config,vo);

        //查询多语言
        QueryWrapper<LiveNoticeTextLang> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveNoticeTextLang::getConfigId,req.getId());
        List<LiveNoticeTextLang> list = liveNoticeTextLangService.list(queryWrapper);
        List<LiveNoticeTextLangVO> langVOList = BeanCopyUtil.copyCollection(list, LiveNoticeTextLangVO.class);
        vo.setLangList(langVOList);
        return vo;
    }

    /**
     * 删除公告信息
     * @param req
     * @param loginUser
     */
    public void delNotice(LongIdReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有操作权限!");
        }
        if(req==null || req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LiveNoticeText config = liveNoticeTextService.getById(req.getId());
        if(config==null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(config.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        BigDecimal minAmount = config.getMinAmount();
        BigDecimal maxAmount = config.getMaxAmount();
        if(minAmount.compareTo(BigDecimal.ZERO)==0 && maxAmount.compareTo(BigDecimal.ZERO)==0){
            throw new BusinessException("默认数据不可删除!");
        }
        config.setIsDelete(true);
        config.setUpdateUser(loginUser.getAccLogin());
        liveNoticeTextService.updateById(config);
    }
}
