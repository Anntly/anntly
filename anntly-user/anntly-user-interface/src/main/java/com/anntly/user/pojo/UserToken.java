package com.anntly.user.pojo;

import com.anntly.user.dto.UserLoginDto;
import lombok.Data;

/**
 * @author soledad
 * @Title: UserToken
 * @ProjectName anntly
 * @Description: TODO
 * @date 2019/2/2216:28
 */
@Data
public class UserToken {

    private AuthToken authToken;

    private UserLoginDto userLoginDto;
}
