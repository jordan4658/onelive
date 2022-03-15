package com.onelive.manage.modules.lottery.business;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.other.UploadConstants;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.GameCodeEnums;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.dto.lottery.LotteryInfoDTO;
import com.onelive.common.model.req.lottery.LotteryCategoryAddReq;
import com.onelive.common.model.req.lottery.LotteryCategoryLangReq;
import com.onelive.common.model.req.lottery.LotteryCategorySaveReq;
import com.onelive.common.model.vo.lottery.LotteryCategoryEditVO;
import com.onelive.common.model.vo.lottery.LotteryCategoryLangVO;
import com.onelive.common.model.vo.lottery.LotteryCategoryVO;
import com.onelive.common.model.vo.lottery.LotteryZIPVO;
import com.onelive.common.model.vo.sys.ShortMsgVO;
import com.onelive.common.mybatis.entity.LotteryCategory;
import com.onelive.common.mybatis.entity.LotteryCategoryLang;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.common.utils.upload.AWSS3Util;
import com.onelive.manage.service.lottery.LotteryCategoryLangService;
import com.onelive.manage.service.lottery.LotteryCategoryService;
import com.onelive.manage.service.lottery.LotteryService;
import com.onelive.manage.service.sys.SysCountryService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LotteryCategoryBusiness {

    @Resource
    private LotteryCategoryService lotteryCategoryService;
    @Resource
    private LotteryCategoryLangService lotteryCategoryLangService;
    @Resource
    private LotteryService lotteryService;
    @Autowired
    private SysCountryService sysCountryService;
    @Resource
    private AWSS3Util aWSS3Util;

    /**
     * 获取彩种大类列表
     * @return
     */
    public PageInfo<LotteryCategoryVO> queryLotteryCategory(PageReq req){
        //单独使用 PageHelper.startPage 后面必须紧跟的方法是查询数据库的，不然会出现线程不安全的分页问题
        if(req == null){
            req = new PageReq();
        }
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        QueryWrapper<LotteryCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryCategory::getType, GameCodeEnums.LOTTERY.getCode());
        queryWrapper.lambda().eq(LotteryCategory::getIsDelete,false);
        queryWrapper.lambda().orderByDesc(LotteryCategory::getSort).orderByDesc(LotteryCategory::getCreateTime);
        List<LotteryCategory> list =   lotteryCategoryService.list(queryWrapper);
        PageInfo<ShortMsgVO> pageInfo = new PageInfo(list);
        return PageInfoUtil.pageInfo2DTO(pageInfo, LotteryCategoryVO.class);
    }

    /**
     * 获取对应Id的彩种大类信息
     * @param id
     * @return
     */
    public LotteryCategoryEditVO getLotteryCategoryById(Integer id){
        LotteryCategoryEditVO vo = new LotteryCategoryEditVO();
        LotteryCategory lotteryCategory =  lotteryCategoryService.getById(id);
        if(lotteryCategory != null){
            BeanCopyUtil.copyProperties(lotteryCategory,vo);
        }
        QueryWrapper<LotteryCategoryLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryCategoryLang::getCategoryId,id);
        List<LotteryCategoryLang> list = lotteryCategoryLangService.list(queryWrapper);
        List<LotteryCategoryLangVO> langList = BeanCopyUtil.copyCollection(list, LotteryCategoryLangVO.class);
        vo.setLangList(langList);
        return vo;
    }


    /**
     * 新增彩种
     * @param req
     */
    public void add(LotteryCategoryAddReq req){
        if (ObjectUtils.isEmpty(req) || req.getCategoryId() == null || StringUtils.isBlank(req.getName())) {
            throw  new BusinessException(StatusCode.PARAM_ERROR);
        }
        LotteryCategory category = lotteryCategoryService.getByCategoryId(req.getCategoryId());
        if(category != null){
            throw  new BusinessException(StatusCode.LOTTERY_CATEGORY_EXISTS.getCode(),StatusCode.LOTTERY_CATEGORY_EXISTS.getMsg());
        }
        category = new LotteryCategory();
        BeanCopyUtil.copyProperties(req,category);
        category.setType(GameCodeEnums.LOTTERY.getCode());
        lotteryCategoryService.save(category);
        SystemRedisUtils.deleteLotteryCaches();

    }


    /**
     * 保存彩种
     * @param req
     */
    @Transactional
    public void save(LotteryCategorySaveReq req){
        if (ObjectUtils.isEmpty(req) || req.getCategoryId() == null || req.getLangList()==null || req.getLangList().size()==0) {
            throw  new BusinessException(StatusCode.PARAM_ERROR);
        }
        Integer id = req.getId();

        List<LotteryCategoryLangReq> langList = req.getLangList();

        String categoryName = "";
        String categoryAlias = "";
        for (LotteryCategoryLangReq item : langList) {
            if(LangConstants.LANG_CN.equals(item.getLang())){
                categoryName = item.getName();
                categoryAlias = item.getAlias();
                break;
            }
        }

        if(StrUtil.isBlank(categoryName)){
            throw  new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }


        if(id==null){
            LotteryCategory category = lotteryCategoryService.getByCategoryId(req.getCategoryId());
            if(category != null){
                throw  new BusinessException(StatusCode.LOTTERY_CATEGORY_EXISTS.getCode(),StatusCode.LOTTERY_CATEGORY_EXISTS.getMsg());
            }
            category = new LotteryCategory();
            BeanCopyUtil.copyProperties(req,category);
            category.setName(categoryName);
            category.setAlias(categoryAlias);
            category.setType(GameCodeEnums.LOTTERY.getCode());
            lotteryCategoryService.save(category);
            id = category.getId();
        }else{
            LotteryCategory category = new LotteryCategory();
            BeanCopyUtil.copyProperties(req,category);
            category.setName(categoryName);
            category.setAlias(categoryAlias);
            lotteryCategoryService.updateById(category);
        }


        List<LotteryCategoryLang> addList = new LinkedList<>();
        List<LotteryCategoryLang> updateList = new LinkedList<>();
        for (LotteryCategoryLangReq item:langList) {
            LotteryCategoryLang lang = new LotteryCategoryLang();
            BeanCopyUtil.copyProperties(item,lang);
            lang.setCategoryId(id);
            if(item.getId()==null){
                addList.add(lang);
            }else{
                updateList.add(lang);
            }
        }

        if(addList.size()>0){
            MysqlMethod.batchInsert(LotteryCategoryLang.class,addList);
        }
        if(updateList.size()>0){
            MysqlMethod.batchUpdate(LotteryCategoryLang.class,updateList);
        }

        SystemRedisUtils.deleteLotteryCaches();
    }

    /**
     *
     *  删除彩票分类
     * @param id
     */
    public void del(Integer id){
        LotteryCategory category = lotteryCategoryService.getById(id);
        if (category == null || category.getIsDelete()) {
            throw new RuntimeException("该分类不存在或已删除！");
        }
//        category.setIsDelete(true);
//        category.setUpdateTime(new Date());
//        lotteryCategoryService.updateById(category);
        lotteryCategoryService.removeById(id);
        SystemRedisUtils.deleteLotteryCaches();
    }

    /**
     * 上传压缩文件
     * @return
     * @throws Exception
     */
    public LotteryZIPVO generateZIP() throws Exception{

        //获取多语言列表
        List<String> langList = SysBusinessRedisUtils.getLangList();

        if(CollectionUtils.isEmpty(langList)){
            //重新缓存一次
            SysBusinessRedisUtils.initCountryInfo(sysCountryService);
            langList = SysBusinessRedisUtils.getLangList();
            if(CollectionUtils.isEmpty(langList)){
                throw new BusinessException("多语言列表为空!");
            }
        }

        //产生时间戳的文件
        String str = String.valueOf(System.currentTimeMillis());
        //每个语种生成一个文件
        langList = langList.stream().distinct().collect(Collectors.toList());
        for (String lang : langList) {
            List<LotteryInfoDTO> list = lotteryService.queryLotteryAllInfo(lang);
            if(CollectionUtils.isNotEmpty(list)) {
                String content = JSONObject.toJSONString(list);
                String fileNameTxt = lang + "_lottery" + str + ".txt";
                String fileNameZip = lang + "_lottery" + str + ".zip";
                String url1 = aWSS3Util.uploadString(content, UploadConstants.FILE, fileNameTxt);
                String url2 = aWSS3Util.uploadZip(content, UploadConstants.FILE, fileNameZip);
                //判断返回的文件目录 空则失败
                if (StringUtils.isNotEmpty(url1) && StringUtils.isNotEmpty(url2)) {
                    // 兼容H5 ...txt
                    lotteryService.updateLotteryVersionZIP(url1 + "," + url2, lang);
                    log.info("{}.updateLotteryVersionZIP更新上传彩票压缩文件信息成功!操作人:{}", getClass().getName(), LoginInfoUtil.getUserAccount());
                } else {
                    log.error("{}.updateLotteryVersionZIP更新压缩包失败，返回文件目录信息为空!", getClass().getName());
                }
            }
        }

/*

        List<LotteryInfoDTO> list = lotteryService.queryLotteryAllInfo(null);
        String content = JSONObject.toJSONString(list);
        //产生时间戳的文件
        String str = String.valueOf(System.currentTimeMillis());
        String fileNameTxt = "lottery" + str + ".txt";
        String fileNameZip = "lottery" + str + ".zip";

        String url1 = aWSS3Util.uploadString(content, UploadConstants.FILE, fileNameTxt);
        String url2 = aWSS3Util.uploadZip(content, UploadConstants.FILE, fileNameZip);

        //判断返回的文件目录 空则失败
        if (StringUtils.isNotEmpty(url1) && StringUtils.isNotEmpty(url2)) {
            // 兼容H5 ...txt
            lotteryService.updateLotteryVersionZIP(url1 + "," + url2);
            log.info("{}.updateLotteryVersionZIP更新上传彩票压缩文件信息成功!操作人:{}", getClass().getName(), LoginInfoUtil.getUserAccount());
        } else {
            log.error("{}.updateLotteryVersionZIP更新压缩包失败，返回文件目录信息为空!", getClass().getName());
        }
*/

        LotteryZIPVO zipVo = new LotteryZIPVO();
        return zipVo;
    }




}
