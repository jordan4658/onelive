package com.onelive.common.utils.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZIPUtil {
	private static final Logger logger = LoggerFactory.getLogger(ZIPUtil.class);
	/**
	 * 压缩
	 * @param source
	 * @return
	 * @throws IOException
	 */
	public static byte[] zip(byte[] source) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
		zipOutputStream.putNextEntry(new ZipEntry("lottery.txt"));
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
		bufferedOutputStream.write(source);
		bufferedOutputStream.close();
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * file转换 MultipartFile 
	 * @param filePath
	 * @return
	 */
	public static MultipartFile transferFile(String filePath) {
		File f = new File(filePath);
        MultipartFile transferFile = null;
        try {
        	FileItem fileItem = new DiskFileItem(null, Files.probeContentType(f.toPath()), false, f.getName(), (int) f.length(), f.getParentFile());
			InputStream input = new FileInputStream(f);
			OutputStream os = fileItem.getOutputStream();
			IOUtils.copy(input, os);
			transferFile = new CommonsMultipartFile(fileItem);
		} catch (Exception e) {
			logger.error("transferFile occur error.", e);
		}
		return transferFile; 
	}
}
