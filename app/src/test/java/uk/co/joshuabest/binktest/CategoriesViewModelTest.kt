package uk.co.joshuabest.binktest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import uk.co.joshuabest.binktest.model.Categories
import uk.co.joshuabest.binktest.model.Category
import uk.co.joshuabest.binktest.model.MealService
import uk.co.joshuabest.binktest.viewmodel.CategoriesViewModel
import java.util.concurrent.TimeUnit

class CategoriesViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var mealService: MealService

    @InjectMocks
    var categoriesViewModel = CategoriesViewModel()

    private var testSingle: Single<Categories>? = null

    @Test
    fun getCategoriesListSuccess() {
        val categories = Categories(arrayListOf(Category("1", "Food", "foodpng", "Tasty Food")))

        testSingle = Single.just(categories)

        Mockito.`when`(mealService.getMealCategories()).thenReturn(testSingle)

        categoriesViewModel.refresh()

        Assert.assertEquals(1, categoriesViewModel.categories.value?.categories?.size)
        Assert.assertEquals(false, categoriesViewModel.isLoadingCategories.value)
        Assert.assertEquals(false, categoriesViewModel.categoriesLoadError.value)
    }

    @Test
    fun getCategoriesListFail() {
        testSingle = Single.error(Throwable())

        Mockito.`when`(mealService.getMealCategories()).thenReturn(testSingle)

        categoriesViewModel.refresh()

        Assert.assertEquals(null, categoriesViewModel.categories.value?.categories?.size)
        Assert.assertEquals(false, categoriesViewModel.isLoadingCategories.value)
        Assert.assertEquals(true, categoriesViewModel.categoriesLoadError.value)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setupRx() {
        val immediateScheduler = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker { it.run() }
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler {
            immediateScheduler
        }
        RxJavaPlugins.setInitComputationSchedulerHandler {
            immediateScheduler
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler {
            immediateScheduler
        }
        RxJavaPlugins.setInitSingleSchedulerHandler {
            immediateScheduler
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            immediateScheduler
        }
    }

}