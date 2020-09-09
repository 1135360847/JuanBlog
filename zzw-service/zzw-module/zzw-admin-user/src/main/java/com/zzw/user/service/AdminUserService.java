package com.zzw.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.core.api.dto.LoginDTO;
import com.zzw.core.api.dto.UserDTO;
import com.zzw.core.api.po.AdminUser;
import com.zzw.core.api.vo.MenuTree;
import com.zzw.core.api.vo.UserInfo;

import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 用户相关服务层</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
public interface AdminUserService extends IService<AdminUser> {

//    String login(LoginDTO loginDTO);

    /**
     * 根据用户名获取用户登录信息
     * @param username
     * @return
     */
    UserInfo getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    List<MenuTree> getMenu(String username);


    IPage<AdminUser> page(Page page, UserDTO userDTO);

    List<AdminUser> list(UserDTO param);
}