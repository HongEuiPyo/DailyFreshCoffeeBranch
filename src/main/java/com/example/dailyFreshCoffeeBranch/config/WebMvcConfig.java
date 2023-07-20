package com.example.dailyFreshCoffeeBranch.config;

import com.example.dailyFreshCoffeeBranch.com.UserInfoArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserInfoArgumentResolver userInfoArgumentResolver;

    @Value("${uploadPath}")
    String uploadPath;

    //    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new PerformanceInterceptor())
//                .addPathPatterns("/**") // 인터셉터를 적용할 대상
//                .excludePathPatterns("/css/**", "/js/**"); // 인터셉터 적용 제외대상
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userInfoArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }

}