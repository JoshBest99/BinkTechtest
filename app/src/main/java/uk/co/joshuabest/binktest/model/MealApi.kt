package uk.co.joshuabest.binktest.model

import io.reactivex.Single
import retrofit2.http.GET

interface MealApi {

    @GET("categories.php")
    fun getMealCategories(): Single<Categories>

}
