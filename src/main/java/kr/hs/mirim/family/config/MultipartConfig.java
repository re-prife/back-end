package kr.hs.mirim.family.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;


@Configuration
public class MultipartConfig {
    private final int FILE_MAX_UPLOAD_SIZE = 1024*1024*10;

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(FILE_MAX_UPLOAD_SIZE);

        try{
            multipartResolver.setUploadTempDir(new FileSystemResource("./upload"));
        }catch (IOException e){
            e.getCause().getMessage();
        }
        return multipartResolver;
    }
}
