package com.zzw.user.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.core.api.dto.PermissionDTO;
import com.zzw.core.api.po.AdminUser;
import com.zzw.core.api.po.Permission;
import com.zzw.core.api.po.RolePermission;
import com.zzw.core.api.vo.MenuTree;
import com.zzw.core.api.vo.PermissionData;
import com.zzw.core.api.vo.PermissionTree;
import com.zzw.core.api.vo.RoleMenuView;
import com.zzw.core.converter.PermissionTreeUtils;
import com.zzw.user.helper.MenuHelper;
import com.zzw.user.helper.PermissionHelper;
import com.zzw.user.mapper.AdminUserMapper;
import com.zzw.user.mapper.PermissionMapper;
import com.zzw.user.service.PermissionService;
import com.zzw.user.service.RolePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p> 
 * 
 * <p>说明： 用户相关服务实现层</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
@Service
@AllArgsConstructor
public class PermissionServiceImpl  extends ServiceImpl<PermissionMapper, Permission> implements PermissionService  {
    private RolePermissionService rolePermissionService;
    private AdminUserMapper userMapper;


    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<MenuTree> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.build(selectPermissionList);
        List<MenuTree> result = MenuHelper.build(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        AdminUser user = userMapper.selectById(userId);
        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }


    //获取全部菜单
    @Override
    public PermissionData queryAllMenu(PermissionDTO permissionDTO) {
        PermissionData permissionData = new PermissionData();
        //查出所有的数据
        List<Permission> allList = baseMapper.selectList(Wrappers.emptyWrapper());
        //根据条件查询所有的展开的菜单
        LambdaQueryWrapper<Permission> wq = new LambdaQueryWrapper<>();
        wq.orderByDesc(Permission::getId);
        //根据名称查询出所有菜单
        wq.eq(Permission::getPid, 1);
        if(!StringUtils.isEmpty(permissionDTO.getMenuName())){
            wq.like(Permission::getName,permissionDTO.getMenuName());
        }
        List<Permission> queryMenuList = baseMapper.selectList(wq);
        //拿出所有查询的菜单id
        List<Permission> buildList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(queryMenuList)){
            //根据菜单
            buildList = build(queryMenuList,allList);
        }
        permissionData.setPermissionList(buildList);
        return permissionData;
    }


    /**
     * 构建前端展示菜单
     * @param
     * @return
     */
    private static List<Permission> build(List<Permission> queryList,List<Permission> allList) {
        if(CollectionUtils.isEmpty(queryList)){
            return new ArrayList<>();
        }
        List<Permission> result = new ArrayList<>();
        allList.forEach(x->{
            if("0".equals(x.getPid())){
                x.setLevel(1);
                result.add(x);
            }
        });
        queryList.forEach(query ->{
            query.setLevel(2);
            if(result.get(0).getChildren()==null){
                result.get(0).setChildren(new ArrayList<>());
            }
            result.get(0).getChildren().add(PermissionHelper.findChildren(query,allList));
        });
        return result;
    }




    //============递归删除菜单==================================
    @Override
    public void removeChildById(String id) {
        //1 创建list集合，用于封装所有删除菜单id值
        List<String> idList = new ArrayList<>();
        //查询出所有的数据
        List<Permission> permissionList = baseMapper.selectList(Wrappers.emptyWrapper());
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById(id,idList,permissionList);
        //把当前id封装到list里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    /**
     * 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
     * @param id
     * @param idList
     */
    private void selectPermissionChildById(String id, List<String> idList,List<Permission> permissionList) {
        if(CollectionUtils.isEmpty(permissionList)){
            return;
        }
        permissionList.forEach(item ->{
            if(id.equals(item.getPid())){
                idList.add(item.getId());
                this.selectPermissionChildById(item.getId(),idList,permissionList);
            }
        });

    }

    //=========================给角色分配菜单=======================
    @Override
    public void saveRolePermissionShip(String roleId, String[] permissionIds) {
        //先删除所有的角色
        rolePermissionService.remove(Wrappers.<RolePermission>lambdaUpdate().eq(RolePermission::getRoleId,roleId));
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        for(String perId : permissionIds) {
            //RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            //封装到list集合
            rolePermissionList.add(rolePermission);
        }
        //添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionList);
    }


    //根据角色获取菜单
    @Override
    public RoleMenuView selectMenuByRoleId(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));
        //生成树
        List<PermissionTree> permissionTreeList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(allPermissionList)){
            permissionTreeList = PermissionTreeUtils.build(allPermissionList);
        }
        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        Set<String> rolePermissionIdList = new HashSet<>();
        if(CollectionUtils.isNotEmpty(rolePermissionList)){
            rolePermissionIdList = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
        }

        RoleMenuView roleMenuView = new RoleMenuView();
        roleMenuView.setAllPermission(permissionTreeList);
        roleMenuView.setCheckPermissionIdList(rolePermissionIdList);
        return roleMenuView;
    }




}