package com.zzw.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangzhiwen
 */
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableZzwFeignClients
//@ComponentScan("com.zzw")
public class AdminUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminUserApplication.class, args);
    }

}
