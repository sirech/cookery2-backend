package com.hceris.cookery2.recipes.presentation

import com.hceris.cookery2.recipes.domain.Ingredient
import io.swagger.v3.oas.annotations.media.Schema

data class IngredientOverview(
        @field:Schema(example = "Fresh pasta")
        val name: String,
        @field:Schema(example = "300")
        val quantity: Int,
        @field:Schema(example = "gr")
        val unit: String)

fun Ingredient.asOverview() = IngredientOverview(
        name = name,
        quantity = quantity,
        unit = unit
)
