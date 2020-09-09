package com.zzw.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzw.core.api.dto.RoleDTO;
import com.zzw.core.api.po.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.core.api.vo.UserRoles;

import java.util.Map;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 用户相关服务层</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
public interface RoleService extends IService<Role> {
    UserRoles getRolesByUserId(String userId);

    //根据用户分配角色
    void saveUserRole(String userId, String[] roleId);

    IPage<Role> page(Page page, RoleDTO userDTO);
}