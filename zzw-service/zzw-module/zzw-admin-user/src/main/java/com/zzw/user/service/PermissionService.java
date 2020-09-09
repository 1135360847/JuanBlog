package com.zzw.user.service;

import com.alibaba.fastjson.JSONObject;
import com.zzw.core.api.dto.PermissionDTO;
import com.zzw.core.api.po.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.core.api.vo.MenuTree;
import com.zzw.core.api.vo.PermissionData;
import com.zzw.core.api.vo.RoleMenuView;

import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 用户相关服务层</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
public interface PermissionService extends IService<Permission> {

    List<String> selectPermissionValueByUserId(String id);


    List<MenuTree> selectPermissionByUserId(String id);

    //获取全部菜单
    PermissionData queryAllMenu(PermissionDTO permissionDTO);

    //递归删除菜单
    void removeChildById(String id);

    //给角色分配权限
    void saveRolePermissionShip(String roleId, String[] permissionId);

    //根据角色获取菜单
    RoleMenuView selectMenuByRoleId(String roleId);
}