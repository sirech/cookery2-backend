package com.hceris.cookery2.config

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
class DatabaseConfiguration {
    @Bean
    fun transactionManager(dataSource: HikariDataSource) = SpringTransactionManager(dataSource)
}
