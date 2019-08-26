package com.hceris.cookery2.recipes

import com.hceris.cookery2.recipes.domain.Ingredient
import com.hceris.cookery2.recipes.domain.Recipe
import com.hceris.cookery2.recipes.domain.RecipeForm
import com.hceris.cookery2.recipes.domain.Step
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class RecipeRepository {
    fun create(recipeForm: RecipeForm): Int {
        var recipe = transaction {
            Recipe.new {
                name = recipeForm.name
                servings = recipeForm.servings
            }
        }

        createIngredients(recipeForm, recipe)
        createSteps(recipeForm, recipe)

        return recipe.id.value
    }

    fun find(id: Int) = Recipe.findById(id)

    private fun createIngredients(recipeForm: RecipeForm, recipe: Recipe) = transaction {
        recipeForm.ingredients.map {
            Ingredient.new {
                name = it.name
                quantity = it.quantity
                unit = it.unit
                this.recipe = recipe
            }
        }
    }

    private fun createSteps(recipeForm: RecipeForm, recipe: Recipe) = transaction {
        recipeForm.steps.map {
            Step.new {
                description = it.description
                duration = it.duration
                this.recipe = recipe
            }
        }
    }
}
