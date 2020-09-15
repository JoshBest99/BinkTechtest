package uk.co.joshuabest.binktest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Categories(
    @SerializedName("categories")
    val categories: ArrayList<Category>?
)

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

data class Meals(
    @SerializedName("meals")
    val meals: ArrayList<Meal>?
)

data class Meal(
    @SerializedName("strMeal")
    val mealName: String?,
    @SerializedName("strMealThumb")
    val mealThumbnail: String?,
    @SerializedName("idMeal")
    val mealId: String?
) : Serializable

data class MealDataParent(
    @SerializedName("meals")
    val meals: ArrayList<MealData>?
)

data class MealData(
    @SerializedName("idMeal")
    val mealId: Int,
    @SerializedName("strMeal")
    val mealName: String,
    @SerializedName("strCategory")
    val mealCategory: String,
    @SerializedName("strArea")
    val mealArea: String,
    @SerializedName("strInstructions")
    val mealInstructions: String,
    @SerializedName("strTags")
    val mealTags: String,
    @SerializedName("strYoutube")
    val mealYoutube: String
)