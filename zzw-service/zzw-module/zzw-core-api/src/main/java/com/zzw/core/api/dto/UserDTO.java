package com.zzw.core.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create by zhangzhiwen on 2020/7/17
 */
@Data
public class UserDTO {
    @ApiModelProperty(name = "username" , value = "账号")
    private String username;

    @ApiModelProperty(name = "nickName" , value = "昵称")
    private String nickName;

    @ApiModelProperty(name = "isDeleted" , value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    @ApiModelProperty(name = "startDate" , value = "创建起始日期")
    private String startDate;

    @ApiModelProperty(name = "endDate" , value = "创建结束日期")
    private String endDate;

}
