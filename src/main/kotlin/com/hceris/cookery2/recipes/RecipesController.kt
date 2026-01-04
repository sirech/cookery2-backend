package com.hceris.cookery2.recipes

import arrow.core.Either
import com.hceris.cookery2.auth.Headers
import com.hceris.cookery2.recipes.domain.FormValidator
import com.hceris.cookery2.recipes.domain.RecipeCreated
import com.hceris.cookery2.recipes.domain.RecipeForm
import com.hceris.cookery2.recipes.presentation.RecipeDetails
import com.hceris.cookery2.recipes.presentation.RecipeOverview
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val PATH = "/rest/recipes"

@RestController
@RequestMapping(PATH, produces = [MediaType.APPLICATION_JSON_VALUE])
class RecipesController(val repository: RecipeRepository) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "creates a new recipe",
        parameters = [
            Parameter(
                name = Headers.AUTHORIZATION,
                description = "JWT OAuth token",
                required = true,
                `in` = ParameterIn.HEADER
            )
        ]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Recipe was created",
                content = [Content(schema = Schema(implementation = RecipeCreated::class))]
            ),
            ApiResponse(responseCode = "403", description = "Not authorized"),
            ApiResponse(responseCode = "400", description = "Invalid recipe form")
        ]
    )
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
    @Operation(summary = "gets a list of recipe overviews")
    fun recipes(): List<RecipeOverview> {
        return repository.all()
    }

    @GetMapping("{id}")
    @Operation(summary = "gets a concrete recipe, including steps and ingredients")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "The recipe"),
            ApiResponse(responseCode = "404", description = "Recipe with the given id not found")

        ]
    )
    fun recipe(
            @Parameter(description = "id of the recipe to fetch", required = true)
            @PathVariable
            id: Int): ResponseEntity<RecipeDetails> {
        return when (val result = repository.find(id)) {
            is Either.Left -> ResponseEntity.status(result.value).build()
            is Either.Right -> ResponseEntity.ok(result.value)
        }
    }
}
