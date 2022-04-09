package com.wstx.ds.common.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware {
    static ApplicationContext app;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (BeanUtils.app == null)
            BeanUtils.app = applicationContext;
    }

    public static <T>T getBean(Class<T> clazz){
        return app.getBean(clazz);
    }
}
