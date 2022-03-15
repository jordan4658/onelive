package com.onelive.manage.modules.live.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.live.LiveBagLangSaveReq;
import com.onelive.common.model.req.live.LiveBagListReq;
import com.onelive.common.model.req.live.LiveBagSaveReq;
import com.onelive.common.model.vo.live.LiveBagLangVo;
import com.onelive.common.model.vo.live.LiveBagListVO;
import com.onelive.common.model.vo.live.LiveBagVO;
import com.onelive.common.mybatis.entity.LiveBag;
import com.onelive.common.mybatis.entity.LiveBagLang;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.live.LiveBagLangService;
import com.onelive.manage.service.live.LiveBagService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 背包管理业务类
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LiveBagBusiness {

    @Resource
    private LiveBagService liveBagService;
    @Resource
    private LiveBagLangService liveBagLangService;

    /**
     * 查询背包列表
     * @param param
     * @return
     */
    public ResultInfo<PageInfo<LiveBagListVO>> getList(LiveBagListReq param) {
        PageInfo<LiveBagListVO> pageInfo = liveBagService.getList(param);
        return ResultInfo.ok(pageInfo);
    }

    /**
     * 保存背包数据
     * @param req
     * @param loginUser
     */
    @Transactional
    public void saveBag(LiveBagSaveReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401,"没有操作权限");
        }

        Long bagId = req.getId();

        String operateUser = loginUser.getAccLogin();

        List<LiveBagLang> addList = new LinkedList<>();
        List<LiveBagLang> updateList = new LinkedList<>();
        List<LiveBagLangSaveReq> langList = req.getLangList();

        if(langList==null || langList.size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        List<String> countryNameList = req.getCountryNameList();
        List<String> countryCodeList = req.getCountryCodeList();
        if(countryNameList==null || countryNameList.size()==0
        || countryCodeList==null || countryCodeList.size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        String bagName = "";

        for (LiveBagLangSaveReq item : langList) {
            if (item.getLang().equals("zh_CN")) {
                bagName = item.getBagName();
                break;
            }
        }

        if (StrUtil.isBlank(bagName)) {
            throw new BusinessException("请设置中文语言内容");
        }

        StringBuilder countryNameBuff = new StringBuilder();
        StringBuilder countryCodeBuff = new StringBuilder();

        for (String name:countryNameList){
            countryNameBuff.append(name).append(",");
        }
        for (String code:countryCodeList){
            countryCodeBuff.append(code).append(",");
        }
        countryNameBuff.setLength(countryNameBuff.length()-1);
        countryCodeBuff.setLength(countryCodeBuff.length()-1);

        if(bagId==null){
            //添加
            LiveBag bag = new LiveBag();
            BeanCopyUtil.copyProperties(req,bag);
            bag.setCountryCodeList(countryCodeBuff.toString());
            bag.setCountryNameList(countryNameBuff.toString());
            bag.setBagName(bagName);
            bag.setCreateUser(operateUser);
            liveBagService.save(bag);
            bagId = bag.getId();
            for (LiveBagLangSaveReq item:langList) {
                LiveBagLang lang = new LiveBagLang();
                BeanCopyUtil.copyProperties(item,lang);
                lang.setBagId(bagId);
                addList.add(lang);
            }
        }else{
            //更新
            LiveBag dbBag = liveBagService.getById(bagId);
            if(dbBag==null){
                throw new BusinessException("找不到数据!");
            }
            LiveBag bag = new LiveBag();
            BeanCopyUtil.copyProperties(req,bag);
            bag.setCountryCodeList(countryCodeBuff.toString());
            bag.setCountryNameList(countryNameBuff.toString());
            bag.setBagName(bagName);
            bag.setUpdateUser(operateUser);
            liveBagService.updateById(bag);
            for (LiveBagLangSaveReq item:langList) {
                LiveBagLang lang = new LiveBagLang();
                BeanCopyUtil.copyProperties(item,lang);
                lang.setBagId(bagId);
                if(item.getId()==null) {
                    addList.add(lang);
                }else{
                    updateList.add(lang);
                }
            }
        }

        //添加和更新多语言内容
        if(addList.size()>0){
            MysqlMethod.batchInsert(LiveBagLang.class,addList);
        }
        if(updateList.size()>0){
            MysqlMethod.batchUpdate(LiveBagLang.class,updateList);
        }
    }

    /**
     * 查询背包物品信息
     * @param id
     * @return
     */
    public LiveBagVO getBag(Long id) {
        LiveBag bag = liveBagService.getById(id);
        if(bag==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        LiveBagVO vo = new LiveBagVO();
        BeanCopyUtil.copyProperties(bag,vo);
        String countryCodeList = bag.getCountryCodeList();
        String countryNameList = bag.getCountryNameList();
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

        //查询多语言列表
        QueryWrapper<LiveBagLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveBagLang::getBagId,id);
        List<LiveBagLang> list = liveBagLangService.list(queryWrapper);

        List<LiveBagLangVo> langVoList = BeanCopyUtil.copyCollection(list, LiveBagLangVo.class);
        vo.setLangList(langVoList);
        return vo;
    }


    /**
     * 删除背包物品
     * @param id
     * @param loginUser
     */
    public void deleteBag(Long id, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401,"没有操作权限");
        }
        liveBagService.deleteBag(id,loginUser.getAccLogin());
    }
}
