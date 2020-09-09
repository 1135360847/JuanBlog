package com.zzw.blog.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzw.blog.service.ArticleCategoryService;
import com.zzw.blog.service.ArticleService;
import com.zzw.common.core.rest.baseVO.R;
import com.zzw.core.api.dto.ArticleDTO;
import com.zzw.core.api.po.Article;
import com.zzw.core.api.po.ArticleCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 前台博客文章分类相关API接口层</P>
 * @version: V1.0
 * @author: zzw
 * @time    2020年07月13日
 *
 */
@Api(tags = "博客文章前台展示相关",value="博客文章前台展示相关" )
@RestController
@AllArgsConstructor
@RequestMapping("/front/articleCategory")
public class FrontArticleCategoryController {
    private ArticleCategoryService articleCategoryService;


    @ApiOperation(value = "查询所有分类", notes = "查询所有")
    @ResponseBody
    @GetMapping(value = "")
    public R list(ArticleCategory param) throws Exception {
        return R.ok(articleCategoryService.list(Wrappers.query(param)));
    }


}