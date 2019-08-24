package com.hceris.cookery2.recipes.domain

data class StepForm(val description: String, val duration: Int)

data class IngredientForm(val name: String, val quantity: Int, val unit: String)

data class RecipeForm(
        val name: String,
        val servings: Int,
        val steps: List<StepForm>,
        val ingredients: List<IngredientForm>)

data class RecipeCreated(val id: Int)
