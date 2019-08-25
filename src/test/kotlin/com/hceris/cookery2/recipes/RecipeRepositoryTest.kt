package com.hceris.cookery2.recipes

import com.hceris.cookery2.TestTransactionConfiguration
import com.hceris.cookery2.recipes.domain.RecipeForm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
        classes = [
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
@Transactional
internal class RecipeRepositoryTest {
    val repository = RecipeRepository()

    @Test
    fun `creates a new recipe`() {
        repository.create(RecipeForm("carbonara", 2, listOf(), listOf()))
    }

    @Test
    fun `reads existing recipe`() {
        val id = repository.create(RecipeForm("carbonara", 2, listOf(), listOf()))
        val recipe = repository.find(id)
        expectThat(recipe)
                .isNotNull()
                .get { name }
                .isEqualTo("carbonara")
    }
}