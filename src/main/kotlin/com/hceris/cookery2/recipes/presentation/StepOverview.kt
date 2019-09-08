package com.hceris.cookery2.recipes.presentation

import com.hceris.cookery2.recipes.domain.Step
import io.swagger.annotations.ApiModelProperty

data class StepOverview(
        @ApiModelProperty(example = "Boil the pasta")
        val description: String,
        @ApiModelProperty(example = "5")
        val duration: Int)

fun Step.asOverview() = StepOverview(
        description = description,
        duration = duration
)
