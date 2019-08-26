package com.hceris.cookery2.recipes

import com.hceris.cookery2.recipes.domain.Ingredient
import com.hceris.cookery2.recipes.domain.Recipe
import com.hceris.cookery2.recipes.domain.RecipeForm
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

        val ingredients = transaction {
            recipeForm.ingredients.map {
                Ingredient.new {
                    name = it.name
                    quantity = it.quantity
                    unit = it.unit
                    this.recipe = recipe
                }
            }
        }

        return recipe.id.value
    }

    fun find(id: Int) = Recipe.findById(id)
}
