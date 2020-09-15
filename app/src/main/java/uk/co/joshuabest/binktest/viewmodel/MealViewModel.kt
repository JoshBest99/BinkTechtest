package uk.co.joshuabest.binktest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import uk.co.joshuabest.binktest.di.DaggerApiComponent
import uk.co.joshuabest.binktest.model.MealDataParent
import uk.co.joshuabest.binktest.model.MealService
import javax.inject.Inject

class MealViewModel : ViewModel() {

    @Inject
    lateinit var mealService: MealService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val mealData = MutableLiveData<MealDataParent>()
    val mealsLoadError = MutableLiveData<Boolean>()
    val isLoadingMealsData = MutableLiveData<Boolean>()

    fun refresh(mealId: String) {
        fetchMealDetails(mealId)
    }

    private fun fetchMealDetails(mealId: String) {
        isLoadingMealsData.value = true
        disposable.add(
            mealService.getMealForId(mealId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MealDataParent>() {
                    override fun onSuccess(value: MealDataParent?) {
                        mealData.value = value
                        mealsLoadError.value = false
                        isLoadingMealsData.value = false
                    }

                    override fun onError(e: Throwable?) {
                        mealsLoadError.value = true
                        isLoadingMealsData.value = false
                    }
                })
        )

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}