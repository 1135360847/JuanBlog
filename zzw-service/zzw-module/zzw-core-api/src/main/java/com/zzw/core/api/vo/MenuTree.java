package com.zzw.core.api.vo;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * create by zhangzhiwen on 2020/7/17
 */
@Data
public class MenuTree {

    @ApiModelProperty(name = "path" , value = "访问路径")
    private String path;

    @ApiModelProperty(name = "component" , value = "组件路径")
    private String component;

    @ApiModelProperty(name = "redirect" , value = "重定向地址")
    private String redirect;

    @ApiModelProperty(name = "name" , value = "名称")
    private String name;

    @ApiModelProperty(name = "hidden" , value = "是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(name = "meta" , value = "meta")
    private Mate meta;

    @ApiModelProperty(name = "meta" , value = "meta")
    private List<MenuTree> children;
}
