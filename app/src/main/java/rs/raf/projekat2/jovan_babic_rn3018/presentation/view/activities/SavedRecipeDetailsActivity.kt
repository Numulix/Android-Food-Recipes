package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.activities

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.databinding.ActivitySavedRecipeDetailsBinding
import rs.raf.projekat2.jovan_babic_rn3018.presentation.contract.RecipeContract
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.states.IngredientState
import rs.raf.projekat2.jovan_babic_rn3018.presentation.viewmodel.RecipeViewModel
import timber.log.Timber
import java.io.FileInputStream

class SavedRecipeDetailsActivity : AppCompatActivity() {

    private val recipeViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var binding: ActivitySavedRecipeDetailsBinding
    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recipe = intent.getParcelableExtra("RECIPE")!!
        recipeViewModel.getIngredients(recipe.recipe_id)
        recipeViewModel.fetchIngredients(recipe.recipe_id)
        init()
    }

    private fun init() {
        initUi()
        initObserver()
    }

    private fun initUi() {
        if (recipe.imageUrl.startsWith("http")) {
            Glide
                    .with(binding.root.context)
                    .load(recipe.imageUrl)
                    .into(binding.recipeDetailsIv)
        } else {
            val b = BitmapFactory.decodeStream(FileInputStream(recipe.imageUrl))
            Glide
                    .with(binding.root.context)
                    .load(b)
                    .into(binding.recipeDetailsIv)
        }

        binding.recipeDetailsTitleTv.text = recipe.title
        binding.recipeDetailsSRankTv.text = recipe.socialRank
    }

    private fun initObserver() {
        recipeViewModel.ingredientState.observe(this, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: IngredientState) {
        when (state) {
            is IngredientState.Success -> {
                Timber.e("Success")
                val ingredients = state.ingredients
                for (ingredient in ingredients.ingredients) {
                    binding.ingredientsTv.text = binding.ingredientsTv.text.toString() + "\n" + ingredient
                }
            }
            is IngredientState.Error -> {
                Timber.e("Error")
            }
            is IngredientState.DataFetched -> {
                Timber.e("Data fetched")
            }
            is IngredientState.Loading -> {
                Timber.e("Loading")
            }
        }
    }

}