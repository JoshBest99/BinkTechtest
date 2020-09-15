package uk.co.joshuabest.binktest.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_categories.pb_loading
import kotlinx.android.synthetic.main.activity_meals.*
import uk.co.joshuabest.binktest.R
import uk.co.joshuabest.binktest.viewmodel.RecipeViewModel

class RecipeView : AppCompatActivity() {

    lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        recipeViewModel.refresh(intent.getStringExtra("categoryName"))

        observeViewModel()
    }

    private fun observeViewModel() {

        recipeViewModel.meals.observe(this, Observer { meals ->
            meals?.let {
                vp_meals.visibility = View.VISIBLE
                meals.meals?.let {
                    vp_meals.adapter = RecipeAdapter(supportFragmentManager, it)
                }
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