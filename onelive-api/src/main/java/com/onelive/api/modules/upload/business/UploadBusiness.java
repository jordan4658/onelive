package com.onelive.api.modules.upload.business;

import cn.hutool.core.bean.BeanUtil;
import com.onelive.api.service.sys.SysFileRecordService;
import com.onelive.common.constants.other.UploadConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.upload.FileDTO;
import com.onelive.common.model.req.upload.UploadGetFileUrlReq;
import com.onelive.common.model.vo.upload.FileMd5VO;
import com.onelive.common.model.vo.upload.FileVO;
import com.onelive.common.mybatis.entity.SysFileRecord;
import com.onelive.common.utils.upload.AWSS3Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private SysFileRecordService sysFileRecordService;

    @Resource
    private AWSS3Util aWSS3Util;

    /**
     * 通过md5获取图片url路径
     * @param req
     * @return
     * @throws Exception
     */
    public FileMd5VO getFileUrl(UploadGetFileUrlReq req) throws Exception {
        if(req == null || StringUtils.isBlank(req.getMd5Flag())){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        FileMd5VO vo = new FileMd5VO();
        SysFileRecord sysFileRecord =sysFileRecordService.getFileUrlByMd5Flag(req.getMd5Flag());
        if(sysFileRecord != null){
            vo.setUrl(AWSS3Util.getAbsoluteUrl(sysFileRecord.getFlieUrl()));
        }
        return vo;
    }

    /**
     * 上传单个图片
     *
     * @param multipartFile
     * @return FileDTO
     */
    public FileVO uploadSingleImg(String md5Flag,MultipartFile multipartFile) throws Exception {
        if (null == multipartFile || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2001);
        }
        String fileName = multipartFile.getOriginalFilename();
        //检查图片格式
        boolean checkRule = this.imageSuffix(fileName);
        if (!checkRule) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2002);
        }

        long size = multipartFile.getSize();
        if ((size / (1024 * 1024)) > 2) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2003);
        }
        // 上传到 s3
        FileDTO dto = aWSS3Util.uploadSingleImg(multipartFile, UploadConstants.IMAGE);
        FileVO vo = new FileVO();
        BeanUtil.copyProperties(dto, vo);

        SysFileRecord record = new SysFileRecord();
        record.setFlieUrl(dto.getFilekeyurl());
        record.setMd5Flag(md5Flag);
        sysFileRecordService.saveSysFileRecord(record);
        return vo;
    }


    /**
     * 上传多个图片
     *
     * @param multipartFiles
     * @return
     * @throws Exception
     */
    public List<FileVO> uploadMultipleImg(String md5Flag,MultipartFile[] multipartFiles) throws Exception {
        if (multipartFiles == null || multipartFiles.length == 0) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2001);
        }

        if (multipartFiles.length > 9) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2004);
        }

        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();

            //判断图片格式
            boolean checkRule = this.imageSuffix(fileName);
            if (!checkRule) {
                throw new BusinessException(StatusCode.UPLOAD_ERROR_2002);
            }
            //判断图片大小
            long size = multipartFile.getSize();
            if ((size / (1024 * 1024)) > 2) {
                throw new BusinessException(StatusCode.UPLOAD_ERROR_2003);
            }
        }
        String[] md5FlagList = md5Flag.split(",");

        // 上传到 s3
        List<FileDTO> dtoList = aWSS3Util.uploadMultipleImg(multipartFiles, UploadConstants.IMAGE);
        List<FileVO> voList = dtoList.stream().map(a -> {
            FileVO vo = new FileVO();
            BeanUtil.copyProperties(a, vo);
            return vo;
        }).collect(Collectors.toList());

        //批量保存文件记录
        List<SysFileRecord> records = new ArrayList<>();
        for(int i = 0; i<voList.size(); i ++){
            FileVO vo = voList.get(i);
            SysFileRecord record = new SysFileRecord();
            record.setMd5Flag(md5FlagList[i]);
            record.setFlieUrl(vo.getFilekeyurl());
            records.add(record);
        }
        sysFileRecordService.saveBatchSysFileRecord(records);

        return voList;
    }


    /**
     * 上传单个视频
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public FileVO uploadSingleVideo(String md5Flag,MultipartFile multipartFile) throws Exception {
        if (null == multipartFile || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2001);
        }
        String fileName = multipartFile.getOriginalFilename();
        //检查视频格式
        boolean checkRule = this.videoSuffix(fileName);
        if (!checkRule) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2002);
        }
        // 上传到 s3
        FileDTO dto = aWSS3Util.uploadSingleVideo(multipartFile, UploadConstants.VIDEO);
        FileVO vo = new FileVO();
        BeanUtil.copyProperties(dto, vo);

        SysFileRecord record = new SysFileRecord();
        record.setFlieUrl(dto.getFilekeyurl());
        record.setMd5Flag(md5Flag);
        sysFileRecordService.saveSysFileRecord(record);
        return vo;
    }

    /**
     * 上传单个文件
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public FileVO uploadSingleFile(String md5Flag,MultipartFile multipartFile) throws Exception {
        if (null == multipartFile || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2001);
        }
        String fileName = multipartFile.getOriginalFilename();
        //检查文件格式
        boolean checkRule = this.fileSuffix(fileName);
        if (!checkRule) {
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2002);
        }
        // 上传到 s3
        FileDTO dto = aWSS3Util.uploadSingleFile(multipartFile, UploadConstants.FILE);
        FileVO vo = new FileVO();
        BeanUtil.copyProperties(dto, vo);

        SysFileRecord record = new SysFileRecord();
        record.setFlieUrl(dto.getFilekeyurl());
        record.setMd5Flag(md5Flag);
        sysFileRecordService.saveSysFileRecord(record);
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
    