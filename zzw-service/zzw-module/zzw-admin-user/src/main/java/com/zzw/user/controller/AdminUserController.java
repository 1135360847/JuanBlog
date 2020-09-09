package com.zzw.user.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzw.common.core.rest.baseVO.R;
import com.zzw.core.api.dto.UserDTO;
import com.zzw.core.api.po.AdminUser;
import com.zzw.core.api.vo.UserCheck;
import com.zzw.core.api.vo.UserRoles;
import com.zzw.user.service.AdminUserService;
import com.zzw.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 用户相关API接口层</P>
 *
 * @version: V1.0
 * @author: zzw
 * @time 2020年07月13日
 */
@Api(tags = "用户相关", value = "用户相关")
@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/admin/user")
public class AdminUserController {
    private AdminUserService adminUserService;
    private RoleService roleService;

    @ApiOperation(value = "分页查询后台管理用户", notes = "分页查询后台管理用户")
    @GetMapping("/page")
    public R page(Page page, UserDTO param) {
        return new R<>(adminUserService.page(page, param));
    }

    @ApiOperation(value = "查询所有", notes = "查询所有")
    @ResponseBody
    @GetMapping(value = "")
    public R list(UserDTO param)  {
        return R.ok(adminUserService.list(param));
    }

    @ApiOperation(value = "查询单条", notes = "查询单条")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType = "path")
    })
    @ResponseBody
    @GetMapping(value = "{id}")
    public R get(@PathVariable("id") String id)  {
        return R.ok(adminUserService.getById(id));
    }

    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping
    public R post(@RequestBody AdminUser e)  {
        String username = e.getUsername();
        Integer exit = adminUserService.count(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getUsername,username));
        if(exit>0){
            return R.fail("用户名已存在");
        }
        return R.ok(adminUserService.save(e));
    }

    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping
    public R put(@RequestBody AdminUser e)  {
        return R.ok(adminUserService.updateById(e));
    }


    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType = "path")
    })
    @DeleteMapping(value = "{id}")
    public R<Boolean> delete(@PathVariable("id") String id)  {
        return R.ok(adminUserService.removeById(id));
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idList", value = "表IDList", required = true, paramType = "list")
    })
    @DeleteMapping(value = "/batch")
    public R<Boolean> batchDelete(@RequestBody List<String> idList)  {
        return R.ok(adminUserService.removeByIds(idList));
    }


    @ApiOperation(value = "用户校验方法", notes = "用户校验方法")
    @PostMapping("check")
    public R post(@RequestBody UserCheck userCheck)  {
        LambdaQueryWrapper<AdminUser> qw = new LambdaQueryWrapper<>();
        //先判断是新增还是修改
        if(!StringUtils.isEmpty(userCheck.getId())){
            //修改过滤当前id
            qw.ne(AdminUser::getId,userCheck.getId());
        }
        Integer userExit = 0;
        if(!StringUtils.isEmpty(userCheck.getUsername())){
            userExit = adminUserService.count(qw.eq(AdminUser::getUsername, userCheck.getUsername()));
        }
        if(userExit>0){
            return R.fail("用户名已存在");
        }
        return R.ok();
    }



    @ApiOperation(value = "根据用户获取角色数据", notes = "根据用户获取角色数据")
    @GetMapping("/assign/{userId}")
    public R userRole(@PathVariable String userId) {
        UserRoles userRoles = roleService.getRolesByUserId(userId);
        return R.ok(userRoles);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/assign")
    public R doAssign(@RequestParam String userId, @RequestParam String[] roleIds) {
        roleService.saveUserRole(userId, roleIds);
        return R.ok();
    }

}