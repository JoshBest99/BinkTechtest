package uk.co.joshuabest.binktest.view.meals

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_categories.pb_loading
import kotlinx.android.synthetic.main.activity_meals.*
import uk.co.joshuabest.binktest.R
import uk.co.joshuabest.binktest.util.popToast
import uk.co.joshuabest.binktest.viewmodel.RecipeViewModel

class RecipeView : AppCompatActivity() {

    lateinit var recipeViewModel: RecipeViewModel
    private var mealAmount: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        recipeViewModel.refresh(intent.getStringExtra("categoryName"))

        vp_meals.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                mealAmount?.let {
                    tv_recipe_count.text = "Recipe: ${position + 1}/$it"
                }

            }

        })

        observeViewModel()
    }

    private fun observeViewModel() {

        recipeViewModel.meals.observe(this, Observer { meals ->
            meals?.let {
                vp_meals.visibility = View.VISIBLE
                meals.meals?.let {
                    vp_meals.adapter =
                        RecipeAdapter(
                            supportFragmentManager,
                            it
                        )
                }
            }

            popToast(this, "Swipe left for more recipes!")
        })

        recipeViewModel.mealAmount.observe(this, Observer { mealTotal ->
            mealTotal?.let {
                mealAmount = mealTotal
                tv_recipe_count.text = "Recipe: ${vp_meals.currentItem + 1}/$it"
            }
        })

        recipeViewModel.mealsLoadError.observe(this, Observer { isError ->
            isError?.let {
                tv_meals_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        recipeViewModel.isLoadingMeals.observe(this, Observer { isLoading ->
            isLoading?.let {
                pb_loading.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tv_meals_error.visibility = View.GONE
                }
            }
        })
    }


}