package com.onelive.manage.modules.sys.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.SysAdvActivitySkipModel;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysAdvActivityLangSaveReq;
import com.onelive.common.model.req.sys.SysAdvActivitySaveReq;
import com.onelive.common.model.vo.sys.SysAdvActivityLangVO;
import com.onelive.common.model.vo.sys.SysAdvActivityListVO;
import com.onelive.common.model.vo.sys.SysAdvActivitySelectVO;
import com.onelive.common.model.vo.sys.SysAdvActivityVO;
import com.onelive.common.mybatis.entity.SysAdvActivity;
import com.onelive.common.mybatis.entity.SysAdvActivityLang;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.sys.SysAdvActivityLangService;
import com.onelive.manage.service.sys.SysAdvActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 活动管理业务类
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SysAdvActivityBusiness {
    @Resource
    private SysAdvActivityService sysAdvActivityService;
    @Resource
    private SysAdvActivityLangService sysAdvActivityLangService;

    /**
     * 查询活动列表
     * @param req
     * @return
     */
    public PageInfo<SysAdvActivityListVO> getActivityList(PageReq req) {
        PageInfo<SysAdvActivity> pageInfo = sysAdvActivityService.getActivityList(req);
        return PageInfoUtil.pageInfo2DTO(pageInfo, SysAdvActivityListVO.class);
    }


    /**
     * 保存活动数据
     * @param req
     * @param loginUser
     */
    @Transactional
    public void save(SysAdvActivitySaveReq req, LoginUser loginUser) {

        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        //校验参数
        String skipModel = req.getSkipModel();
        if(SysAdvActivitySkipModel.ACTIVITY.getModel().equals(skipModel) && req.getConfigId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        if(SysAdvActivitySkipModel.GAME.getModel().equals(skipModel) && (req.getLotteryCategoryId()==null || req.getLotteryId()==null)){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //取出语言列表
        List<SysAdvActivityLangSaveReq> langList = req.getLangList();

        if (langList == null || langList.size() == 0) {
            throw new BusinessException("语言列表不能为空");
        }

        String activityName = "";

        for (SysAdvActivityLangSaveReq item : langList) {
            if (item.getLang().equals("zh_CN")) {
                activityName = item.getActivityName();
                break;
            }
        }

        if (StrUtil.isBlank(activityName)) {
            throw new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }

        List<String> countryNameList = req.getCountryNameList();
        List<String> countryCodeList = req.getCountryCodeList();
        if(countryNameList==null || countryNameList.size()==0
                || countryCodeList==null || countryCodeList.size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        StringBuilder countryNameBuff = new StringBuilder();
        StringBuilder countryCodeBuff = new StringBuilder();

        for (String name:countryNameList){
            countryNameBuff.append(name).append(",");
        }
        for (String id:countryCodeList){
            countryCodeBuff.append(id).append(",");
        }
        countryNameBuff.setLength(countryNameBuff.length()-1);
        countryCodeBuff.setLength(countryCodeBuff.length()-1);

        String operateUser = loginUser.getAccLogin();

        SysAdvActivity active = new SysAdvActivity();

        BeanUtil.copyProperties(req, active);
        active.setActivityName(activityName);
        active.setCountryCodeList(countryCodeBuff.toString());
        active.setCountryNameList(countryNameBuff.toString());
        //判断是新增还是更新
        List<SysAdvActivityLang> itemAddList = new ArrayList<>();
        List<SysAdvActivityLang> itemUpdateList = new ArrayList<>();
        Long activityId = active.getId();
        if (activityId == null) {
            active.setCreateUser(operateUser);
            sysAdvActivityService.save(active);
            for (SysAdvActivityLangSaveReq item : langList) {
                SysAdvActivityLang langItem = new SysAdvActivityLang();
                BeanUtil.copyProperties(item, langItem);
                langItem.setActivityId(active.getId());
                langItem.setCreateUser(operateUser);
                itemAddList.add(langItem);
            }
        } else {
            //先查询， 再更新
            SysAdvActivity dbActivity = sysAdvActivityService.getById(activityId);
            if(dbActivity==null){
                throw new BusinessException("该活动不存在");
            }
            active.setUpdateUser(operateUser);
            sysAdvActivityService.updateById(active);
            for (SysAdvActivityLangSaveReq item : langList) {
                SysAdvActivityLang langItem = new SysAdvActivityLang();
                BeanUtil.copyProperties(item, langItem);
                langItem.setActivityId(activityId);
                if(item.getId()==null){
                    //需要新增的内容
                    langItem.setCreateUser(operateUser);
                    itemAddList.add(langItem);
                }else{
                    //需要更新的内容
                    langItem.setUpdateUser(operateUser);
                    itemUpdateList.add(langItem);
                }
            }
        }

        if(itemAddList.size()>0){
            MysqlMethod.batchInsert(SysAdvActivityLang.class,itemAddList);
        }
        if(itemUpdateList.size()>0){
            MysqlMethod.batchUpdate(SysAdvActivityLang.class,itemUpdateList);
        }

    }

    /**
     * 删除活动
     * @param id
     * @param loginUser
     */
    public void deleteActivity(Long id, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        SysAdvActivity activity = sysAdvActivityService.getById(id);
        if(activity==null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(activity.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        sysAdvActivityService.deleteActivity(id,loginUser.getAccLogin());
        sysAdvActivityLangService.deleteActivityLangByActiveId(id,loginUser.getAccLogin());
    }

    /**
     * 根据ID查询活动
     * @param id
     * @return
     */
    public SysAdvActivityVO getActivity(Long id) {

        SysAdvActivity activity = sysAdvActivityService.getById(id);
        if(activity==null){
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        SysAdvActivityVO vo = new SysAdvActivityVO();
        BeanCopyUtil.copyProperties(activity,vo);

        String countryCodeList = activity.getCountryCodeList();
        String countryNameList = activity.getCountryNameList();
        if(StrUtil.isNotBlank(countryCodeList)){
            String[] arr = countryCodeList.split(",");
            List<String> codeList = new LinkedList<>();
            for(String idStr:arr){
                codeList.add(idStr);
            }
            vo.setCountryCodeList(codeList);
        }
        if(StrUtil.isNotBlank(countryNameList)){
            String[] arr = countryNameList.split(",");
            List<String> nameList = new LinkedList<>();
            for(String name:arr){
                nameList.add(name);
            }
            vo.setCountryNameList(nameList);
        }

        List<SysAdvActivityLang> list = sysAdvActivityLangService.getListByActivityId(id);
        List<SysAdvActivityLangVO> voList = BeanCopyUtil.copyCollection(list, SysAdvActivityLangVO.class);
        vo.setLangList(voList);
        return vo;
    }

    /**
     * 查询活动列表-用于选择
     * @return
     */
    public List<SysAdvActivitySelectVO> getSelectList() {
        QueryWrapper<SysAdvActivity> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SysAdvActivity::getIsDelete,false);
        List<SysAdvActivity> list = sysAdvActivityService.list(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            return new LinkedList<>();
        }
        return BeanCopyUtil.copyCollection(list,SysAdvActivitySelectVO.class);
    }
}
