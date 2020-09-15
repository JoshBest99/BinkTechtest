package uk.co.joshuabest.binktest.view.categories

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_categories.*
import uk.co.joshuabest.binktest.R
import uk.co.joshuabest.binktest.viewmodel.CategoriesViewModel

class CategoriesView : AppCompatActivity() {

    lateinit var categoriesViewModel: CategoriesViewModel
    private val categoriesListAdapter =
        CategoriesListAdapter(
            arrayListOf()
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        categoriesViewModel.refresh()

        rv_categories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesListAdapter
        }

        swl_refresh.setOnRefreshListener {
            swl_refresh.isRefreshing = false
            categoriesViewModel.refresh()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        categoriesViewModel.categories.observe(this, Observer { categories ->
            categories?.let {
                rv_categories.visibility = View.VISIBLE
                categories.categories?.let { categoriesListAdapter.updateCategories(it) }
            }
        })
        categoriesViewModel.categoriesLoadError.observe(this, Observer { isError ->
            isError?.let {
                tv_categories_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        categoriesViewModel.isLoadingCategories.observe(this, Observer { isLoading ->
            isLoading?.let {
                pb_loading.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tv_categories_error.visibility = View.GONE
                    rv_categories.visibility = View.GONE
                }
            }
        })
    }


}