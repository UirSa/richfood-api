package com.richfood.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置 CORS 設置
        registry.addMapping("/**")  // 設置允許跨域的路徑，這裡是全域所有路徑
                .allowedOrigins("http://localhost:5173/")  // 允許的來源（React 前端地址）
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允許的方法
                .allowedHeaders("*")  // 允許的標頭
                .allowCredentials(true)  // 是否允許發送 Cookie
                .maxAge(3600);  // 預檢請求的最大有效時間（秒）
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/UserImages/**")
                .addResourceLocations("classpath:/static/UserImages/");
    }
}
