package com.hceris.cookery2.recipes.presentation

import com.hceris.cookery2.recipes.domain.Recipe

data class RecipeDetails(
        val id: Int,
        val name: String,
        val servings: Int,
        val duration: Int,
        val steps: List<StepOverview>,
        val ingredients: List<IngredientOverview>
)

fun Recipe.asDetails() = RecipeDetails(
        id = id.value,
        name = name,
        servings = servings,
        duration = steps.map { it.duration }.sum(),
        steps = steps.map { it.asOverview() },
        ingredients = ingredients.map { it.asOverview() }
)
