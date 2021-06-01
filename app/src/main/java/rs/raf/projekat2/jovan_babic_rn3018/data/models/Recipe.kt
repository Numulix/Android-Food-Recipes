package rs.raf.projekat2.jovan_babic_rn3018.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Recipe(
        val id: String,
        val page: String = "",
        val title: String,
        var imageUrl: String = "",
        val socialRank: String = "",
        val publisher: String = "",
        val recipe_id: String,
        val ingredients: List<String>,
        val category: String = "",
        val date: Date = Date()
): Parcelable