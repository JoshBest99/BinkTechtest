package uk.co.joshuabest.binktest.di

import dagger.Component
import uk.co.joshuabest.binktest.model.MealService
import uk.co.joshuabest.binktest.viewmodel.CategoriesViewModel


@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(mealService: MealService)

    fun inject(categoriesViewModel: CategoriesViewModel)

}