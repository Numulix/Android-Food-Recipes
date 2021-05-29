package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.projekat2.jovan_babic_rn3018.data.models.Category
import rs.raf.projekat2.jovan_babic_rn3018.databinding.CategoryItemBinding

class CategoryViewHolder(private val itemBinding: CategoryItemBinding,
                         search: (Int) -> Unit): RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemBinding.root.setOnClickListener { search(bindingAdapterPosition) }
    }

    fun bind(category: Category) {
        Glide
            .with(itemBinding.root.context)
            .load(category.img)
            .into(itemBinding.catImage)
        itemBinding.catTitle.text = category.title
    }

}