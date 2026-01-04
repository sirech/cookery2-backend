package com.hceris.cookery2.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {
    @Bean
    fun api(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Cookery2 API")
                    .description("This API allows to create and read recipes")
                    .license(
                        License()
                            .name("MIT")
                            .url("https://github.com/sirech/cookery2-backend/blob/master/LICENSE")
                    )
                    .version("1")
            )
    }
}
