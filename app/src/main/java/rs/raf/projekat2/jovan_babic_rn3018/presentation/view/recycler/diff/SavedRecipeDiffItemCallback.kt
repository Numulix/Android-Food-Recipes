package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Recipe

class SavedRecipeDiffItemCallback: DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.title == newItem.title
                && oldItem.category == newItem.category
                && oldItem.date == newItem.date
    }
}