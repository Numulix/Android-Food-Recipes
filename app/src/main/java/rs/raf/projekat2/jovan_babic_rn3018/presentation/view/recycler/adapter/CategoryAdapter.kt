package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Category
import rs.raf.projekat2.jovan_babic_rn3018.databinding.CategoryItemBinding
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.diff.CategoryDiffItemCallback
import rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.viewholder.CategoryViewHolder

class CategoryAdapter(private val search: (Category) -> Unit): ListAdapter<Category, CategoryViewHolder>(CategoryDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding) {
            search(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}