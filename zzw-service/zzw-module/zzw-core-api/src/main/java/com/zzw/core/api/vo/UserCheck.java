package com.zzw.core.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by zhangzhiwen on 2020/7/21
 */
@Data
public class UserCheck {
    @ApiModelProperty(name = "id" , value = "id")
    private String id;

    @ApiModelProperty(name = "username" , value = "用户名")
    private String username;


}
