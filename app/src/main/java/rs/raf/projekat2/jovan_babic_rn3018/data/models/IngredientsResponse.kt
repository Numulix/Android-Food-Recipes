package rs.raf.projekat2.jovan_babic_rn3018.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientsResponse(
    val recipe: RecipeIngredientsResponse
)
