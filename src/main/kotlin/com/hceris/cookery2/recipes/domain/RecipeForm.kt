package com.hceris.cookery2.recipes.domain

import io.swagger.v3.oas.annotations.media.Schema

data class StepForm(
        @field:Schema(example = "Boil the pasta")
        val description: String,
        @field:Schema(example = "5")
        val duration: Int)

data class IngredientForm(
        @field:Schema(example = "Fresh pasta")
        val name: String,
        @field:Schema(example = "300")
        val quantity: Int,
        @field:Schema(example = "gr")
        val unit: String)

data class RecipeForm(
        @field:Schema(example = "Pasta carbonara")
        val name: String,
        @field:Schema(example = "3")
        val servings: Int,
        val steps: List<StepForm>,
        val ingredients: List<IngredientForm>)

data class RecipeCreated(
        @field:Schema(example = "1")
        val id: Int)
