package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.databinding.SavedRecipeItemBinding
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.diff.SavedRecipeDiffItemCallback
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.viewholder.SavedRecipeViewHolder

class SavedRecipeAdapter(
        private val details: (Recipe) -> Unit
): ListAdapter<Recipe, SavedRecipeViewHolder>(SavedRecipeDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipeViewHolder {
        val itemBinding = SavedRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedRecipeViewHolder(itemBinding) {
            details(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: SavedRecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}