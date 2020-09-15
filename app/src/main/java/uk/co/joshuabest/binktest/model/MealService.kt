package uk.co.joshuabest.binktest.model

import io.reactivex.Single
import uk.co.joshuabest.binktest.di.DaggerApiComponent
import javax.inject.Inject

class MealService {

    @Inject
    lateinit var mealApi: MealApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getMealCategories() : Single<Categories> {
        return mealApi.getMealCategories()
    }

}