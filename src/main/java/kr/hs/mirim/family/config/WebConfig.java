package kr.hs.mirim.family.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    private String resourcePath = "d:/temp/upload/users/";

    private String uploadPath = "/upload/**";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(uploadPath)
                .addResourceLocations(resourcePath);
    }
}
