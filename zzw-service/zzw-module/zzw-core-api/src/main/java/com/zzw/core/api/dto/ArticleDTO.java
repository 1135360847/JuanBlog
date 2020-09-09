package com.zzw.core.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by zhangzhiwen on 2020/8/3
 */
@Data
public class ArticleDTO {

    @ApiModelProperty(name = "articleState" , value = "文章状态")
    private Integer articleState;

    @ApiModelProperty(name = "articleTitle" , value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(name = "articleTag" , value = "文章标签")
    private String articleTag;

    @ApiModelProperty(name = "articleType" , value = "文章类型")
    private Integer articleType;

    @ApiModelProperty(name = "articleTypeName" , value = "文章类型名称")
    private String articleTypeName;

    @ApiModelProperty(name = "managerId" , value = "作者id")
    private String managerName;

    @ApiModelProperty(name = "keywords" , value = "搜索")
    private String keywords;

    @ApiModelProperty(name = "tagKeywords" , value = "搜索")
    private String tagKeywords;
}
