package com.zzw.core.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by zhangzhiwen on 2020/7/22
 */
@Data
public class RoleDTO {


    @ApiModelProperty(name = "roleName" , value = "角色名称")
    private String roleName;

    @ApiModelProperty(name = "roleCode" , value = "角色编码")
    private String roleCode;

    @ApiModelProperty(name = "isDeleted" , value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    @ApiModelProperty(name = "startDate" , value = "创建起始日期")
    private String startDate;

    @ApiModelProperty(name = "endDate" , value = "创建结束日期")
    private String endDate;
}
