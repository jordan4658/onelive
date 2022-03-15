package com.onelive.manage.modules.sys.business;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.other.UploadConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.SysAppType;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.upload.FileDTO;
import com.onelive.common.model.req.sys.appversion.*;
import com.onelive.common.model.vo.sys.AppPackageInfoVO;
import com.onelive.common.model.vo.sys.SysAppVersionListVO;
import com.onelive.common.model.vo.sys.SysAppVersionVO;
import com.onelive.common.mybatis.entity.SysAppVersion;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.common.utils.upload.AWSS3Util;
import com.onelive.manage.service.sys.SysAppVersionService;
import com.onelive.manage.utils.other.AppPackageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

@Component
@Slf4j
public class SysAppVersionBusiness {

    @Resource
    private SysAppVersionService service;
    @Resource
    private AWSS3Util aWSS3Util;

    /**
     * 分页查询
     *
     * @return
     */
    public PageInfo<SysAppVersionListVO> getList(SysAppVersionListReq req) {
        PageInfo<SysAppVersion> pageInfo = service.getAllList(req);
        return PageInfoUtil.pageInfo2DTO(pageInfo, SysAppVersionListVO.class);
    }

    /**
     * 新增
     *
     * @param req
     */
    public void add(SysAppVersionAddReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        if(req==null || req.getAppType()==null || StringUtils.isBlank(req.getShowVersion())
                || req.getUpgradeVersion()==null|| StringUtils.isBlank(req.getDownUrl())
                || StringUtils.isBlank(req.getCode()) || req.getFileSize()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        if(!SysAppType.Android.getCode().equals(req.getCode()) && !SysAppType.IOS.getCode().equals(req.getCode())){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //获取最新版本

        QueryWrapper<SysAppVersion> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysAppVersion::getAppType,req.getAppType())
                .eq(SysAppVersion::getCode,req.getCode())
                .orderByDesc(SysAppVersion::getUpgradeVersion)
                .last(" LIMIT 1");
        SysAppVersion maxVersion = service.getBaseMapper().selectOne(queryWrapper);

        if(maxVersion!=null && maxVersion.getUpgradeVersion()>req.getUpgradeVersion()){
            throw new BusinessException("已有更高版本,请上传更高的版本!");
        }
        SysAppVersion p = new SysAppVersion();
        BeanUtil.copyProperties(req, p);
        p.setCreateUser(admin.getAccLogin());
        service.save(p);
    }

    /**
     * 编辑
     *
     * @param req
     */
    public void update(SysAppVersionUpdateReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req.getId() == null) {
            throw new BusinessException(101, "主键不能为空");
        }
        SysAppVersion byId = service.getById(req.getId());
        if (byId == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }

        SysAppVersion p = new SysAppVersion();
        p.setUpdateUser(admin.getAccLogin());
        BeanUtil.copyProperties(req, p);
        service.updateById(p);
    }


    /**
     * 根据ID查询信息
     *
     * @param req
     * @return
     */
    public SysAppVersionVO getInfo(SysAppVersionIdReq req) {
        SysAppVersion version = service.getById(req.getId());
        if (version == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        SysAppVersionVO vo = new SysAppVersionVO();
        BeanUtil.copyProperties(version, vo);
        return vo;
    }

    /**
     * 上传app安装文件
     *
     * @param multipartFile
     * @return
     */
    public AppPackageInfoVO uploadAppPackageFile(MultipartFile multipartFile) {
        if (null == multipartFile || org.apache.commons.lang3.StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2001.getCode(), StatusCode.UPLOAD_ERROR_2001.getMsg());
        }
        String fileName = multipartFile.getOriginalFilename();
        //检查文件格式
        boolean checkRule = this.fileSuffix(fileName);
        if (!checkRule) {
            throw new BusinessException("请上传后缀名 .apk .ipa 文件类型！");
        }
        AppPackageInfoVO vo = new AppPackageInfoVO();
        try {
            // 上传到 s3
            FileDTO dto = aWSS3Util.uploadSingleFile(multipartFile, UploadConstants.FILE);
            String url = dto.getFilekeyurl();
            vo.setDownUrl(url);
            BigDecimal bg = new BigDecimal(multipartFile.getSize());
            BigDecimal size = bg.divide(new BigDecimal(1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP);
            vo.setFileSize(size);

            //系统临时目录
            String tmpdir=System.getProperty("java.io.tmpdir");
            File file = new File(tmpdir,fileName);
            multipartFile.transferTo(file);

            if (fileName.toLowerCase().endsWith(UploadConstants.APK)) {
                //安卓包信息获取
                Map<String, String> map = AppPackageUtil.getApkInfo(file.getAbsolutePath());
                log.info("安卓安装包信息：" + JSONObject.toJSONString(map));
                vo.setUpgradeVersion(map.get("versionCode"));
                vo.setShowVersion(map.get("versionName"));
            } else {
                //ios包信息获取
                Map<String, String> map = AppPackageUtil.getIpaInfo(file.getAbsolutePath());
                log.info("ios安装包信息：" + JSONObject.toJSONString(map));
                vo.setShowVersion(map.get("CFBundleShortVersionString"));
                vo.setUpgradeVersion(map.get("CFBundleVersion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }


    /**
     * 限制文件格式
     *
     * @param fileName
     * @return
     */
    private boolean fileSuffix(String fileName) {
        fileName = fileName.toLowerCase();
        return fileName.endsWith(UploadConstants.APK) || fileName.endsWith(UploadConstants.IPA);
    }

    /**
     * 更新状态
     * @param req
     * @param admin
     */
    public void updateStatus(SysAppVersionUpdateStatusReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if(req == null || req.getId()==null || req.getStatus()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        SysAppVersion version = service.getById(req.getId());
        if (version == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        version.setStatus(req.getStatus());
        version.setUpdateUser(admin.getAccLogin());
        service.updateById(version);
    }
}
