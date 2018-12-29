package queuro.jegumi.es.queuro

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.vicpin.krealmextensions.*
import io.realm.Sort
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.profile_header.*
import kotlinx.android.synthetic.main.queuro_card.*
import queuro.jegumi.es.queuro.adapter.OperationsAdapter
import queuro.jegumi.es.queuro.model.Operation
import queuro.jegumi.es.queuro.model.User

class HomeFragment : Fragment() {

    private var user: User? = null
    private var operationList: List<Operation> = ArrayList()

    companion object {

        fun newInstance(): HomeFragment {
            val fragmentHome = HomeFragment()
            val args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUser()
        initCard()
        initToolbar()
    }

    private fun initUser() {
        user = User().queryFirst()
        if (user == null) {
            user = User()
            user?.name = "Jesús"
            user?.surname = "Gumiel"
            user?.card_number = "4242 4242 4242 4242"
            user?.card_valid = "10/20"
            user?.dni = "123456789G"
            user?.create()

            val operation = Operation()
            operation.id = 1
            operation.value = 40.25
            operation.name = "Restaurante Meco"
            operation.date = "23 dic. 10:06"
            operation.type = 0
            operation.area = 1
            operation.save()

            val operation1 = Operation()
            operation1.id = 2
            operation1.value = 50.69
            operation1.name = "Supermercado Dia"
            operation1.date = "24 dic. 16:045"
            operation1.type = 0
            operation1.area = 0
            operation1.save()

            val operation2 = Operation()
            operation2.id = 3
            operation2.value = 1.2
            operation2.name = "Bar Paco"
            operation2.date = "25 dic. 12:20"
            operation2.type = 0
            operation2.area = 1
            operation2.save()

            val operation3 = Operation()
            operation3.id = 4
            operation3.value = 2.5
            operation3.name = "Panaderia Toni"
            operation3.date = "26 dic. 20:08"
            operation3.type = 0
            operation3.area = 0
            operation3.save()

            val operation4 = Operation()
            operation4.id = 5
            operation4.value = 5.3
            operation4.name = "Supermercado Recio"
            operation4.date = "27 dic. 14:02"
            operation4.type = 0
            operation4.area = 0
            operation4.save()

            val operation5 = Operation()
            operation5.id = 6
            operation5.value = 3.4
            operation5.name = "Churreria Adela"
            operation5.date = "27 dic. 09:04"
            operation5.type = 0
            operation5.area = 1
            operation5.save()

            val operation6 = Operation()
            operation6.id = 7
            operation6.value = 7.3
            operation6.name = "Librería Paua"
            operation6.date = "27 dic. 11:15"
            operation6.type = 0
            operation6.area = 2
            operation6.save()

            val operation7 = Operation()
            operation7.id = 8
            operation7.value = 1.2
            operation7.name = "Bar Paco"
            operation7.date = "28 dic. 13:30"
            operation7.type = 0
            operation7.area = 1
            operation7.save()

            val operation8 = Operation()
            operation8.id = 9
            operation8.value = 6.9
            operation8.name = "Restaurante Antonio"
            operation8.date = "28 dic. 14:13"
            operation8.type = 0
            operation8.area = 1
            operation8.save()

            val operation9 = Operation()
            operation9.id = 10
            operation9.value = 1.2
            operation9.name = "Bar Paco"
            operation9.type = 0
            operation9.area = 1
            operation9.date = "29 dic. 09:01"
            operation9.save()
        }

        var incomes = 500.0
        var expenses = 0.0
        operationList = Operation().querySorted("date", Sort.DESCENDING)
        for (operation in operationList) {
            if (operation.type == 0) {
                expenses += operation.value!!
            } else {
                incomes += operation.value!!
            }
        }

        expenses_value_text_view.text = formatDoubleValue(expenses)
        incomes_value_text_view.text = formatDoubleValue(incomes)
        balance_text_view.text = formatDoubleValue(incomes - expenses)

        expenses_bar.progress = ((100 * expenses) / incomes).toInt()
        operations_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        operations_recycler_view.adapter = OperationsAdapter(operationList)
    }

    private fun initCard() {
        card_name_text_view.text = user?.getFullName()
        card_number_text_view.text = user?.card_number
        card_valid_text_view.text = user?.card_valid

        card_image_view.setImageBitmap(getQrCode(user?.card_number!!))
        sliding_layout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {

            }

            override fun onPanelStateChanged(panel: View, previousState: PanelState, newState: PanelState) {
                if (newState == PanelState.EXPANDED) {
                    card_slide_message_text_view.text = getString(R.string.card_slide_back_message)
                } else if (newState == PanelState.COLLAPSED) {
                    card_slide_message_text_view.text = getString(R.string.card_slide_message)
                }
            }
        })
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(main_item_toolbar)
    }

    private fun formatDoubleValue(value: Double): String {
        return "%.2f".format(value) + "€"
    }

    @Throws(WriterException::class)
    private fun getQrCode(Value: String): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                500, 500, null
            )

        } catch (Illegalargumentexception: IllegalArgumentException) {

            return null
        }

        val bitMatrixWidth = bitMatrix.width
        val bitMatrixHeight = bitMatrix.height
        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.colorBlack)
                else
                    resources.getColor(R.color.colorWhite)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

        bitmap.setPixels(pixels, 0, bitMatrixWidth, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }
}
