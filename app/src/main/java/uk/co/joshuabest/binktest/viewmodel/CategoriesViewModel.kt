package uk.co.joshuabest.binktest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import uk.co.joshuabest.binktest.di.DaggerApiComponent
import uk.co.joshuabest.binktest.model.Categories
import uk.co.joshuabest.binktest.model.MealService
import javax.inject.Inject

class CategoriesViewModel : ViewModel() {

    @Inject
    lateinit var mealService: MealService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val categories = MutableLiveData<Categories>()
    val categoriesLoadError = MutableLiveData<Boolean>()
    val isLoadingCategories = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        isLoadingCategories.value = true
        disposable.add(
            mealService.getMealCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Categories>() {
                    override fun onSuccess(value: Categories?) {
                        categories.value = value
                        categoriesLoadError.value = false
                        isLoadingCategories.value = false
                    }

                    override fun onError(e: Throwable?) {
                        categoriesLoadError.value = true
                        isLoadingCategories.value = false
                    }
                })
        )

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}