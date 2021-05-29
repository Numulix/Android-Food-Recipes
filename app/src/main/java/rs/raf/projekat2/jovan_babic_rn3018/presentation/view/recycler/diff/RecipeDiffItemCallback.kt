package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe

class RecipeDiffItemCallback: DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
                && oldItem.title == newItem.title
                && oldItem.socialRank == newItem.socialRank
                && oldItem.publisher == newItem.publisher
    }

}