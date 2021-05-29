package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.databinding.RecipeItemBinding
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.diff.RecipeDiffItemCallback
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.viewholder.RecipeViewHolder

class RecipeAdapter(private val details: (Recipe) -> Unit): ListAdapter<Recipe, RecipeViewHolder>(RecipeDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding) {
            details(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}