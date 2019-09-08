package com.hceris.cookery2.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hceris.cookery2"))
                .paths(PathSelectors.any())
                .build()
                .protocols(setOf("https"))
                .useDefaultResponseMessages(false)
    }

    private fun apiInfo() = ApiInfoBuilder()
            .title("Cookery2 API")
            .description("This API allows to create and read recipes")
            .license("MIT")
            .licenseUrl("https://github.com/sirech/cookery2-backend/blob/master/LICENSE")
            .version("1")
            .build()
}
