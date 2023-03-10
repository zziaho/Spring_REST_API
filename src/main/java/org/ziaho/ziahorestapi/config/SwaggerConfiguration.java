package org.ziaho.ziahorestapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(
                title = "Spring API Documentation",
                description = "앱 개발시 사용되는 서버 API에 대한 연동 문서입니다.",
                version = "1",
                contact = @Contact(
                        name = "ziaho",
                        email = "rlawlgh1100@gamil.com"
                )
        )
)

@Configuration
public class SwaggerConfiguration {



//    @Bean
//    public Docket swaggerApi() {
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
//                .apis(RequestHandlerSelectors.basePackage("org.ziaho.ziahorestapi.controller"))
//                // basePackage : 지정한 pacakge 경로 하단의 Controller 내용을 읽어 매핑된 resource들을 문서화 시킵니다.
//                .paths(PathSelectors.any())
//                .build()
//                .useDefaultResponseMessages(false); // 기본으로 세팅되는 200, 401, 403, 404 메세지를 표시 하지 않음
//    }
//
//    private ApiInfo swaggerInfo() {
//        return new ApiInfoBuilder().title("Spring API Documentation")
//                .description("앱 개발시 사용되는 서버 API에 대한 연동 문서입니다.")
//                .license("zzziaho").licenseUrl("https://ziaho0403.tistory.com/").version("1").build();
//    }

}