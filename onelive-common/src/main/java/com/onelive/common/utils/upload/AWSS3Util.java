package com.onelive.common.utils.upload;


import cn.hutool.core.util.IdUtil;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.upload.FileDTO;
import com.onelive.common.utils.Login.IPdatabaseConfig;
import com.onelive.common.utils.others.SpringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AWSS3Util
 * @Desc 上传工具类
 * @Date 2021/3/23 11:53
 */
@Slf4j
@Configuration
public class AWSS3Util {

    private static AWSS3Config config;

    private static String http = "http";
    private static String https = "https";

    public static AWSS3Config getConfig() {
        if (config == null) {
            config = SpringUtil.getBean(AWSS3Config.class);
        }
        return config;
    }


    /**
     * 上传单张图片
     *
     * @param multipartFile
     * @param preixfKey
     * @return
     * @throws Exception
     */
    public FileDTO uploadSingleImg(MultipartFile multipartFile, String preixfKey) throws Exception {

        Boolean isSuccess = true;
        String fileName = multipartFile.getOriginalFilename();
        //获取文件后缀
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        String key = preixfKey + getKeyCode() + fileSuffix.toLowerCase();
        AmazonS3 s3 = getS3();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());

            PutObjectRequest putObjectRequest = new PutObjectRequest(getConfig().getAwsBucketNamePhoto(), key,
                    multipartFile.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead);
            // 上传
            s3.putObject(putObjectRequest);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            isSuccess = false;
            log.error("AmazonClientException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            isSuccess = false;
            log.error("SdkClientException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } finally {
            s3.shutdown();
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilename(multipartFile.getOriginalFilename());
        if (isSuccess) {
            String url = getUploadUrl(key);
            fileDTO.setFlag(1);
            fileDTO.setFilekeyurl(url);
        } else {
            fileDTO.setFlag(0);
        }
        return fileDTO;
    }

    /**
     * 上传图片
     * @param is
     * @param fileName
     * @param contentType
     * @param contentLength
     * @param preixfKey
     * @return
     */
    public FileDTO uploadSingleImg(InputStream is, String fileName, String contentType ,Long contentLength , String preixfKey){
        Boolean isSuccess = true;
        //获取文件后缀
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        String key = preixfKey + getKeyCode() + fileSuffix.toLowerCase();
        AmazonS3 s3 = getS3();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType); // "image/png"
            metadata.setContentLength(contentLength);

            PutObjectRequest putObjectRequest = new PutObjectRequest(getConfig().getAwsBucketNamePhoto(), key,
                    is, metadata).withCannedAcl(CannedAccessControlList.PublicRead);
            // 上传
            s3.putObject(putObjectRequest);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            isSuccess = false;
            log.error("AmazonClientException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            isSuccess = false;
            log.error("SdkClientException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } finally {
            s3.shutdown();
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilename(fileName);
        if (isSuccess) {
            String url = getUploadUrl(key);
            fileDTO.setFlag(1);
            fileDTO.setFilekeyurl(url);
        } else {
            fileDTO.setFlag(0);
        }
        return fileDTO;
    }

    /**
     * 上传多张图片
     *
     * @param files
     * @param preixfKey
     * @return
     * @throws Exception
     */
    public List<FileDTO> uploadMultipleImg(MultipartFile[] files, String preixfKey) throws Exception {
        List<FileDTO> list = new ArrayList<>();
        AmazonS3 s3 = getS3();
        for (MultipartFile multipartFile : files) {
            Boolean isSuccess = true;
            // 记录上传过程起始时的时间，用来计算上传时间
            long pre = System.currentTimeMillis();

            // 后缀 .flv
            String fileName = multipartFile.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            String key = preixfKey + getKeyCode() + fileSuffix.toLowerCase();
            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(multipartFile.getContentType());
                metadata.setContentLength(multipartFile.getSize());

                PutObjectRequest putObjectRequest = new PutObjectRequest(getConfig().getAwsBucketNamePhoto(), key,
                        multipartFile.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead);
                // 上传
                s3.putObject(putObjectRequest);
            } catch (AmazonServiceException e) {
                // The call was transmitted successfully, but Amazon S3 couldn't process
                // it, so it returned an error response.
                isSuccess = false;
                log.error("AmazonClientException", e);
                s3.shutdown();
                throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
            } catch (SdkClientException e) {
                // Amazon S3 couldn't be contacted for a response, or the client
                // couldn't parse the response from Amazon S3.
                isSuccess = false;
                log.error("SdkClientException", e);
                s3.shutdown();
                throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
            } finally {

            }

            FileDTO fileDTO = new FileDTO();
            fileDTO.setFilename(multipartFile.getOriginalFilename());
            if (isSuccess) {
                String url = getUploadUrl(key);
                fileDTO.setFlag(1);
                fileDTO.setFilekeyurl(url);
            } else {
                fileDTO.setFlag(0);
            }
            list.add(fileDTO);
            long finaltime = System.currentTimeMillis();
            log.info("上传费时：{}", (finaltime - pre));
        }
        s3.shutdown();
        return list;
    }


    /**
     * 上传单个视频
     *
     * @param multipartFile
     * @param preixfKey
     * @return
     * @throws Exception
     */
    public FileDTO uploadSingleVideo(MultipartFile multipartFile, String preixfKey) throws Exception {
        Boolean isSuccess = true;
        String fileName = multipartFile.getOriginalFilename();
        // 后缀 .flv
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        String key = preixfKey + getKeyCode() + fileSuffix.toLowerCase();
        // aws s3 上传
        // Transfer
        AmazonS3 s3 = getS3();
        TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3).build();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        try {

            PutObjectRequest putObjectRequest = new PutObjectRequest(getConfig().getAwsBucketNameVideo(), key,
                    multipartFile.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead);
			/*
			// 监听在文件传输完毕后后有异常
			putObjectRequest.setGeneralProgressListener(new ProgressListener() {

				@Override
				public void progressChanged(ProgressEvent progressEvent) {
					logger.info("{}", "Transferred bytes: " + progressEvent.getBytesTransferred());
				}
			});*/

            Upload upload = tm.upload(putObjectRequest);
            // loop with Transfer.isDone()
            // XferMgrProgress.showTransferProgress(upload);
            upload.waitForCompletion();
        } catch (AmazonServiceException e) {
            isSuccess = false;
            log.error("AmazonServiceException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            isSuccess = false;
            log.error("SdkClientException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } finally {
            tm.shutdownNow();
            s3.shutdown();
        }
        log.info("{}", "*******Completion**********");
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilename(multipartFile.getOriginalFilename());
        if (isSuccess) {
            // 获取视频播放路径
			/*GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
					awsBucketNameVideo, key).withMethod(HttpMethod.GET);
			URL url = s3.generatePresignedUrl(generatePresignedUrlRequest);*/
            String url = getUploadUrl(key);
            fileDTO.setFlag(1);
            fileDTO.setFilekeyurl(url);
        } else {
            fileDTO.setFlag(0);
        }
        return fileDTO;
    }


    /**
     * 上传单个文件
     *
     * @param multipartFile
     * @param preixfKey
     * @return
     * @throws Exception
     */
    public FileDTO uploadSingleFile(MultipartFile multipartFile, String preixfKey) throws Exception {
        Boolean isSuccess = true;
        String fileName = multipartFile.getOriginalFilename();
        // 后缀 .flv
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        String key = preixfKey + getKeyCode() + fileSuffix.toLowerCase();
        // aws s3 上传
        // Transfer
        AmazonS3 s3 = getS3();
        TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3).build();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(getConfig().getAwsBucketNameFile(), key,
                    multipartFile.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead);
            Upload upload = tm.upload(putObjectRequest);
            upload.waitForCompletion();
        } catch (AmazonServiceException e) {
            isSuccess = false;
            log.error("AmazonServiceException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            isSuccess = false;
            log.error("SdkClientException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } finally {
            tm.shutdownNow();
            s3.shutdown();
        }
        log.info("{}", "*******Completion**********");
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilename(multipartFile.getOriginalFilename());
        if (isSuccess) {
            String url = getUploadUrl(key);
            fileDTO.setFlag(1);
            fileDTO.setFilekeyurl(url);
        } else {
            fileDTO.setFlag(0);
        }
        return fileDTO;
    }


    /**
     * 上传文本
     * @param content
     * @param preixfKey
     * @param fileSuffix
     * @return
     * @throws Exception
     */
    public String uploadString(String content, String preixfKey, String fileSuffix) throws Exception {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(content)) {
            String contentType = "text/plain";
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8.name());
            return this.uploadBytes(bytes, preixfKey, fileSuffix, contentType);
        } else {
            log.info("{}上传文件内容为空", getClass().getName());
            return null;
        }

    }

