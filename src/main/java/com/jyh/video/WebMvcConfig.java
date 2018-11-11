package com.jyh.video;


import com.jyh.video.controller.interceptor.MiniInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 图片绝对地址与虚拟地址映射
 * @author JYH
 * 2018/11/3 13:01
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    /**
     * 文件磁盘图片url映射
     * 配置服务端虚拟路径，handler为前台方法的目录，Locations为files相对应的本地路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("file:D:/video_dev/");
    }

    @Bean
    public MiniInterceptor miniInterceptor(){
        return  new MiniInterceptor();
    }

}
