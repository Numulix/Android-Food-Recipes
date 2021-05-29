package rs.raf.projekat2.jovan_babic_rn3018.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Resource
import rs.raf.projekat2.jovan_babic_rn3018.data.models.SavedRecipeEntity
import rs.raf.projekat2.jovan_babic_rn3018.data.repositories.RecipeRepository
import rs.raf.projekat2.jovan_babic_rn3018.presentation.contract.RecipeContract
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.states.IngredientState
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.states.RecipeState
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class RecipeViewModel(
        private val recipeRepository: RecipeRepository
): ViewModel(), RecipeContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val recipeState: MutableLiveData<RecipeState> = MutableLiveData()
    override val savedRecipeState: MutableLiveData<RecipeState> = MutableLiveData()
    override val ingredientState: MutableLiveData<IngredientState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
                //.debounce(200, TimeUnit.MILLISECONDS)
                //.distinctUntilChanged()
                .switchMap {
                    recipeRepository
                            .getRecipes(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnError {
                                Timber.e("Publish Subject Error")
                            }
                }
                .subscribe(
                        {
                            recipeState.value = RecipeState.Success(it)
                        },
                        {
                            recipeState.value = RecipeState.Error("Error getting data from db")
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getRecipesPage(recipe: String, page: String) {
        val subscription = recipeRepository
                .getPage(recipe, page)
                .startWith(Resource.Loading())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            when(it) {
                                is Resource.Loading -> recipeState.value = RecipeState.Loading
                                is Resource.Success -> recipeState.value = RecipeState.DataFetched
                                is Resource.Error -> recipeState.value = RecipeState.Error("Error getting data")
                            }
                        },
                        {
                            recipeState.value = RecipeState.Error("Error getting data")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getRecipes(filter: String) {
        publishSubject.onNext(filter)
    }

    override fun deleteRecipes() {
        val subscription = recipeRepository
                .deleteRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.e("Deleted")
                        },
                        {
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun fetchIngredients(recipe_id: String) {
        val subscription = recipeRepository
                .fetchIngredients(recipe_id)
                .startWith(Resource.Loading())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            when(it) {
                                is Resource.Loading -> ingredientState.value = IngredientState.Loading
                                is Resource.Success -> ingredientState.value = IngredientState.DataFetched
                                is Resource.Error -> ingredientState.value = IngredientState.Error("Error fetching data")
                            }
                        },
                        {
                            ingredientState.value = IngredientState.Error("Error fetching data")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getIngredients(recipe_id: String) {
        val subscription = recipeRepository
                .getIngredients(recipe_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            ingredientState.value = IngredientState.Success(it)
                        },
                        {
                            ingredientState.value = IngredientState.Error("Error getting data from db")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getSavedRecipes() {
        val subscription = recipeRepository
                .getSavedRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            savedRecipeState.value = RecipeState.Success(it)
                        },
                        {
                            savedRecipeState.value = RecipeState.Error("Error geetting data from db")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun saveRecipe(recipe: Recipe, category: String, date: Date) {
        val subscription = recipeRepository
                .saveRecipe(
                        SavedRecipeEntity(
                                room_id = 0,
                                id = recipe.id,
                                title = recipe.title,
                                publisher = recipe.publisher,
                                recipe_id = recipe.recipe_id,
                                category = category,
                                date = date
                        )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.e("Saved")
                        },
                        {
                            savedRecipeState.value = RecipeState.Error("Error updating db")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }


    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}