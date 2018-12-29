package queuro.jegumi.es.queuro.analytics

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
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
            categoryList[operation.area].value += operation.value!!
            categoryList[operation.area].operations++
            categoryList[operation.area].percentage =
                    ((100 * categoryList[operation.area].operations) / operations.size).toDouble()
        }

        loadChart(categoryList)
        return categoryList
    }

    private fun loadChart(categoryList: List<Category>) {
        any_chart_view.setProgressBar(progress_bar)

        val cartesian = AnyChart.column()
        val data = ArrayList<ValueDataEntry>()

        for (category in categoryList) {
            var name = category.name
            if (name.length > 8) {
                name = name.substring(0, 8) + "."
            }
            data.add(ValueDataEntry(name, category.value))
        }

        val column = cartesian.column(data as List<DataEntry>?)
        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("\${%Value}{groupsSeparator: }")

        cartesian.animation(true)
        cartesian.yScale().minimum(0.0)
        cartesian.yAxis(0).labels().format("\${%Value}{groupsSeparator: }")
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        any_chart_view.setChart(cartesian)
    }

}