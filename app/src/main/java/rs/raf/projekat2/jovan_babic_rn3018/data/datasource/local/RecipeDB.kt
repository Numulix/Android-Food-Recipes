package rs.raf.projekat2.jovan_babic_rn3018.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat2.jovan_babic_rn3018.data.datasource.local.converters.DateConverter
import rs.raf.projekat2.jovan_babic_rn3018.data.datasource.local.converters.ListConverter
import rs.raf.projekat2.jovan_babic_rn3018.data.models.IngredientsEntity
import rs.raf.projekat2.jovan_babic_rn3018.data.models.RecipeEntity
import rs.raf.projekat2.jovan_babic_rn3018.data.models.SavedRecipeEntity


@Database(
        entities = [RecipeEntity::class, IngredientsEntity::class, SavedRecipeEntity::class],
        version = 11,
        exportSchema = false
)
@TypeConverters(DateConverter::class, ListConverter::class)
abstract class RecipeDB: RoomDatabase() {
    abstract fun getRecipeDao(): RecipeDao
}