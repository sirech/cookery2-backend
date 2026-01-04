package com.hceris.cookery2.recipes.presentation

import com.hceris.cookery2.recipes.domain.Step
import io.swagger.v3.oas.annotations.media.Schema

data class StepOverview(
        @field:Schema(example = "Boil the pasta")
        val description: String,
        @field:Schema(example = "5")
        val duration: Int)

fun Step.asOverview() = StepOverview(
        description = description,
        duration = duration
)
