package com.onelive.common.utils.upload;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "upload")
public class AWSS3Config {

    /**
     * #aws访问前缀
     */
    private String awsS3PrefixUrl;

    /**
     * aws 区域
     */
    private String awsRegion;

    /**
     * 访问KeyId
     */
    private String awsAccessKeyId;

    /**
     * 访问密钥
     */
    private String awsSecretSccessKey;

    /**
     * 视频存储桶
     */
    private String awsBucketNameVideo;

    /**
     * 图片存储桶
     */
    private String awsBucketNamePhoto;

    /**
     * 文件存储桶
     */
    private String awsBucketNameFile;
}
