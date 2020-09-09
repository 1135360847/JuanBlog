package com.zzw.core.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * create by zhangzhiwen on 2020/7/17
 */
@Data
public class UserInfo {

    @ApiModelProperty(name = "username" , value = "用户名")
    private String username;

    @ApiModelProperty(name = "avatar" , value = "头像")
    private String avatar;

    @ApiModelProperty(name = "roles" , value = "角色名称集合")
    private List<String> roles;

    @ApiModelProperty(name = "permissionValueList" , value = "内容名称集合")
    private List<String> permissionValueList;
}
