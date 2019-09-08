package com.hceris.cookery2.recipes.domain

import io.swagger.annotations.ApiModelProperty

data class StepForm(
        @ApiModelProperty(example = "Boil the pasta")
        val description: String,
        @ApiModelProperty(example = "5")
        val duration: Int)

data class IngredientForm(
        @ApiModelProperty(example = "Fresh pasta")
        val name: String,
        @ApiModelProperty(example = "300")
        val quantity: Int,
        @ApiModelProperty(example = "gr")
        val unit: String)

data class RecipeForm(
        @ApiModelProperty(example = "Pasta carbonara")
        val name: String,
        @ApiModelProperty(example = "3")
        val servings: Int,
        val steps: List<StepForm>,
        val ingredients: List<IngredientForm>)

data class RecipeCreated(
        @ApiModelProperty(example = "1")
        val id: Int)
