package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Category

class CategoryDiffItemCallback: DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.img == newItem.img && oldItem.title == newItem.title
    }

}