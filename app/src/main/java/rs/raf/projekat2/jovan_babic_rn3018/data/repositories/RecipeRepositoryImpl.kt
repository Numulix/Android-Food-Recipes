package rs.raf.projekat2.jovan_babic_rn3018.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.jovan_babic_rn3018.data.datasource.local.RecipeDao
import rs.raf.projekat2.jovan_babic_rn3018.data.datasource.remote.RecipeService
import rs.raf.projekat2.jovan_babic_rn3018.data.models.*
import timber.log.Timber

class RecipeRepositoryImpl(
        private val localDataSource: RecipeDao,
        private val remoteDataSource: RecipeService
): RecipeRepository {

    override fun getPage(recipe: String, page: String): Observable<Resource<Unit>> {
        return remoteDataSource
                .getPage(recipe, page)
                .doOnNext { it ->
                    Timber.e("[Getting Page]")
                    val recipes = it.recipes.map {
                        RecipeEntity(
                                id = it._id,
                                title = it.title,
                                socialRank = it.social_rank,
                                imageUrl = it.image_url,
                                page = page,
                                recipe_id = it.recipe_id,
                                publisher = it.publisher
                        )
                    }
                    Timber.e("DELETE AND INSERT ALL")
                    localDataSource.deleteAndInsertAll(recipes)
                }
                .map {
                    Resource.Success(Unit)
                }
    }

    override fun getRecipes(recipe: String): Observable<List<Recipe>> {
        return localDataSource
                .getRecipes()
                .map { it ->
                    it.map {
                        Recipe(
                                id = it.id,
                                page = it.page,
                                title = it.title,
                                imageUrl = it.imageUrl,
                                socialRank = it.socialRank,
                                publisher = it.publisher,
                                recipe_id = it.recipe_id,
                                ingredients = listOf()
                        )
                    }
                }
    }

    override fun saveRecipe(recipe: SavedRecipeEntity): Completable {
        return localDataSource
                .saveRecipe(recipe)
    }

    override fun getSavedRecipes(): Observable<List<Recipe>> {
        return localDataSource
                .getSavedRecipes()
                .map {
                    it.map {
                        Recipe(
                                id = it.id,
                                title = it.title,
                                publisher = it.publisher,
                                recipe_id = it.recipe_id,
                                category = it.category,
                                date = it.date,
                                ingredients = listOf()
                        )
                    }
                }
    }

    override fun fetchIngredients(recipe_id: String): Observable<Resource<Unit>> {
        return remoteDataSource
                .getIngredients(recipe_id)
                .doOnNext {
                    Timber.e("[Getting ingredients]")

                    val ingredientsResponse = it.recipe
                    val ingredientsEntity = IngredientsEntity(
                            id = ingredientsResponse._id,
                            ingredients = ingredientsResponse.ingredients,
                            recipe_id = ingredientsResponse.recipe_id
                    )
                    localDataSource.insertIngredient(ingredientsEntity).blockingAwait()
                }
                .map {
                    Resource.Success(Unit)
                }
    }

    override fun getIngredients(recipe_id: String): Observable<Ingredients> {
        Timber.e("[Getting ingredients]")
        return localDataSource
                .getIngredientsForRecipe(recipe_id)
                .map {
                    Ingredients(
                            id = it.id,
                            ingredients = it.ingredients,
                            recipe_id = it.recipe_id
                    )
                }
    }

    override fun deleteRecipes(): Completable {
        return localDataSource.deleteAll()
    }


}