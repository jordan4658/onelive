package com.onelive.api.modules.upload.controller;

import com.onelive.api.modules.upload.business.UploadBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.upload.UploadGetFileUrlReq;
import com.onelive.common.model.vo.upload.FileMd5VO;
import com.onelive.common.model.vo.upload.FileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName UploadController
 * @Desc 上传接口类
 * @Date 2021/3/23 14:11
 */
@RestController
@RequestMapping("/upload")
@Api(tags = "上传公共接口")
@Slf4j
public class UploadController {

    @Resource
    private UploadBusiness uploadBusiness;

    @ApiOperation("通过md5获取Url地址")
    @PostMapping(name = "通过md5获取Url地址",  value = {"/pc/v1/getFileUrl", "/app/v1/getFileUrl"})
    public ResultInfo<FileMd5VO> getFileUrl( @RequestBody UploadGetFileUrlReq  req)throws Exception {
        return ResultInfo.ok(uploadBusiness.getFileUrl(req));
    }


    @ApiOperation("上传单个图片，form提交方式")
    @PostMapping(name = "上传单个图片", value = "/app/v1/uploadSingleImg")
    @AllowAccess
    public ResultInfo<FileVO> uploadSingleImg(@ApiParam("md5字符串") @RequestParam(value = "md5Flag",required = false) String md5Flag,@ApiParam("文件") @RequestParam(value = "file",required = false) MultipartFile multipartFile) throws Exception {
        return ResultInfo.ok(uploadBusiness.uploadSingleImg(md5Flag,multipartFile));
    }

    @ApiOperation("上传多个图片，form提交方式")
    @PostMapping(name = "上传多个图片", value = "/app/v1/uploadMultipleImg")
    public ResultInfo<List<FileVO>> uploadSingleImg(@ApiParam("md5字符串,以逗号分割") @RequestParam(value = "md5FlagList",required = false) String md5FlagList,@ApiParam("文件") @RequestParam(value = "file",required = false) MultipartFile[] files) throws Exception {
        return ResultInfo.ok(uploadBusiness.uploadMultipleImg(md5FlagList,files));
    }

    @ApiOperation("上传单个媒体源，form提交方式")
    @PostMapping(name = "上传单个媒体源", value = "/app/v1/uploadSingleVideo")
    public ResultInfo<FileVO> uploadSingleVideo(@ApiParam("md5字符串") @RequestParam(value = "md5Flag",required = false) String md5Flag,@ApiParam("文件") @RequestParam(value = "file",required = false) MultipartFile multipartFile) throws Exception {
        return ResultInfo.ok(uploadBusiness.uploadSingleVideo(md5Flag,multipartFile));
    }

    @ApiOperation("上传单个文件，form提交方式")
    @PostMapping(name = "上传单个文件", value = "/app/v1/uploadSingleFile")
    public ResultInfo<FileVO> uploadSingleFile(@ApiParam("md5字符串") @RequestParam(value = "md5Flag",required = false) String md5Flag,@ApiParam("文件") @RequestParam(value = "file",required = false) MultipartFile multipartFile) throws Exception {
        return ResultInfo.ok(uploadBusiness.uploadSingleFile(md5Flag,multipartFile));
    }


}    
    