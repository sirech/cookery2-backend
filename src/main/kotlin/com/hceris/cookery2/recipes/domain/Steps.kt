package com.hceris.cookery2.recipes.domain

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Steps : IntIdTable(name = "steps") {
    val description = text("description")
    val duration = integer("duration")

    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    val recipe = reference("recipe_id", Recipes)
}

class Step(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Step>(Steps)

    var description by Steps.description
    var duration by Steps.duration

    var createdAt by Recipes.createdAt
    var updatedAt by Recipes.updatedAt

    var recipe by Recipe referencedOn Steps.recipe
}
