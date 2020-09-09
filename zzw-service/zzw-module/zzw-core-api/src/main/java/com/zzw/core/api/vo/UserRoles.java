package com.zzw.core.api.vo;

import com.zzw.core.api.po.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * create by zhangzhiwen on 2020/7/17
 */
@Data
public class UserRoles {

    @ApiModelProperty(name = "allRoleList" , value = "所有的角色")
    List<Role> assignRoles;

    @ApiModelProperty(name = "allRoleList" , value = "所有的角色")
    List<Role> allRoleList;

}
