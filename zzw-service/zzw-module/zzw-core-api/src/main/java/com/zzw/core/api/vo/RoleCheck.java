package com.zzw.core.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by zhangzhiwen on 2020/7/21
 */
@Data
public class RoleCheck {
    @ApiModelProperty(name = "id" , value = "id")
    private String id;

    @ApiModelProperty(name = "roleName" , value = "角色名")
    private String roleName;


}
