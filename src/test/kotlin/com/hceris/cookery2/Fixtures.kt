package com.hceris.cookery2

import com.hceris.cookery2.recipes.domain.IngredientForm
import com.hceris.cookery2.recipes.domain.RecipeForm
import com.hceris.cookery2.recipes.domain.StepForm

object Fixtures {
    val recipeForm = RecipeForm(
            "carbonara", 2,
            listOf(
                    StepForm("boil the pasta in water", 5),
                    StepForm("cook the meat", 10),
                    StepForm("mix the pasta, meat and egg together", 3)
            ),
            listOf(
                    IngredientForm("pasta", 200, "gr"),
                    IngredientForm("guanciale", 100, "gr"),
                    IngredientForm("egg", 1, "unit")
            ))
}
