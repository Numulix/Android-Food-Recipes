package rs.raf.projekat2.jovan_babic_rn3018.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Ingredients
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Resource
import rs.raf.projekat2.jovan_babic_rn3018.data.models.SavedRecipeEntity

interface RecipeRepository {

    fun getPage(recipe: String, page: String): Observable<Resource<Unit>>
    fun getRecipes(recipe: String): Observable<List<Recipe>>
    fun saveRecipe(recipe: SavedRecipeEntity): Completable
    fun getSavedRecipes(): Observable<List<Recipe>>
    fun fetchIngredients(recipe_id: String): Observable<Resource<Unit>>
    fun getIngredients(recipe_id: String): Observable<Ingredients>
    fun deleteRecipes(): Completable

}