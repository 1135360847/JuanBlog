package com.zzw.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.core.api.po.UserRole;
import com.zzw.user.mapper.UserRoleMapper;
import com.zzw.user.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * create by zhangzhiwen on 2020/7/20
 */
@Service
@AllArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
