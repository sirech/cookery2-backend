package com.hceris.cookery2.recipes.domain

class FormValidator(private val form: RecipeForm) {
    fun isValid(): Boolean {
        if (form.name.isBlank()) return false
        if (form.servings <= 0) return false

        form.ingredients.map { ingredient ->
            if (ingredient.name.isBlank()) return false
            if (ingredient.quantity <= 0) return false
        }

        form.steps.map { step ->
            if (step.description.isBlank()) return false
            if (step.duration <= 0) return false
        }

        return true
    }
}
