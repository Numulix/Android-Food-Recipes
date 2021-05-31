package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.jovan_babic_rn3018.R
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.databinding.ActivitySaveRecipeBinding
import rs.raf.projekat2.jovan_babic_rn3018.presentation.contract.RecipeContract
import rs.raf.projekat2.jovan_babic_rn3018.presentation.viewmodel.RecipeViewModel
import java.text.SimpleDateFormat
import java.util.*

class SaveRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveRecipeBinding
    private val recipeViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var recipe: Recipe
    private lateinit var savedDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recipe = intent.getParcelableExtra("RECIPE")!!
        init()
    }

    private fun init() {
        initUi()
        initListeners()
    }

    private fun initUi() {
        Glide
            .with(binding.root.context)
            .load(recipe.imageUrl)
            .into(binding.savedRecipeImageIv)
        binding.savedRecipeTitleTv.text = recipe.title
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        savedDate = date
        binding.savedRecipeDateBtn.text = dateFormatter.format(date)

        val spinner = binding.savedRecipeSpinner
        ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
    }

    private fun initListeners() {
        binding.addToMenuBtn.setOnClickListener {
            val cat = binding.savedRecipeSpinner.selectedItem.toString()
            recipeViewModel.saveRecipe(recipe, cat, savedDate)
            Toast.makeText(this, "Recipe has been saved successfully", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.savedRecipeDateBtn.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                    savedDate = calendar.time
                    binding.savedRecipeDateBtn.text = dateFormatter.format(savedDate)
                }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }



}