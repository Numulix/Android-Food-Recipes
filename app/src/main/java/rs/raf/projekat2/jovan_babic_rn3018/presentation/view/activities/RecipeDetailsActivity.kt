package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.databinding.ActivityRecipeDetailsBinding
import rs.raf.projekat2.jovan_babic_rn3018.presentation.contract.RecipeContract
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.states.IngredientState
import rs.raf.projekat2.jovan_babic_rn3018.presentation.viewmodel.RecipeViewModel
import timber.log.Timber

class RecipeDetailsActivity : AppCompatActivity() {

    private val recipeViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var binding: ActivityRecipeDetailsBinding
    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recipe = intent.getParcelableExtra("RECIPE")!!
        recipeViewModel.getIngredients(recipe.recipe_id)
        recipeViewModel.fetchIngredients(recipe.recipe_id)
        init()
    }

    private fun init() {
        initUi()
        initObserver()
        initListeners()
    }

    private fun initUi() {
        Glide
            .with(binding.root.context)
            .load(recipe.imageUrl)
            .into(binding.recipeDetailsIv)
        binding.recipeDetailsTitleTv.text = recipe.title
        binding.recipeDetailsSRankTv.text = recipe.socialRank
    }

    private fun initObserver() {
        recipeViewModel.ingredientState.observe(this, Observer {
            renderState(it)
        })
    }

    private fun initListeners() {
        binding.saveRecipeBtn.setOnClickListener {
            val intent = Intent(this, SaveRecipeActivity::class.java)
            intent.putExtra("RECIPE", recipe)
            startActivity(intent)
        }
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