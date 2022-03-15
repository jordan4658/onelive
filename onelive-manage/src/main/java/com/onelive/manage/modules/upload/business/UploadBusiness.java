package com.onelive.manage.modules.upload.business;

import cn.hutool.core.bean.BeanUtil;
import com.onelive.common.constants.other.UploadConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.upload.FileDTO;
import com.onelive.common.model.vo.upload.FileVO;
import com.onelive.common.utils.upload.AWSS3Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName UploadBusiness
 * @Desc 上传业务类
 * @Date 2021/3/23 14:12
 */
@Component
public class UploadBusiness {

    @Resource
    private AWSS3Util aWSS3Util;

    /**
     * 上传单个图片
     *
     * @param multipartFile
     * @return FileDTO
     */
    public FileVO uploadSingleImg(MultipartFile multipartFile) throws Exception {
        if (null == multipartFile || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2001.getCode(), StatusCode.UPLOAD_ERROR_2001.getMsg());
        }
        String fileName = multipartFile.getOriginalFilename();

        //检查图片格式
        boolean checkRule = this.imageSuffix(fileName);
        if (!checkRule) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2002.getCode(), StatusCode.UPLOAD_ERROR_2002.getMsg());
        }

        long size = multipartFile.getSize();
        if ((size / (1024 * 1024)) > 2) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2003.getCode(), StatusCode.UPLOAD_ERROR_2003.getMsg());
        }
        // 上传到 s3
        FileDTO dto = aWSS3Util.uploadSingleImg(multipartFile, UploadConstants.IMAGE);
        FileVO vo = new FileVO();
        BeanUtil.copyProperties(dto, vo);
        return vo;
    }


    /**
     * 上传多个图片
     *
     * @param multipartFiles
     * @return
     * @throws Exception
     */
    public List<FileVO> uploadMultipleImg(MultipartFile[] multipartFiles) throws Exception {
        if (multipartFiles == null || multipartFiles.length == 0) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2001.getCode(), StatusCode.UPLOAD_ERROR_2001.getMsg());
        }

        if (multipartFiles.length > 9) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2004.getCode(), StatusCode.UPLOAD_ERROR_2004.getMsg());
        }

        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();

            //判断图片格式
            boolean checkRule = this.imageSuffix(fileName);
            if (!checkRule) {
                throw new BusinessException(StatusCode.UPLOAD_ERROR_2002.getCode(), StatusCode.UPLOAD_ERROR_2002.getMsg());
            }
            //判断图片大小
            long size = multipartFile.getSize();
            if ((size / (1024 * 1024)) > 2) {
                throw new BusinessException(StatusCode.UPLOAD_ERROR_2003.getCode(),  StatusCode.UPLOAD_ERROR_2003.getMsg());
            }
        }

        // 上传到 s3
        List<FileDTO> dtoList = aWSS3Util.uploadMultipleImg(multipartFiles, UploadConstants.IMAGE);
        List<FileVO> voList = dtoList.stream().map(a -> {
            FileVO vo = new FileVO();
            BeanUtil.copyProperties(a, vo);
            return vo;
        }).collect(Collectors.toList());

        return voList;
    }


    /**
     * 上传单个视频
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public FileVO uploadSingleVideo(MultipartFile multipartFile) throws Exception {
        if (null == multipartFile || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2001.getCode(), StatusCode.UPLOAD_ERROR_2001.getMsg());
        }
        String fileName = multipartFile.getOriginalFilename();
        //检查视频格式
        boolean checkRule = this.videoSuffix(fileName);
        if (!checkRule) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2002.getCode(), StatusCode.UPLOAD_ERROR_2002.getMsg());
        }
        // 上传到 s3
        FileDTO dto = aWSS3Util.uploadSingleVideo(multipartFile, UploadConstants.VIDEO);
        FileVO vo = new FileVO();
        BeanUtil.copyProperties(dto, vo);
        return vo;
    }

    /**
     * 上传单个文件
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public FileVO uploadSingleFile(MultipartFile multipartFile) throws Exception {
        if (null == multipartFile || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2001.getCode(), StatusCode.UPLOAD_ERROR_2001.getMsg());
        }
        String fileName = multipartFile.getOriginalFilename();
        //检查文件格式
        boolean checkRule = this.fileSuffix(fileName);
        if (!checkRule) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2002.getCode(), StatusCode.UPLOAD_ERROR_2002.getMsg());
        }
        // 上传到 s3
        FileDTO dto = aWSS3Util.uploadSingleFile(multipartFile, UploadConstants.FILE);
        FileVO vo = new FileVO();
        BeanUtil.copyProperties(dto, vo);
        return vo;
    }


    /**
     * 限制图片上传格式
     *
     * @param fileName
     * @return
     */
    private boolean imageSuffix(String fileName) {
        fileName = fileName.toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".bmp") || fileName.endsWith(".gif") || fileName.endsWith(".png") || fileName.endsWith(".svga");
    }

    /**
     * 限制视频上传格式
     *
     * @param fileName
     * @return
     */
    private boolean videoSuffix(String fileName) {
        fileName = fileName.toLowerCase();
        return fileName.endsWith(".mp3") || fileName.endsWith(".mp4") || fileName.endsWith(".flv") || fileName.endsWith(".avi");
    }

    /**
     * 限制文件格式
     *
     * @param fileName
     * @return
     */
    private boolean fileSuffix(String fileName) {
        fileName = fileName.toLowerCase();
        return fileName.endsWith(".zip") || fileName.endsWith(".rar") || fileName.endsWith(".svga")
                || fileName.endsWith(".tar") || fileName.endsWith(".doc");
    }

}
    