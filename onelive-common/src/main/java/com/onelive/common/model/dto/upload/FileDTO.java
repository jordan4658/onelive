package com.onelive.common.model.dto.upload;

import lombok.Data;

/**
 * @ClassName FileDTO
 * @Desc 文件上传DTO
 * @Date 2021/3/23 10:26
 */
@Data
public class FileDTO {

    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件路径
     */
    private String filekeyurl;
    /**
     * 是否成功 1成功 0失败
     */
    private Integer flag;
}
