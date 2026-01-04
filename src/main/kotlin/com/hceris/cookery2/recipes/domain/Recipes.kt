package com.hceris.cookery2.recipes.domain

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Recipes : IntIdTable(name = "recipes") {
    val name = varchar("name", 255).uniqueIndex()
    val servings = integer("servings")

    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }
}

class Recipe(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Recipe>(Recipes)

    var name by Recipes.name
    var servings by Recipes.servings

    var createdAt by Recipes.createdAt
    var updatedAt by Recipes.updatedAt

    val ingredients by Ingredient referrersOn Ingredients.recipe
    val steps by Step referrersOn Steps.recipe
}
