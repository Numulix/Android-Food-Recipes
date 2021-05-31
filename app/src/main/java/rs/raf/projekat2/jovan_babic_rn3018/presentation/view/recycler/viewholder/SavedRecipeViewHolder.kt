package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.databinding.SavedRecipeItemBinding
import java.text.SimpleDateFormat
import java.util.*

class SavedRecipeViewHolder(
        private val itemBinding: SavedRecipeItemBinding,
        details: (Int) -> Unit
): RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemBinding.root.setOnClickListener { details(bindingAdapterPosition) }
    }

    fun bind(recipe: Recipe) {
        itemBinding.savedRecipeTitle.text = recipe.title
        itemBinding.savedRecipeCategory.text = recipe.category
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
        val date: Date = recipe.date
        itemBinding.savedRecipeDate.text = dateFormatter.format(date)
    }

}