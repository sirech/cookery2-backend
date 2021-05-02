package com.hceris.cookery2.recipes

import arrow.core.Either
import com.hceris.cookery2.auth.Headers
import com.hceris.cookery2.recipes.domain.FormValidator
import com.hceris.cookery2.recipes.domain.RecipeCreated
import com.hceris.cookery2.recipes.domain.RecipeForm
import com.hceris.cookery2.recipes.presentation.RecipeDetails
import com.hceris.cookery2.recipes.presentation.RecipeOverview
import io.swagger.annotations.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val PATH = "/rest/recipes"

@RestController
@RequestMapping(PATH, produces = [MediaType.APPLICATION_JSON_VALUE])
class RecipesController(val repository: RecipeRepository) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "creates a new recipe")
    @ApiImplicitParams(value = [ApiImplicitParam(
            name = Headers.AUTHORIZATION,
            value = "JWT OAuth token",
            required = true,
            dataType = "string",
            paramType = "header")])
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Recipe was created", response = RecipeCreated::class),
        ApiResponse(code = 403, message = "Not authorized"),
        ApiResponse(code = 400, message = "Invalid recipe form")
    ])
    fun create(@RequestBody form: RecipeForm): ResponseEntity<RecipeCreated> {
        val result = Either.conditionally(FormValidator(form).isValid(),
                { 400 },
                { repository.create(form) })

        return when (result) {
            is Either.Left -> ResponseEntity.status(result.value).build()
            is Either.Right -> ResponseEntity.status(HttpStatus.CREATED).body(RecipeCreated(result.value))
        }
    }

    @GetMapping
    @ApiOperation(value = "gets a list of recipe overviews")
    fun recipes(): List<RecipeOverview> {
        return repository.all()
    }

    @GetMapping("{id}")
    @ApiOperation(value = "gets a concrete recipe, including steps and ingredients")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "The recipe"),
        ApiResponse(code = 404, message = "Recipe with the given id not found")

    ])
    fun recipe(
            @ApiParam("id of the recipe to fetch", required = true)
            @PathVariable
            id: Int): ResponseEntity<RecipeDetails> {
        return when (val result = repository.find(id)) {
            is Either.Left -> ResponseEntity.status(result.value).build()
            is Either.Right -> ResponseEntity.ok(result.value)
        }
    }
}
