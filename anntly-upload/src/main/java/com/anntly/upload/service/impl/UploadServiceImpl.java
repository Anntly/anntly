package com.anntly.upload.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.upload.config.UploadProperties;
import com.anntly.upload.service.UploadService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * @author soledad
 * @Title: UploadServiceImpl
 * @ProjectName anntly
 * @Description: UploadServicer实现类
 * @date 2019/1/2016:07
 */
@Slf4j
@Service
@EnableConfigurationProperties(UploadProperties.class)
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties props;

    /**
     * 上传图片，返回图片url
     * @param file
     * @return
     */
    @Override
    public String uploadImage(MultipartFile file) {
        try {
            // 校验文件类型
            //      获取文件类型
            String contentType = file.getContentType();
            if (!props.getAllowTypes().contains(contentType)) {
                throw new AnnException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            //      通过file的文件类型校验，如果不是图片读出为空
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(null == image){
                throw new AnnException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            // 上传文件
            //      获取文件后缀名
            String extension = StringUtils.
                    substringAfterLast(file.getOriginalFilename(),".");
            StorePath storePath = storageClient
                    .uploadFile(file.getInputStream(), file.getSize(), extension, null);
            log.info("文件上传成功");
            return props.getBaseUrl() + storePath.getFullPath();
        }catch (Exception e){
            //上传失败 保存日志信息
            log.error("[文件上传] 上传文件失败",e);
            throw new AnnException(ExceptionEnum.FILE_UPLOAD_ERROR);
        }
    }
}
