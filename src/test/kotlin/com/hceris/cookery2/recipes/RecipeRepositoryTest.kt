package com.hceris.cookery2.recipes

import arrow.core.Either
import com.hceris.cookery2.Fixtures
import com.hceris.cookery2.TestTransactionConfiguration
import com.hceris.cookery2.recipes.presentation.RecipeDetails
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo

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
        repository.create(Fixtures.recipeForm)
    }

    @Test
    fun `reads existing recipe`() {
        val id = repository.create(Fixtures.recipeForm)
        val recipe = repository.find(id)
        expectThat(recipe)
                .isA<Either.Right<RecipeDetails>>().get { b } and {
            get { name }
                    .isEqualTo("carbonara")
            get { ingredients.toList() }
                    .hasSize(3)
            get { steps.toList() }
                    .hasSize(3)
        }
    }

    @Test
    fun `reads recipes`() {
        (1..10).forEach { _ -> repository.create(Fixtures.recipeForm)  }
        expectThat(repository.all())
                .hasSize(10)
    }
}
