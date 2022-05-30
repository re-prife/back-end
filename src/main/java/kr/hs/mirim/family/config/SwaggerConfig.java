package kr.hs.mirim.family.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {
    private ApiInfo SwaggerApiInfo() {
        return new ApiInfoBuilder()
                .title("Freemily")
                .description("2022 IT SHOW 전시회 작품으로 가족과 함께 냉장고를 공유하는 서비스의 API입니다.")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket GroupApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("API")
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kr.hs.mirim.family.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(SwaggerApiInfo());
    }
}
