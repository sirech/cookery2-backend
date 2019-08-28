package com.hceris.cookery2.pact

import com.hceris.cookery2.recipes.RecipeRepository
import com.hceris.cookery2.recipes.domain.IngredientForm
import com.hceris.cookery2.recipes.domain.RecipeForm
import com.hceris.cookery2.recipes.domain.StepForm
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private const val PATH = "/pact"

data class Pact(val state: String)

@RestController
@RequestMapping(PATH, consumes = [MediaType.APPLICATION_JSON_VALUE])
@ConditionalOnExpression("\${pact.enabled:true}")
@Suppress("MagicNumber")
class PactController(val repository: RecipeRepository) {
    @PostMapping
    fun setup(@RequestBody body: Pact): ResponseEntity<Map<String,String>> {
        when(body.state) {
            "i have a list of recipes" -> initialRecipes()
            else -> doNothing()
        }

        return ResponseEntity.ok(mapOf())
    }

    private fun initialRecipes() {
        val recipeForm = RecipeForm(
                "carbonara", 2,
                listOf(
                        StepForm("boil the pasta in water", 5),
                        StepForm("cook the meat", 10),
                        StepForm("mix the pasta, meat and egg together", 3)
                ),
                listOf(
                        IngredientForm("pasta", 200, "gr"),
                        IngredientForm("guanciale", 100, "gr"),
                        IngredientForm("egg", 1, "unit")
                ))

        repository.create(recipeForm)
    }

    private fun doNothing() {

    }
}
