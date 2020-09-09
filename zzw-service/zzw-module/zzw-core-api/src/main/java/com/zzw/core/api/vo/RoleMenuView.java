package com.zzw.core.api.vo;

import com.zzw.core.api.po.Permission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * create by zhangzhiwen on 2020/7/23
 */
@Data
public class RoleMenuView {

    @ApiModelProperty(name = "allPermission" , value = "所有的菜单")
    private List<PermissionTree> allPermission;

    @ApiModelProperty(name = "checkPermissionIdList" , value = "选中的菜单id")
    private Set<String> checkPermissionIdList;
}
