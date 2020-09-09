package com.zzw.core.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by zhangzhiwen on 2020/7/17
 * 前端展示的标题和图标属性
 */
@Data
public class Mate {
    @ApiModelProperty(name = "title", value = "菜单标题")
    private String title;

    @ApiModelProperty(name = "icon", value = "图标")
    private String icon;
}