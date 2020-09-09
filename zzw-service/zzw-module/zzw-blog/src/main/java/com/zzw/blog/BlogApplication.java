package com.zzw.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangzhiwen
 */
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableZzwFeignClients
//@ComponentScan("com.zzw")
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
