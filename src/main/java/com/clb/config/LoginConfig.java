package com.clb.config;

import com.clb.interceptor.WebAppInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Bean
    public WebAppInterceptor myAuthInterceptor() {
        return new WebAppInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(myAuthInterceptor());
        registration.excludePathPatterns("/ssoTokenCheck");
        registration.addPathPatterns("/**");                      //所有路径都被拦截

    }
}