package uk.co.joshuabest.binktest.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("categories.php")
    fun getMealCategories(): Single<Categories>

    @GET("filter.php")
    fun getRecipesForCategory(
        @Query("c") categoryName : String
    ): Single<Meals>

    @GET("lookup.php")
    fun getMealForId(
        @Query("i") mealId : String
    ): Single<MealDataParent>
}
