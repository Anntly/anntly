package com.anntly.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author soledad
 * @Title: UploadService
 * @ProjectName anntly
 * @Description: Service
 * @date 2019/1/2016:06
 */
public interface UploadService {
    String uploadImage(MultipartFile file);
}
