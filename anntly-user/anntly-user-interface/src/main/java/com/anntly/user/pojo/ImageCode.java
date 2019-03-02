package com.anntly.user.pojo;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author soledad
 * @Title: ImageCode
 * @ProjectName anntly
 * @Description: 图片验证码
 * @date 2019/2/2711:24
 */
@Getter
@Setter
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    /**
     * 传入过期秒数的构造器
     * @param image
     * @param code
     * @param expireIn
     */
    public ImageCode(BufferedImage image,String code,int expireIn){
        super(code,expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }

}
