package rs.raf.projekat2.jovan_babic_rn3018.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "saved")
data class SavedRecipeEntity(
    @PrimaryKey(autoGenerate = true) val room_id: Int,
    val id: String,
    val title: String,
    val publisher: String,
    val recipe_id: String,
    val category: String,
    val imageLink: String,
    val date: Date
)
