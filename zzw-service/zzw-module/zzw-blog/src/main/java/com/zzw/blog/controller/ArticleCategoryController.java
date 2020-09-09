package com.zzw.blog.controller;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzw.blog.service.ArticleCategoryService;
import com.zzw.blog.service.ArticleService;
import com.zzw.common.core.rest.baseVO.R;
import com.zzw.core.api.dto.ArticleDTO;
import com.zzw.core.api.po.Article;
import com.zzw.core.api.po.ArticleCategory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import io.swagger.annotations.Api;

import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 博分类相关API接口层</P>
 * @version: V1.0
 * @author: zzw
 * @time    2020年07月13日
 *
 */
@Api(tags = "博分类相关",value="博分类相关" )
@RestController
@AllArgsConstructor
@RequestMapping("/admin/articleCategory")
public class ArticleCategoryController{

    private ArticleCategoryService articleCategoryService;
    private ArticleService articleService;

    @ApiOperation(value = "分页查询标签和分类", notes = "分页查询文章后台管理")
    @GetMapping("/page")
    public R page(Page page, ArticleCategory param) {
        LambdaQueryWrapper<ArticleCategory> qw = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(param.getName())){
            qw.like(ArticleCategory::getName,param.getName());
        }
        if(!ObjectUtils.isEmpty(param.getIsEnable())){
            qw.eq(ArticleCategory::getIsEnable,param.getIsEnable());
        }
        return new R<>(articleCategoryService.page(page, qw));
    }

    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping
    public R list(ArticleCategory param) throws Exception {
        return R.ok(articleCategoryService.list(Wrappers.query(param)));
    }

    @ApiOperation(value = "查询单条", notes = "查询单条")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType = "path")
    })
    @ResponseBody
    @GetMapping(value = "{id}")
    public R get(@PathVariable("id") Integer id) throws Exception {
        return R.ok(articleCategoryService.getById(id));
    }

    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping
    public R post(@RequestBody ArticleCategory e) throws Exception {
        return R.ok(articleCategoryService.save(e));
    }

    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping
    public R put(@RequestBody ArticleCategory e) throws Exception {
        return R.ok(articleCategoryService.updateById(e));
    }


    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType = "path")
    })
    @DeleteMapping(value = "{id}")
    public R<Boolean> delete(@PathVariable("id") Integer id) throws Exception {
        Integer count = articleService.count(Wrappers.<Article>lambdaQuery().eq(Article::getArticleCategoryId,id));

        if(count>0){
            return R.fail("该分类下面有关联文章无法删除");
        }
        return R.ok(articleCategoryService.removeById(id));
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idList", value = "表IDList", required = true, paramType = "list")
    })
    @DeleteMapping(value = "/batch")
    public R<Boolean> batchDelete(@RequestBody List<Integer> idList) throws Exception {
        Integer count = articleService.count(Wrappers.<Article>lambdaQuery().in(Article::getArticleCategoryId,idList));
        if(count>0){
            return R.fail("分类下面有关联文章无法删除");
        }
        return R.ok(articleCategoryService.removeByIds(idList));
    }
}