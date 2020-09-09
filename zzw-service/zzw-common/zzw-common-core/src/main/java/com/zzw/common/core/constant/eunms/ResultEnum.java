package com.zzw.common.core.constant.eunms;

import lombok.Getter;

/**
 * @author zhangzhiwen
 */

@Getter
public enum ResultEnum {

    LOGIN_ERROR(1,"登陆失败"),
    ACCOUNT_NOT_EXIST(2,"账号不存在"),
    ACCOUNT_PASSWORD_ERROR(3,"账号或密码错误"),
    ACCOUNT_PARAM_NULL(4,"账号输入为空"),
    PASSWORD_PARAM_NULL(5,"密码输入为空"),
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
