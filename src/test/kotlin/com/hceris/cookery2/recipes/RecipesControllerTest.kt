package com.hceris.cookery2.recipes

import arrow.core.left
import arrow.core.right
import com.hceris.cookery2.auth.Verifier
import com.hceris.cookery2.auth.JwtAuthorizationFilter
import com.hceris.cookery2.auth.SecurityConfiguration
import com.hceris.cookery2.recipes.presentation.RecipeDetails
import com.hceris.cookery2.recipes.presentation.RecipeOverview
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [RecipesController::class])
@Import(SecurityConfiguration::class, JwtAuthorizationFilter::class)
@TestPropertySource(properties = ["auth.enabled=true"])
internal class RecipesControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean(relaxed = true)
    private lateinit var repository: RecipeRepository

    @MockkBean
    private lateinit var verifier: Verifier

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

    val invalidJson = """
     {
          "name": "carbonara",
          "servings": -1,
          "steps": [],
          "ingredients": []
            
     }   
    """.trimIndent()

    @Test
    fun `creates recipe`() {
        every { repository.create(any()) } returns 1

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/recipes")
                .with(user("dudu").authorities(SimpleGrantedAuthority("create:recipes")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `creates throws error if invalid form was sent`() {
        every { repository.create(any()) } returns 1

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/recipes")
                .with(user("dudu").authorities(SimpleGrantedAuthority("create:recipes")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `create throws error if not authorized`() {
        every { repository.create(any()) } returns 1

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().`is`(403))
    }

    @Test
    fun `list recipes`() {
        every { repository.all() } returns listOf(RecipeOverview(1, "carbonara", 3, 25))

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/recipes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `gets one recipe`() {
        every { repository.find(1) } returns RecipeDetails(
                1,
                "carbonara",
                3,
                25,
                listOf(),
                listOf()
        ).right()

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/recipes/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `returns error if recipe is not found`() {
        every { repository.find(1) } returns 404.left()

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/recipes/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}
