package com.zzw.user.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzw.common.core.rest.baseVO.R;
import com.zzw.core.api.dto.RoleDTO;
import com.zzw.core.api.po.Role;
import com.zzw.core.api.vo.RoleCheck;
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
@RequestMapping("/admin/role")
public class AdminRoleController {
    private RoleService roleService;

    @ApiOperation(value = "获取角色分页列表", notes = "获取角色分页列表")
    @GetMapping("/page")
    public R<IPage<Role>> page(Page page, RoleDTO param) {
        return new R<>(roleService.page(page, param));
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("{id}")
    public R<Role> get(@PathVariable String id) {
        return R.ok(roleService.getById(id));
    }

    @ApiOperation(value = "新增角色")
    @PostMapping()
    public R<Boolean> save(@RequestBody Role role) {
        return R.ok(roleService.save(role));
    }

    @ApiOperation(value = "修改角色")
    @PutMapping()
    public R<Boolean> updateById(@RequestBody Role role) {
        return R.ok(roleService.updateById(role));
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "表ID", required = true, paramType = "path")
    })
    @DeleteMapping("{id}")
    public R<Boolean> remove(@PathVariable String id) {
        return R.ok(roleService.removeById(id));
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batch")
    public R<Boolean> batchRemove(@RequestBody List<String> idList) {
        return R.ok(roleService.removeByIds(idList));
    }

    @ApiOperation(value = "角色校验方法", notes = "角色校验方法")
    @PostMapping("check")
    public R<Boolean> post(@RequestBody RoleCheck roleCheck) throws Exception {
        LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
        //先判断是新增还是修改
        if(!StringUtils.isEmpty(roleCheck.getId())){
            //修改过滤当前id
            qw.ne(Role::getId,roleCheck.getId());
        }
        Integer userExit = 0;
        if(!StringUtils.isEmpty(roleCheck.getRoleName())){
            userExit = roleService.count(qw.eq(Role::getRoleName, roleCheck.getRoleName()));
        }
        if(userExit>0){
            return R.fail("角色已存在");
        }
        return R.ok();
    }
}