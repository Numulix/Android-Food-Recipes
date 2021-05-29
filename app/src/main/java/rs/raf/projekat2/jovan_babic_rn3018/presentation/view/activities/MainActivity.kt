package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import rs.raf.projekat2.jovan_babic_rn3018.R
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Category
import rs.raf.projekat2.jovan_babic_rn3018.databinding.ActivityMainBinding
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.diff.CategoryDiffItemCallback

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

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initRecycler()
    }

    private fun initRecycler() {
        binding.categoryRv.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter() {

        }
        binding.categoryRv.adapter = categoryAdapter
        categoryAdapter.submitList(categories)
    }
}