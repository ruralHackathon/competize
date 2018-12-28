package queuro.jegumi.es.queuro.extensions

import android.os.Build
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

val esLocale = Locale("es", "ES")
val formatDate = SimpleDateFormat("yyyy-MM-dd", esLocale)
val localeFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, esLocale)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    try {
        val snack = Snackbar.make(this, message, length)
        val view = snack.view
        val textView: TextView = view.findViewById(android.support.design.R.id.snackbar_text)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        } else {
            textView.gravity = Gravity.CENTER_HORIZONTAL
        }
        snack.show()
    } catch (e: IllegalArgumentException) {
        //
    }
}