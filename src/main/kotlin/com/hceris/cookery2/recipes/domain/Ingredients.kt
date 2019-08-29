package com.hceris.cookery2.recipes.domain

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Ingredients : IntIdTable(name = "ingredients") {
    val name = varchar("name", 255).uniqueIndex()
    val quantity = integer("quantity")
    val unit = varchar("unit", 8)

    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    val recipe = reference("recipe_id", Recipes)
}

class Ingredient(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Ingredient>(Ingredients)

    var name by Ingredients.name
    var quantity by Ingredients.quantity
    var unit by Ingredients.unit

    var createdAt by Recipes.createdAt
    var updatedAt by Recipes.updatedAt

    var recipe by Recipe referencedOn Ingredients.recipe
}
