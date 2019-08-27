package com.hceris.cookery2.recipes

import com.hceris.cookery2.recipes.domain.Ingredient
import com.hceris.cookery2.recipes.domain.Recipe
import com.hceris.cookery2.recipes.domain.RecipeForm
import com.hceris.cookery2.recipes.domain.Step
import com.hceris.cookery2.recipes.presentation.asOverview
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RecipeRepository {
    companion object {
        const val PAGE_SIZE = 25
    }

    fun create(recipeForm: RecipeForm): Int {
        val recipe = Recipe.new {
            name = recipeForm.name
            servings = recipeForm.servings
        }

        createIngredients(recipeForm, recipe)
        createSteps(recipeForm, recipe)

        return recipe.id.value
    }

    fun find(id: Int) = Recipe.findById(id)

    fun all() = Recipe.all().limit(PAGE_SIZE).map { it.asOverview() }

    private fun createIngredients(recipeForm: RecipeForm, recipe: Recipe) =
            recipeForm.ingredients.map {
                Ingredient.new {
                    name = it.name
                    quantity = it.quantity
                    unit = it.unit
                    this.recipe = recipe
                }
            }

    private fun createSteps(recipeForm: RecipeForm, recipe: Recipe) =
            recipeForm.steps.map {
                Step.new {
                    description = it.description
                    duration = it.duration
                    this.recipe = recipe
                }
            }
}
