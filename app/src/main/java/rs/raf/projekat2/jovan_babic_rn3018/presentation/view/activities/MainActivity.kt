package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.jovan_babic_rn3018.R
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Category
import rs.raf.projekat2.jovan_babic_rn3018.databinding.ActivityMainBinding
import rs.raf.projekat2.jovan_babic_rn3018.presentation.contract.RecipeContract
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.adapter.RecipeAdapter
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.diff.CategoryDiffItemCallback
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.states.RecipeState
import rs.raf.projekat2.jovan_babic_rn3018.presentation.viewmodel.RecipeViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val categories: List<Category> = listOf(
        Category(
            "1",
            "https://image.flaticon.com/icons/png/512/908/908146.png",
            "Barbecue"
        ),
        Category(
            "2",
            "https://image.flaticon.com/icons/png/512/887/887359.png",
            "Breakfast"
        ),
        Category(
            "3",
            "https://image.flaticon.com/icons/png/512/1046/1046751.png",
            "Chicken"
        ),
        Category(
            "4",
            "https://image.flaticon.com/icons/png/512/1702/1702834.png",
            "Beef"
        ),
        Category(
            "5",
            "https://image.flaticon.com/icons/png/512/1814/1814186.png",
            "Brunch"
        ),
        Category(
            "6",
            "https://image.flaticon.com/icons/png/512/3778/3778570.png",
            "Dinner"
        ),
        Category(
            "7",
            "https://image.flaticon.com/icons/png/512/1425/1425570.png",
            "Wine"
        ),
        Category(
            "8",
            "https://image.flaticon.com/icons/png/512/776/776458.png",
            "Italian"
        )
    )

    private val recipeViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recipeAdapter: RecipeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initListeners()
        initRecycler()
        initObservers()
    }

    private fun initListeners() {
        binding.searchBt.setOnClickListener {
            val input = binding.searchEt.text.toString()
            if (input.isBlank()) {
                Toast.makeText(applicationContext, "Field empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.searchEt.text.clear()

            binding.categoryRv.visibility = View.GONE
            binding.recipeRv.visibility = View.VISIBLE
            recipeViewModel.deleteRecipes()
            recipeViewModel.getRecipes(input)
            recipeViewModel.getRecipesPage(input, "1")
        }
    }

    private fun initObservers() {
        recipeViewModel.recipeState.observe(this, Observer {
            renderState(it)
        })
    }

    private fun initRecycler() {
        binding.categoryRv.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter() {
            println(it.title)
            binding.categoryRv.visibility = View.GONE
            binding.recipeRv.visibility = View.VISIBLE
            recipeViewModel.deleteRecipes()
            recipeViewModel.getRecipes(it.title)
            recipeViewModel.getRecipesPage(it.title, "1")
        }
        binding.categoryRv.adapter = categoryAdapter
        categoryAdapter.submitList(categories)

        binding.recipeRv.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter() {
            println(it.title)
        }
        binding.recipeRv.adapter = recipeAdapter
    }

    private fun renderState(state: RecipeState) {
        when (state) {
            is RecipeState.Success -> {
                recipeAdapter.submitList(state.recipes)
            }
            is RecipeState.Error -> {
                Timber.e("Error")
            }
            is RecipeState.DataFetched -> {}
            is RecipeState.Loading -> {
                Timber.e("Loading")
            }
        }
    }
}