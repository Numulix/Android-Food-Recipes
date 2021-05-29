package rs.raf.projekat2.jovan_babic_rn3018.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeResponseItem(
    val image_url: String,
    val _id: String,
    val recipe_id: String,
    val social_rank: String,
    val publisher: String,
    val title: String
)