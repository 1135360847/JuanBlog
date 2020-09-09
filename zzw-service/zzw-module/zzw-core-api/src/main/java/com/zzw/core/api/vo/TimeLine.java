package com.zzw.core.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * create by zhangzhiwen on 2020/8/19
 */
@Data
public class TimeLine {
    @ApiModelProperty(name = "year" , value = "年份")
    private Integer year;
    @ApiModelProperty(name = "articleVOList" , value = "文章列表")
    private List<ArticleVO> articleVOList;
}
