package com.hceris.cookery2.recipes.presentation

import com.hceris.cookery2.recipes.domain.Ingredient
import io.swagger.annotations.ApiModelProperty

data class IngredientOverview(
        @ApiModelProperty(example = "Fresh pasta")
        val name: String,
        @ApiModelProperty(example = "300")
        val quantity: Int,
        @ApiModelProperty(example = "gr")
        val unit: String)

fun Ingredient.asOverview() = IngredientOverview(
        name = name,
        quantity = quantity,
        unit = unit
)
