package com.zzw.blog.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzw.blog.service.ArticleService;
import com.zzw.common.core.rest.baseVO.R;
import com.zzw.common.security.entity.SecurityUser;
import com.zzw.common.security.entity.User;
import com.zzw.core.api.dto.ArticleDTO;
import com.zzw.core.api.po.Article;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import io.swagger.annotations.Api;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 博客文章相关API接口层</P>
 * @version: V1.0
 * @author: zzw
 * @time    2020年07月13日
 *
 */
@Api(tags = "博客文章管理相关",value="博客文章管理相关" )
@RestController
@AllArgsConstructor
@RequestMapping("/admin/article")
public class AdminArticleController{
    private ArticleService articleService;


    @ApiOperation(value = "分页查询文章后台管理", notes = "分页查询文章后台管理")
    @GetMapping("/page")
    public R page(Page page, ArticleDTO param) {
        return new R<>(articleService.page(page, param));
    }

//    @ApiOperation(value = "查询所有", notes = "查询所有")
//    @ResponseBody
//    @GetMapping(value = "")
//    public R list(ArticleDTO param) throws Exception {
//        return R.ok(articleService.list(param));
//    }

    @ApiOperation(value = "查询单条", notes = "查询单条")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType = "path")
    })
    @ResponseBody
    @GetMapping(value = "{id}")
    public R get(@PathVariable("id") Integer id) throws Exception {

        return R.ok(articleService.selectVOById(id));
    }

    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping
    public R post(@RequestBody Article e) throws Exception {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        e.setManagerId(user.getId());
        return R.ok(articleService.save(e));
    }

    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping
    public R put(@RequestBody Article e) throws Exception {
        return R.ok(articleService.updateById(e));
    }


    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType = "path")
    })
    @DeleteMapping(value = "{id}")
    public R<Boolean> delete(@PathVariable("id") Integer id) throws Exception {
        return R.ok(articleService.removeById(id));
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idList", value = "表IDList", required = true, paramType = "list")
    })
    @DeleteMapping(value = "/batch")
    public R<Boolean> batchDelete(@RequestBody List<Integer> idList) throws Exception {
        return R.ok(articleService.removeByIds(idList));
    }

}