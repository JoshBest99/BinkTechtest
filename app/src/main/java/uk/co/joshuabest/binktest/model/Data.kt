package uk.co.joshuabest.binktest.model

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("categories")
    val categories: ArrayList<Category>?)

data class Category(
    @SerializedName("idCategory")
    val categoryId: String?,
    @SerializedName("strCategory")
    val categoryName: String?,
    @SerializedName("strCategoryThumb")
    val categoryThumbnail: String?,
    @SerializedName("strCategoryDescription")
    val categoryDescription: String?
)