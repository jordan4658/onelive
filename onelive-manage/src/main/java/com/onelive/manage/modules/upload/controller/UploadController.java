package com.onelive.manage.modules.upload.controller;


import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.upload.FileVO;
import com.onelive.manage.modules.upload.business.UploadBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @ApiOperation("上传单个图片")
    @PostMapping(name = "上传单个图片", value = "/v1/uploadSingleImg")
    public ResultInfo<FileVO> uploadSingleImg(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return ResultInfo.ok(uploadBusiness.uploadSingleImg(multipartFile));
    }

    @ApiOperation("上传多个图片")
    @PostMapping(name = "上传多个图片", value = "/v1/uploadMultipleImg")
    public ResultInfo<List<FileVO>> uploadSingleImg(@RequestParam("file") MultipartFile[] files) throws Exception {
        return ResultInfo.ok(uploadBusiness.uploadMultipleImg(files));
    }

    @ApiOperation("上传单个媒体源")
    @PostMapping(name = "上传单个媒体源", value = "/v1/uploadSingleVideo")
    public ResultInfo<FileVO> uploadSingleVideo(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return ResultInfo.ok(uploadBusiness.uploadSingleVideo(multipartFile));
    }

    @ApiOperation("上传单个文件")
    @PostMapping(name = "上传单个文件", value = "/v1/uploadSingleFile")
    public ResultInfo<FileVO> uploadSingleFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return ResultInfo.ok(uploadBusiness.uploadSingleFile(multipartFile));
    }


}    
    