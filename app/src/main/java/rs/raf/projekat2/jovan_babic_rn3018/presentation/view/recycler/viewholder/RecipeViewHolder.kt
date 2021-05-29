package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe
import rs.raf.projekat2.jovan_babic_rn3018.databinding.RecipeItemBinding

class RecipeViewHolder(
        private val itemBinding: RecipeItemBinding,
        details: (Int) -> Unit
): RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemBinding.root.setOnClickListener { details(bindingAdapterPosition) }
    }

    fun bind(recipe: Recipe) {
        Glide
                .with(itemBinding.root.context)
                .load(recipe.imageUrl)
                .into(itemBinding.recipeImage)
        itemBinding.recipeTitleTv.text = recipe.title
        itemBinding.recipePublisherTv.text = recipe.publisher
        itemBinding.recipeSRankTv.text = recipe.socialRank
    }

}