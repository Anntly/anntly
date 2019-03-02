package com.anntly.user.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author soledad
 * @Title: ValidateCode
 * @ProjectName anntly
 * @Description: 验证码实体类（图片验证码，短信验证码）
 * @date 2019/2/2711:22
 */
@Getter
@Setter
public class ValidateCode {

    private String code;

    private LocalDateTime expireTime;

    /**
     * 传入过期秒数的构造器
     * @param code
     * @param expireIn
     */
    public ValidateCode(String code, int expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    /**
     * 传入过期具体时间
     * @param code
     * @param expireTime
     */
    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    // 判断验证码是否过期
    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
