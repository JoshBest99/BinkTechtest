package uk.co.joshuabest.binktest.di

import dagger.Component
import uk.co.joshuabest.binktest.model.MealService


@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(mealService: MealService)

}