    /**
     * 上传zip包
     * @param content
     * @param preixfKey
     * @param fileSuffix
     * @return
     * @throws Exception
     */
    public String uploadZip(String content, String preixfKey, String fileSuffix) throws Exception {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(content)) {
            byte[] bytes = ZIPUtil.zip(content.getBytes(StandardCharsets.UTF_8.name()));
            String contentType = "text/plain";
            return this.uploadBytes(bytes, preixfKey, fileSuffix, contentType);
        } else {
            log.info("{}上传压缩文件内容为空", getClass().getName());
            return null;
        }
    }


    ///////////////////////////私有方法//////////////////////////////////////

    /**
     * 凭证
     *
     * @return
     */
    private AmazonS3 getS3() {
        //东京区域
        Region region = Region.getRegion(Regions.fromName(getConfig().getAwsRegion()));
        AWSCredentials awsCredentials = new BasicAWSCredentials(getConfig().getAwsAccessKeyId(), getConfig().getAwsSecretSccessKey());
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        builder.setRegion(region.getName());
        AmazonS3 s3 = builder.build();
        return s3;
    }

    /**
     * s3 生成对象 唯一的 key
     *
     * @return
     */
    private String getKeyCode() {
        return IdUtil.simpleUUID();
    }

    /**
     * s3 上传路径
     *
     * @return
     */
    private String getUploadUrl(String keyName) {
        if (StringUtils.isNotEmpty(keyName)) {
            return getConfig().getAwsS3PrefixUrl() + "/" + keyName;
        }
        return null;
    }


    /**
     * 上传文件类型公共方法
     *
     * @param bytes
     * @param preixfKey
     * @param fileSuffix
     * @param contentType
     * @return
     * @throws Exception
     */
    private String uploadBytes(byte[] bytes, String preixfKey, String fileSuffix, String contentType) throws Exception {
        String key = preixfKey + fileSuffix;
        log.info(key);
        AmazonS3 s3 = getS3();

        String uploadUrl;
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(bytes.length);
            InputStream targetStream = new ByteArrayInputStream(bytes);

            PutObjectRequest putObjectRequest = new PutObjectRequest(getConfig().getAwsBucketNameFile(), key,
                    targetStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead);
            // 上传
            s3.putObject(putObjectRequest);

            uploadUrl = this.getUploadUrl(key);
            log.info("{}文件上传成功,文件目录{}", getClass().getName(), uploadUrl);
        } catch (AmazonServiceException e) {
            log.info("{}", "AmazonClientException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } catch (SdkClientException e) {
            log.info("{}", "SdkClientException", e);
            throw new BusinessException(StatusCode.UPLOAD_ERROR_2000.getCode(), StatusCode.UPLOAD_ERROR_2000.getMsg());
        } finally {
            s3.shutdown();
        }
        return uploadUrl;
    }

    /**
     * 获取文件的相对路径
     * @param url
     * @return
     */
    public static  String getRelativeUrl(String url){
        if(StringUtils.isNotBlank(url)){
            url = url.replace(getConfig().getAwsS3PrefixUrl(),"");
        }
        return url;
    }

    /**
     * 获取文件的绝对路径
     * @param url
     * @return
     */
    public static String getAbsoluteUrl(String url){
        if(StringUtils.isNotBlank(url)){
            if(url.contains(http) || url.contains(https)){
                return url;
            }else {
                return getConfig().getAwsS3PrefixUrl()+url;
            }
        }
        return null;
    }


}
    