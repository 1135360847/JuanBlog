package com.zzw.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzw.blog.service.ArticleService;
import com.zzw.common.core.rest.baseVO.R;
import com.zzw.core.api.dto.ArticleDTO;
import com.zzw.core.api.po.Article;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 前台博客文章相关API接口层</P>
 * @version: V1.0
 * @author: zzw
 * @time    2020年07月13日
 *
 */
@Api(tags = "博客文章前台展示相关",value="博客文章前台展示相关" )
@RestController
@AllArgsConstructor
@RequestMapping("/front/article")
public class FrontArticleController {
    private ArticleService articleService;


    @ApiOperation(value = "分页查询文章后台管理", notes = "分页查询文章后台管理")
    @GetMapping("/page")
    public R page(Page page, ArticleDTO param) {
        return new R<>(articleService.page(page, param));
    }


    @ApiOperation(value = "查询归档文章", notes = "查询归档文章")
    @ResponseBody
    @GetMapping(value = "/timeLine")
    public R list() throws Exception {
        return R.ok(articleService.timeLine());
    }

    @ApiOperation(value = "查询单条", notes = "查询单条")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType = "path")
    })
    @ResponseBody
    @GetMapping(value = "{id}")
    public R get(@PathVariable("id") Integer id) throws Exception {
        return R.ok(articleService.selectVOById(id));
    }

    @ApiOperation(value = "查询所有的标签", notes = "查询所有的标签")
    @ResponseBody
    @GetMapping("/tagList")
    public R<Set<String>> tagList() throws Exception {
        return R.ok(articleService.selectTagList());
    }


    @ApiOperation(value = "修改阅读数", notes = "修改阅读数")
    @PutMapping("/readCount")
    public R<Article> put(Integer id) throws Exception {
        return R.ok(articleService.updateReadCount(id));
    }


}