package uk.co.joshuabest.binktest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import uk.co.joshuabest.binktest.di.DaggerApiComponent
import uk.co.joshuabest.binktest.model.MealService
import uk.co.joshuabest.binktest.model.Meals
import javax.inject.Inject

class RecipeViewModel : ViewModel() {

    @Inject
    lateinit var mealService: MealService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val meals = MutableLiveData<Meals>()
    val mealsLoadError = MutableLiveData<Boolean>()
    val isLoadingMeals = MutableLiveData<Boolean>()

    fun refresh(categoryName: String) {
        fetchCountries(categoryName)
    }

    private fun fetchCountries(categoryName: String) {
        isLoadingMeals.value = true
        disposable.add(
            mealService.getRecipesForCategory(categoryName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Meals>() {
                    override fun onSuccess(value: Meals?) {
                        meals.value = value
                        mealsLoadError.value = false
                        isLoadingMeals.value = false
                    }

                    override fun onError(e: Throwable?) {
                        mealsLoadError.value = true
                        isLoadingMeals.value = false
                    }
                })
        )

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}