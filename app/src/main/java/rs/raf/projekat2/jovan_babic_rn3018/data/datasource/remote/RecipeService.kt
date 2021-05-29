package rs.raf.projekat2.jovan_babic_rn3018.data.datasource.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat2.jovan_babic_rn3018.data.models.IngredientsResponse
import rs.raf.projekat2.jovan_babic_rn3018.data.models.RecipeListResponse

interface RecipeService {

    @GET("search?")
    fun getPage(@Query("q") recipe: String, @Query("page") page: String): Observable<RecipeListResponse>

    @GET("get")
    fun getIngredients(@Query("rId") recipe_id: String): Observable<IngredientsResponse>

}