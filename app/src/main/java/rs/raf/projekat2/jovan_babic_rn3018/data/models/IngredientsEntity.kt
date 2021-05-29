package rs.raf.projekat2.jovan_babic_rn3018.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientsEntity(
    @PrimaryKey val id: String,
    val ingredients: List<String>,
    val recipe_id: String
)
