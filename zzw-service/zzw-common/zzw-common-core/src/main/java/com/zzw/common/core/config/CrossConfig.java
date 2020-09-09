package com.zzw.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author ：zhangzhiwen
 * @date ：Created in 2019/10/31 14:14
 * @description：跨域配置
 * @modified By：
 */
@Configuration
public class CrossConfig {
    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        //是否设置跨域
        config.setAllowCredentials(true);
        //允许的域名例如http:www.a.com
        config.setAllowedOrigins(Arrays.asList("*"));
        //允许的头
        config.setAllowedHeaders(Arrays.asList("*"));
        //允许的类型例如get，post，put
        config.setAllowedMethods(Arrays.asList("*"));
        //缓存时间，时间内相同的请求不进行检查，单位s
        config.setMaxAge(300L);
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }

}
