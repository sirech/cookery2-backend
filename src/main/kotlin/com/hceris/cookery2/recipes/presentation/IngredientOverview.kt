package com.hceris.cookery2.recipes.presentation

import com.hceris.cookery2.recipes.domain.Ingredient

data class IngredientOverview(val name: String, val quantity: Int, val unit: String)

fun Ingredient.asOverview() = IngredientOverview(
        name = name,
        quantity = quantity,
        unit = unit
)
