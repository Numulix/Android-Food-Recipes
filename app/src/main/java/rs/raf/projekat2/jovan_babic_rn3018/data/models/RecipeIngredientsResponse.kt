package rs.raf.projekat2.jovan_babic_rn3018.data.models

import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeIngredientsResponse(
    val _id: String,
    val ingredients: List<String>,
    @PrimaryKey val recipe_id: String
)
