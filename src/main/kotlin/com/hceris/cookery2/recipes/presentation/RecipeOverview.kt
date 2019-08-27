package com.hceris.cookery2.recipes.presentation

import com.hceris.cookery2.recipes.domain.Recipe

data class RecipeOverview(
        val id: Int,
        val name: String,
        val servings: Int,
        val duration: Int
)

fun Recipe.asOverview() = RecipeOverview(
        id = id.value,
        name = name,
        servings = servings,
        duration = steps.map { it.duration }.sum()
)
