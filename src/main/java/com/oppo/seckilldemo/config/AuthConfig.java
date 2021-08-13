package com.oppo.seckilldemo.config;

import com.oppo.seckilldemo.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Bean
    public GlobalInterceptor initAuthInterceptor(){
        return new GlobalInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(initAuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**");
    }

}
