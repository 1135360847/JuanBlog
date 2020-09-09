package com.zzw.user.controller;
import com.zzw.common.core.rest.baseVO.R;
import com.zzw.core.api.dto.PermissionDTO;
import com.zzw.core.api.po.Permission;
import com.zzw.core.api.vo.PermissionData;
import com.zzw.core.api.vo.RoleMenuView;
import com.zzw.user.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import io.swagger.annotations.Api;

import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 用户相关API接口层</P>
 * @version: V1.0
 * @author: zzw
 * @time    2020年07月13日
 *
 */
@Api(tags = "用户相关",value="用户相关" )
@RestController
@AllArgsConstructor
@RequestMapping("/admin/permission")
public class AdminPermissionController {
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public R<PermissionData> indexAllPermission(PermissionDTO permissionDTO) {
        return R.ok(permissionService.queryAllMenu(permissionDTO));
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        permissionService.removeChildById(id);
        return R.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/assign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionShip(roleId,permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("assign/{roleId}")
    public R assign(@PathVariable String roleId) {
        RoleMenuView roleMenuView = permissionService.selectMenuByRoleId(roleId);
        return R.ok(roleMenuView);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }
}