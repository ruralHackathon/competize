package queuro.jegumi.es.queuro.analytics

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.vicpin.krealmextensions.queryAll
import kotlinx.android.synthetic.main.analytics_fragment.*
import queuro.jegumi.es.queuro.R
import queuro.jegumi.es.queuro.adapter.CategoriesAdapter
import queuro.jegumi.es.queuro.model.Category
import queuro.jegumi.es.queuro.model.Operation

class AnalyticsFragment : Fragment() {

    companion object {

        fun newInstance(): AnalyticsFragment {
            val analyticsFragment = AnalyticsFragment()
            val args = Bundle()
            analyticsFragment.arguments = args
            return analyticsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.analytics_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        analytics_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        analytics_recycler_view.adapter = CategoriesAdapter(getCategories())
    }

    private fun getCategories(): List<Category> {
        val categoryList: ArrayList<Category> = ArrayList()
        categoryList.add(Category("Restaurantes", 0.0, 0.0, 0, 0))
        categoryList.add(Category("Supermercados", 0.0, 0.0, 0, 1))
        categoryList.add(Category("Compras", 0.0, 0.0, 0, 2))
        categoryList.add(Category("Transporte", 0.0, 0.0, 0, 3))
        categoryList.add(Category("Bancos", 0.0, 0.0, 0, 4))

        val operations = Operation().queryAll()
        for (operation in operations) {
            categoryList.get(operation.area).value += operation.value!!
            categoryList.get(operation.area).operations++
            categoryList.get(operation.area).percentage =
                    ((100 * categoryList.get(operation.area).operations) / operations.size).toDouble()
        }

        val categoryListFinal: ArrayList<Category> = ArrayList()
        for (category in categoryList) {
            if (category.value > 0.0) {
                categoryListFinal.add(category)
            }
        }

        return categoryListFinal
    }
}