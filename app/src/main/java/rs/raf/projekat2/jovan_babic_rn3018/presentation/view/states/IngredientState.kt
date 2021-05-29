package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.states

import rs.raf.projekat2.jovan_babic_rn3018.data.models.Ingredients

sealed class IngredientState {
    object Loading: IngredientState()
    object DataFetched: IngredientState()
    data class Success(val ingredients: Ingredients): IngredientState()
    data class Error(val message: String): IngredientState()
}
