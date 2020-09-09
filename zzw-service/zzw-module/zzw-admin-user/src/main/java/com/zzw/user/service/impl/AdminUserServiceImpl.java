package com.zzw.user.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.common.core.constant.JWTConstants;
import com.zzw.common.core.constant.eunms.ResultEnum;
import com.zzw.common.core.exception.ZzwException;
import com.zzw.common.core.util.MD5;
import com.zzw.core.api.dto.LoginDTO;
import com.zzw.core.api.dto.UserDTO;
import com.zzw.core.api.po.AdminUser;
import com.zzw.core.api.vo.MenuTree;
import com.zzw.core.api.vo.UserInfo;
import com.zzw.user.mapper.AdminUserMapper;
import com.zzw.user.mapper.RoleMapper;
import com.zzw.user.service.AdminUserService;
import com.zzw.user.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 用户相关服务实现层</P>
 *
 * @version: V1.0
 * @author: zzw
 */
@Service
@AllArgsConstructor
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {
    private RoleMapper roleMapper;
    private PermissionService permissionService;
    private RedisTemplate redisTemplate;



//    //登录的方法
//    @Override
//    public String login(LoginDTO loginDTO) {
//        //获取登录手机号和密码
//        String username = loginDTO.getUsername();
//        String password = loginDTO.getPassword();
//        //手机号和密码非空判断
//        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
//            throw new ZzwException(ResultEnum.ACCOUNT_NOT_EXIST);
//        }
//        //判断手机号是否正确5g
//        LambdaQueryWrapper<AdminUser> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(AdminUser::getUsername, username);
//        AdminUser user = baseMapper.selectOne(wrapper);
//        //判断查询对象是否为空
//        if (user == null) {//没有这个账号
//            throw new ZzwException(ResultEnum.ACCOUNT_NOT_EXIST);
//        }
//
//        //判断密码
//        //因为存储到数据库密码肯定加密的
//        //把输入的密码进行加密，再和数据库密码进行比较
//        //加密方式 MD5
//        if (!MD5.encrypt(password).equals(user.getPassword())) {
//            throw new ZzwException(ResultEnum.ACCOUNT_PASSWORD_ERROR);
//        }
//
//        //登录成功
//        //生成token字符串，使用jwt工具类
//        String jwtToken = JwtUtils.getJwtToken(user.getId(), user.getNickName(), JWTConstants.ADMIN_SUBJECT);
//        return jwtToken;
//    }

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    public UserInfo getUserInfo(String username) {
        UserInfo result = new UserInfo();
        AdminUser user = baseMapper.selectOne(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getUsername, username));
        if (null == user) {
            throw new ZzwException(ResultEnum.ACCOUNT_NOT_EXIST);
        }
        //根据用户id获取角色
        List<String> roleNameList = roleMapper.selectRoleNamesByUserId(user.getId());
        if (roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);
        result.setUsername(user.getUsername());
        //TODO 回头对头像进行修改
        result.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.setRoles(roleNameList);
        result.setPermissionValueList(permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     *
     * @param username
     * @return
     */
    public List<MenuTree> getMenu(String username) {
        AdminUser user = baseMapper.selectOne(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getUsername, username));

        //根据用户id获取用户菜单权限
        List<MenuTree> permissionList = permissionService.selectPermissionByUserId(user.getId());
        return permissionList;
    }

    /**
     * 分页查询后台管理用户
     * @param page
     * @param userDTO
     * @return
     */
    public IPage<AdminUser> page(Page page, UserDTO userDTO) {
        LambdaQueryWrapper<AdminUser> qw = new LambdaQueryWrapper<>();
        //用户名模糊查询
        if (!StringUtils.isEmpty(userDTO.getUsername())) {
            qw.like(AdminUser::getUsername, userDTO.getUsername());
        }
        //昵称模糊查询
        if (!StringUtils.isEmpty(userDTO.getNickName())) {
            qw.like(AdminUser::getNickName, userDTO.getNickName());
        }
        //查询起始日期
        if (!StringUtils.isEmpty(userDTO.getStartDate())) {
            qw.ge(AdminUser::getGmtCreate, userDTO.getStartDate());
        }
        //查询结束日期
        if (!StringUtils.isEmpty(userDTO.getEndDate())) {
            qw.le(AdminUser::getGmtCreate, userDTO.getEndDate());
        }
        return this.getBaseMapper().selectPage(page, qw);
    }

    /**
     * 按条件查询后台管理用户集合
     */
    public List<AdminUser> list(UserDTO userDTO) {
        LambdaQueryWrapper<AdminUser> qw = new LambdaQueryWrapper<>();
        //用户名模糊查询
        if (!StringUtils.isEmpty(userDTO.getUsername())) {
            qw.like(AdminUser::getUsername, userDTO.getUsername());
        }
        //昵称模糊查询
        if (!StringUtils.isEmpty(userDTO.getNickName())) {
            qw.like(AdminUser::getNickName, userDTO.getNickName());
        }
        //查询起始日期
        if (!StringUtils.isEmpty(userDTO.getStartDate())) {
            qw.ge(AdminUser::getGmtCreate, userDTO.getStartDate());
        }
        //查询结束日期
        if (!StringUtils.isEmpty(userDTO.getEndDate())) {
            qw.le(AdminUser::getGmtCreate, userDTO.getEndDate());
        }
        return this.getBaseMapper().selectList(qw);
    }

    /**
     * 重写新增
     * @param user
     * @return
     */
    public boolean save(AdminUser user){
        //加密
        String password = user.getPassword();
        String enPassword = MD5.encrypt(password);
        user.setPassword(enPassword);

        if(baseMapper.insert(user)>0){
            return true;
        }
        return false;
    }

}