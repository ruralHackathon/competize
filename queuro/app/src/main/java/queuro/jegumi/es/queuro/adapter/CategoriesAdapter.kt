package queuro.jegumi.es.queuro.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import queuro.jegumi.es.queuro.R
import queuro.jegumi.es.queuro.extensions.inflate
import queuro.jegumi.es.queuro.model.Category
import queuro.jegumi.es.queuro.model.Operation

class CategoriesAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoriesAdapter.OperationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationHolder {
        val inflatedView = parent.inflate(R.layout.category_item, false)
        return OperationHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onBindViewHolder(holder: OperationHolder, position: Int) {
        holder.bindCategory(categories[position])
    }

    inner class OperationHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var nameTextView: TextView = itemView.findViewById(R.id.operation_name_text_view)
        private var descTextView: TextView = itemView.findViewById(R.id.operation_desc_text_view)
        private var thumbImageView: ImageView = itemView.findViewById(R.id.operation_image_view)
        private var valueTextView: TextView = itemView.findViewById(R.id.operation_value_text_view)
        private var percentageTextView: TextView = itemView.findViewById(R.id.operation_percentage_text_view)
        private var context = v.context
        private var category: Category? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            /*    val intent = Intent(context, BusinessItemActivity::class.java)
                intent.putExtra("operation", operation)
                context.startActivity(intent)*/
        }

        fun bindCategory(category: Category) {
            this.category = category
            nameTextView.text = category.name
            descTextView.text = category.operations.toString() + " operaciones"
            percentageTextView.text = formatDoubleValue(category.percentage) + "%"
            valueTextView.text = formatDoubleValue(category.value)+ "€"
            thumbImageView.setImageResource(getDrawable(category.area))
        }
    }

    private fun getDrawable(type: Int): Int {
        return when (type) {
            0 -> R.drawable.supermarket
            1 -> R.drawable.restaurants
            2 -> R.drawable.shops
            3 -> R.drawable.transport
            4 -> R.drawable.bank
            else -> R.drawable.transport

        }
    }

    private fun formatDoubleValue(value: Double): String {
        return "%.2f".format(value)
    }
}

