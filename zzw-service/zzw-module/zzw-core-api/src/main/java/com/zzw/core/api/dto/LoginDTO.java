package com.zzw.core.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by zhangzhiwen on 2020/7/13
 */
@Data
public class LoginDTO {
    @ApiModelProperty(name = "username" , value = "用户名")
    private String username;

    @ApiModelProperty(name = "password" , value = "用户密码")
    private String password;
}
