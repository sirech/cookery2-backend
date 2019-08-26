package com.hceris.cookery2.recipes

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [RecipesController::class])
internal class RecipesControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean(relaxed = true)
    private lateinit var repository: RecipeRepository

    val json = """
     {
          "name": "carbonara",
          "servings": 2,
          "steps": [
            {
              "description": "cook the pasta",
              "duration": 15
            },
            {
              "description": "fry the bacon",
              "duration": 5
            }
          ],
          "ingredients": [
            {
              "name": "pasta",
              "quantity": 300,
              "unit": "gr"
            },
            {
              "name": "guanciale",
              "quantity": 150,
              "unit": "gr"
            }
          ]
     }   
    """.trimIndent()

    @Test
    fun `creates recipe`() {
        every { repository.create(any()) } returns 1

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
    }
}