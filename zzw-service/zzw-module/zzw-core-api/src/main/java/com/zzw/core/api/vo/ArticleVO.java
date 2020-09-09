package com.zzw.core.api.vo;

import com.zzw.core.api.po.Article;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * create by zhangzhiwen on 2020/8/3
 */
@Data
public class ArticleVO extends Article {

    @ApiModelProperty(name = "articleTagList" , value = "文章标签集合")
    private List<String> articleTagList;

    @ApiModelProperty(name = "managerName" , value = "作者")
    private String managerName;

    @ApiModelProperty(name = "articleCategoryName" , value = "分类名称")
    private String articleCategoryName;

}
