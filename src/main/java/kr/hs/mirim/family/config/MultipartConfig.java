package kr.hs.mirim.family.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;


@Slf4j
@Configuration
public class MultipartConfig {
    private final int FILE_MAX_UPLOAD_SIZE = 10485760;

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(FILE_MAX_UPLOAD_SIZE);

        try{
            multipartResolver.setUploadTempDir(new FileSystemResource("c:/temp/upload/users"));
            multipartResolver.setUploadTempDir(new FileSystemResource("c:/temp/upload/ingredients"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return multipartResolver;
    }
}
