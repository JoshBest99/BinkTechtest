package uk.co.joshuabest.binktest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import uk.co.joshuabest.binktest.R
import uk.co.joshuabest.binktest.model.Category
import uk.co.joshuabest.binktest.util.loadImage


class CategoriesListAdapter(var categories: ArrayList<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.CategoriesViewHolder>() {

    fun updateCategories(newCategories: List<Category>){
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoriesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
    )

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val thumbnail = view.iv_thumbnail
        private val categoryName = view.tv_category_name
        private val categoryDesc = view.tv_category_description

        fun bind(category: Category) {
            categoryName.text = category.categoryName
            categoryDesc.text = if(category.categoryDescription?.length!! > 75){
                "${category.categoryDescription.substring(0, 75)}..."
            } else {
                category.categoryDescription
            }

            thumbnail.loadImage(category.categoryThumbnail)
        }
    }

}