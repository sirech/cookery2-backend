package com.hceris.cookery2.recipes.presentation

import com.hceris.cookery2.recipes.domain.Recipe
import io.swagger.annotations.ApiModelProperty

data class RecipeDetails(
        @ApiModelProperty(example = "1")
        val id: Int,
        @ApiModelProperty(example = "Pasta carbonara")
        val name: String,
        @ApiModelProperty(example = "3")
        val servings: Int,
        @ApiModelProperty(example = "20")
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
