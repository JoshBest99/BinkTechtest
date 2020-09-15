package uk.co.joshuabest.binktest.view.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_recipe.*
import uk.co.joshuabest.binktest.R
import uk.co.joshuabest.binktest.model.Meal
import uk.co.joshuabest.binktest.util.loadImage
import uk.co.joshuabest.binktest.viewmodel.MealViewModel

class RecipeFragment : Fragment() {

    lateinit var mealViewModel: MealViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments == null) return

        mealViewModel = ViewModelProviders.of(this).get(MealViewModel::class.java)

        val meal = arguments!!.getSerializable("meal") as Meal
        mealViewModel.refresh(meal.mealId ?: "")
        tv_meal_name.text = meal.mealName
        iv_thumbnail.loadImage(meal.mealThumbnail)

        observeViewModel()
    }

    private fun observeViewModel() {
        mealViewModel.mealData.observe(this, Observer { mealData ->
            mealData?.let {
                mealData.meals?.let {
                    li_data.visibility = View.VISIBLE
                    tv_meal_category.text = it[0].mealCategory
                    tv_meal_area.text = it[0].mealArea
                    tv_meal_instructions.text = it[0].mealInstructions
                }
            }
        })

        mealViewModel.mealsLoadError.observe(this, Observer { isError ->
            isError?.let {
                li_loading.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        mealViewModel.isLoadingMealsData.observe(this, Observer { isLoading ->
            isLoading?.let {
                li_loading.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    li_error.visibility = View.GONE
                }
            }
        })
    }

    companion object {
        fun newInstance(meal: Meal?): Fragment {
            val fragment = RecipeFragment()
            val args = Bundle()
            args.putSerializable("meal", meal)
            fragment.arguments = args
            return fragment
        }
    }

}