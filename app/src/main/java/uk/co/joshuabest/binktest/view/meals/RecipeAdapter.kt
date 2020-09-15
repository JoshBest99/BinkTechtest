package uk.co.joshuabest.binktest.view.meals

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uk.co.joshuabest.binktest.model.Meal
import java.util.ArrayList

class RecipeAdapter(fm: FragmentManager, private val meals: ArrayList<Meal>) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return meals.size
    }

    override fun getItem(position: Int): Fragment {
        val meal = meals[position]
        return RecipeFragment.newInstance(
            meal
        )
    }


}