package com.zzw.core.api.vo;

import com.zzw.core.api.po.Permission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * create by zhangzhiwen on 2020/7/24
 */
@Data
public class PermissionData {

    @ApiModelProperty(name = "permissionList" , value = "所有数据")
    private List<Permission> permissionList;

    @ApiModelProperty(name = "selectMenuIdList" , value = "展开的菜单集合")
    private List<String> selectMenuIdList;

}
