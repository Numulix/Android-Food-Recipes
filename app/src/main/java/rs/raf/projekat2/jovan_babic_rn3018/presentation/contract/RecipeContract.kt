package rs.raf.projekat2.jovan_babic_rn3018.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.states.IngredientState
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.states.RecipeState
import java.util.*

interface RecipeContract {

    interface ViewModel {

        val recipeState: LiveData<RecipeState>
        val ingredientState: LiveData<IngredientState>
        val savedRecipeState: LiveData<RecipeState>

        fun getRecipesPage(recipe: String, page: String)
        fun getRecipes(filter: String)
        fun getSavedRecipes()
        fun saveRecipe(recipe: Recipe, category: String, date: Date)
        fun deleteRecipes()
        fun fetchIngredients(recipe_id: String)
        fun getIngredients(recipe_id: String)

    }
}