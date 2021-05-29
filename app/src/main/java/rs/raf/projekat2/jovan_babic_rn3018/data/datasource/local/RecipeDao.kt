package rs.raf.projekat2.jovan_babic_rn3018.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.jovan_babic_rn3018.data.models.IngredientsEntity
import rs.raf.projekat2.jovan_babic_rn3018.data.models.RecipeEntity
import rs.raf.projekat2.jovan_babic_rn3018.data.models.SavedRecipeEntity

@Dao
abstract class RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<RecipeEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveRecipe(recipe: SavedRecipeEntity): Completable

    @Query("SELECT * FROM saved")
    abstract fun getSavedRecipes(): Observable<List<SavedRecipeEntity>>

    @Query("SELECT * FROM recipe")
    abstract fun getRecipes(): Observable<List<RecipeEntity>>

    @Query("DELETE FROM recipe")
    abstract fun deleteAll(): Completable

    @Query("SELECT * FROM ingredients WHERE recipe_id = :recipe_id")
    abstract fun getIngredientsForRecipe(recipe_id: String): Observable<IngredientsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertIngredient(ingredient: IngredientsEntity): Completable

    open fun deleteAndInsertAll(entities: List<RecipeEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

}