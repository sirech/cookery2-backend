package com.hceris.cookery2.recipes

import com.hceris.cookery2.recipes.domain.RecipeCreated
import com.hceris.cookery2.recipes.domain.RecipeForm
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private const val PATH = "/rest/recipes"

@RestController
@RequestMapping(PATH, produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
class RecipesController {
    @PostMapping
    fun create(@RequestBody form: RecipeForm): ResponseEntity<RecipeCreated> {
        return ResponseEntity.status(HttpStatus.CREATED).body(RecipeCreated(1))
    }

}