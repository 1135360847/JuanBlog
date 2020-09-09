package com.zzw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.core.api.dto.RoleDTO;
import com.zzw.core.api.po.Role;
import com.zzw.core.api.po.UserRole;
import com.zzw.core.api.vo.UserRoles;
import com.zzw.user.mapper.RoleMapper;
import com.zzw.user.service.RoleService;
import com.zzw.user.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class RoleServiceImpl  extends ServiceImpl<RoleMapper, Role> implements RoleService  {
    private UserRoleService userRoleService;
    //根据用户获取角色数据
    @Override
    public UserRoles getRolesByUserId(String userId) {
        //查询所有的角色
        List<Role> allRolesList =baseMapper.selectList(Wrappers.emptyWrapper());

        //根据用户id，查询用户拥有的角色id
        List<UserRole> existUserRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId).select(UserRole::getRoleId));

        List<String> existRoleList = existUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());

        //对角色进行分类
        List<Role> assignRoles = new ArrayList<Role>();
        allRolesList.forEach(role -> {
            //已分配
            if(existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        });

        UserRoles userRoles = new UserRoles();
        userRoles.setAssignRoles(assignRoles);
        userRoles.setAllRoleList(allRolesList);
        return userRoles;
    }

    //根据用户分配角色
    @Override
    public void saveUserRole(String userId, String[] roleIds) {
        //先清空对应的角色
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        //添加角色
        List<UserRole> userRoleList = new ArrayList<>();
        Arrays.stream(roleIds)
                .filter(roleId -> !StringUtils.isEmpty(roleId))
                .forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        });
        userRoleService.saveBatch(userRoleList);
    }

    /**
     * 分页查询后台角色
     * @param page
     * @param roleDTO
     * @return
     */
    public IPage<Role> page(Page page, RoleDTO roleDTO) {
        LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
        //用户名模糊查询
        if (!StringUtils.isEmpty(roleDTO.getRoleName())) {
            qw.like(Role::getRoleName, roleDTO.getRoleName());
        }
        //昵称模糊查询
        if (!StringUtils.isEmpty(roleDTO.getRoleCode())) {
            qw.eq(Role::getRoleCode, roleDTO.getRoleCode());
        }
        //查询起始日期
        if (!StringUtils.isEmpty(roleDTO.getStartDate())) {
            qw.ge(Role::getGmtCreate, roleDTO.getStartDate());
        }
        //查询结束日期
        if (!StringUtils.isEmpty(roleDTO.getEndDate())) {
            qw.le(Role::getGmtCreate, roleDTO.getEndDate());
        }
        return this.getBaseMapper().selectPage(page, qw);
    }


}