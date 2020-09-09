package com.zzw.user.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.zzw.common.core.constant.eunms.ResultEnum;
import com.zzw.common.core.rest.baseVO.R;
import com.zzw.common.security.entity.User;
import com.zzw.core.api.dto.LoginDTO;
import com.zzw.core.api.vo.MenuTree;
import com.zzw.core.api.vo.UserInfo;
import com.zzw.user.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "登陆相关",value="登陆相关" )
@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/admin/index")
public class AdminLoginController {
    private AdminUserService adminUserService;

    @ApiOperation(value = "登陆", notes = "登陆由security进行管理，该登录接口只用于security配置时填入")
    @PostMapping("login")
    public R<String> loginUser(
            @RequestBody LoginDTO loginDTO) {
//        if(StringUtils.isEmpty(loginDTO.getUsername())){
//             return new R(ResultEnum.ACCOUNT_PARAM_NULL);
//        }
//
//        if(StringUtils.isEmpty(loginDTO.getPassword())){
//            return new R(ResultEnum.PASSWORD_PARAM_NULL);
//        }
//
//        //返回token值，使用jwt生成
//        String token = adminUserService.login(loginDTO);
        //
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public R<UserInfo> info(){
        //获取当前登录用户用户名
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo userInfo = adminUserService.getUserInfo(user.getUsername());
        return R.ok(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public R<List<MenuTree>> getMenu(){
        //获取当前登录用户用户名
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MenuTree> permissionList = adminUserService.getMenu(user.getUsername());
        return R.ok(permissionList);
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }



}