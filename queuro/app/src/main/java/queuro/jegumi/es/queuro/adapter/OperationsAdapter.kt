package queuro.jegumi.es.queuro.adapter

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import queuro.jegumi.es.queuro.R
import queuro.jegumi.es.queuro.extensions.inflate
import queuro.jegumi.es.queuro.model.Operation

class OperationsAdapter(private val operations: List<Operation>) :
    RecyclerView.Adapter<OperationsAdapter.OperationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationHolder {
        val inflatedView = parent.inflate(R.layout.operation_item, false)
        return OperationHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return operations.count()
    }

    override fun onBindViewHolder(holder: OperationHolder, position: Int) {
        holder.bindOperation(operations[position])
    }

    inner class OperationHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var nameTextView: TextView = itemView.findViewById(R.id.operation_name_text_view)
        private var descTextView: TextView = itemView.findViewById(R.id.operation_desc_text_view)
        private var thumbImageView: ImageView = itemView.findViewById(R.id.operation_image_view)
        private var valueTextView: TextView = itemView.findViewById(R.id.operation_value_text_view)
        private var context = v.context
        private var operation: Operation? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            /*    val intent = Intent(context, BusinessItemActivity::class.java)
                intent.putExtra("operation", operation)
                context.startActivity(intent)*/
        }

        fun bindOperation(operation: Operation) {
            this.operation = operation
            nameTextView.text = operation.name
            descTextView.text = operation.date
            valueTextView.text = operation.value.toString() + "€"
            thumbImageView.setImageResource(getDrawable(operation.area))
        }
    }

    private fun getDrawable(type: Int): Int {
        return when (type) {
            1 -> R.drawable.supermarket
            0 -> R.drawable.restaurants
            2 -> R.drawable.shops
            3 -> R.drawable.transport
            4 -> R.drawable.bank
            else -> R.drawable.transport
        }
    }
}

