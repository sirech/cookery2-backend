package com.hceris.cookery2.recipes

import com.hceris.cookery2.recipes.domain.RecipeCreated
import com.hceris.cookery2.recipes.domain.RecipeForm
import com.hceris.cookery2.recipes.presentation.RecipeOverview
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val PATH = "/rest/recipes"

@RestController
@RequestMapping(PATH, produces = [MediaType.APPLICATION_JSON_VALUE])
class RecipesController(val repository: RecipeRepository) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody form: RecipeForm): ResponseEntity<RecipeCreated> {
        val id = repository.create(form)
        return ResponseEntity.status(HttpStatus.CREATED).body(RecipeCreated(id))
    }

    @GetMapping
    fun recipes(): List<RecipeOverview> {
        return repository.all()
    }
}
