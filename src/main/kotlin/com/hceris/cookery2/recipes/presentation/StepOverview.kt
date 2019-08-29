package com.hceris.cookery2.recipes.presentation

import com.hceris.cookery2.recipes.domain.Step

data class StepOverview(val description: String, val duration: Int)

fun Step.asOverview() = StepOverview(
        description = description,
        duration = duration
)
