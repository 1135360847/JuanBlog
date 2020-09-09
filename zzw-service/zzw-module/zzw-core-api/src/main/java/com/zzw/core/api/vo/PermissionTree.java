package com.zzw.core.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * create by zhangzhiwen on 2020/7/23
 * 用于设置角色内容时的树形展示
 */
@Data
public class PermissionTree {

    @ApiModelProperty(name = "id" , value = "key")
    private String id;

    @ApiModelProperty(name = "name" , value = "名称")
    private String name;

    @ApiModelProperty(name = "name" , value = "名称")
    private List<PermissionTree> children;

}
