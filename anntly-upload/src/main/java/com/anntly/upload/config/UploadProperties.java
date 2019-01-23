package com.anntly.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author soledad
 * @Title: UploadProperties
 * @ProjectName anntly
 * @Description: 配置
 * @date 2019/1/2016:02
 */
@Data
@ConfigurationProperties(prefix = "an.upload")
public class UploadProperties {

    private String baseUrl;

    private List<String> allowTypes;
}
