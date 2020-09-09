package com.zzw.core.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by zhangzhiwen on 2020/7/24
 */
@Data
public class PermissionDTO {
    @ApiModelProperty(name = "menuName" , value = "菜单名称")
    private String menuName;
}
