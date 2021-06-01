package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.activities

import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.jovan_babic_rn3018.R
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.databinding.ActivitySaveRecipeBinding
import rs.raf.projekat2.jovan_babic_rn3018.presentation.contract.RecipeContract
import rs.raf.projekat2.jovan_babic_rn3018.presentation.viewmodel.RecipeViewModel
import timber.log.Timber
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class SaveRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveRecipeBinding
    private val recipeViewModel: RecipeContract.ViewModel by viewModel<RecipeViewModel>()
    private lateinit var recipe: Recipe
    private lateinit var savedDate: Date
    private val getPicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val bundle = it.data?.extras
            val finalPhoto: Bitmap = bundle?.get("data") as Bitmap
            val path: String? = saveToInternalStorage(finalPhoto, recipe.recipe_id)
            recipe.imageUrl = path + "/" + recipe.recipe_id + ".jpg"
            if (path != null) {
                loadImageFromStorage(path, recipe.recipe_id)
            }
        }
    }

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

        binding.savedRecipeImageIv.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                getPicture.launch(takePictureIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Camera app not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap, fileName: String): String? {
        val cw = ContextWrapper(applicationContext)
        val dir: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val myPath = File(dir, "$fileName.jpg")
        var fos: FileOutputStream? = null

        try {
            fos = FileOutputStream(myPath)
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return dir.absolutePath
    }

    private fun loadImageFromStorage(path: String, fileName: String) {
        try {
            val f = File(path, "$fileName.jpg")
            Timber.e(f.absolutePath)
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            Glide
                    .with(this)
                    .load(b)
                    .into(binding.savedRecipeImageIv)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }


}