package uk.co.joshuabest.binktest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_recipe.*
import uk.co.joshuabest.binktest.R
import uk.co.joshuabest.binktest.model.Meal

class RecipeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments == null) return

        val meal = arguments!!.getSerializable("meal") as Meal
        tv_name.text = meal.mealName

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