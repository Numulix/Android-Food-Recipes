package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.states

import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe

sealed class RecipeState {
    object Loading: RecipeState()
    object DataFetched: RecipeState()
    data class Success(val recipes: List<Recipe>): RecipeState()
    data class Error(val message: String): RecipeState()
}
