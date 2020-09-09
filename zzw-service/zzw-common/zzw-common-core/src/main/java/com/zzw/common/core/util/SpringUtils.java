package com.zzw.common.core.util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    private static Map beanMaps = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);

    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> Map<String, T> getBeanMaps() {
        return beanMaps;
    }

    public static <T> void setBeanMaps(Map<String, T> beanMaps) {
        SpringUtils.beanMaps = beanMaps;
    }

    // 得到实现类
    public static <T> Map<String, T> getClassFromInterface(Class<T> clazz) {

        log.info("SpringUtils getApplicationContext()="+ getApplicationContext());
        beanMaps = getApplicationContext().getBeansOfType(clazz);
        return beanMaps;
    }

    /*****
     * 返回interceptor内的所需对象
     * @param clazz 类的字节码
     * @param request http请求
     * @param <T>
     * @return object
     */
    public static <T> T getDaoBean(Class<T> clazz, HttpServletRequest request){
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return context.getBean(clazz);
    }
}
