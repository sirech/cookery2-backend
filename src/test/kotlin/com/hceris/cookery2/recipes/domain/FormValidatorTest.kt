package com.hceris.cookery2.recipes.domain

import com.hceris.cookery2.Fixtures
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

internal class FormValidatorTest {

    private fun subject(form: RecipeForm) = FormValidator(form).isValid()

    @Test
    fun `returns true if form is valid`() {
        expectThat(subject(Fixtures.recipeForm)).isTrue()

    }

    @Test
    fun `rejects empty recipe name`() {
        expectThat(subject(RecipeForm(
                "",
                2,
                steps = listOf(),
                ingredients = listOf()))).isFalse()
    }

    @Test
    fun `rejects invalid servings`() {
        expectThat(subject(RecipeForm(
                "Pasta",
                -1,
                steps = listOf(),
                ingredients = listOf()))).isFalse()
    }

    @Test
    fun `rejects empty ingredient names`() {
        expectThat(subject(RecipeForm(
                "Pasta",
                2,
                steps = listOf(),
                ingredients = listOf(IngredientForm("", 3, "gr"))))).isFalse()
    }

    @Test
    fun `rejects invalid ingredient quantities`() {
        expectThat(subject(RecipeForm(
                "Pasta",
                2,
                steps = listOf(),
                ingredients = listOf(IngredientForm("Pasta", -1, "gr"))))).isFalse()
    }

    @Test
    fun `rejects empty step descriptions`() {
        expectThat(subject(RecipeForm(
                "Pasta",
                2,
                steps = listOf(StepForm("", 5)),
                ingredients = listOf()))).isFalse()
    }

    @Test
    fun `rejects invalid step durations`() {
        expectThat(subject(RecipeForm(
                "Pasta",
                2,
                steps = listOf(StepForm("Boil the pasta", -1)),
                ingredients = listOf()))).isFalse()
    }
}
