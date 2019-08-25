package com.hceris.cookery2.recipes

import com.hceris.cookery2.recipes.domain.Recipe
import com.hceris.cookery2.recipes.domain.RecipeForm
import org.joda.time.DateTime
import org.springframework.stereotype.Service

@Service
class RecipeRepository {
    fun create(recipe: RecipeForm): Int {
        return Recipe.new {
            name = recipe.name
            servings = recipe.servings

            DateTime.now().let {
                updatedAt = it
                createdAt = it
            }

        }.id.value
    }

    fun find(id: Int) = Recipe.findById(id)
}
