package queuro.jegumi.es.queuro

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.create

import kotlinx.android.synthetic.main.queuro_card.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.profile_header.*
import queuro.jegumi.es.queuro.model.User

class HomeFragment : Fragment() {

    private var user: User? = null

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
            user?.card_valid = "04/10/2020"
            user?.dni = "123456789G"
            user?.create()
        }
        profile_name_text_view.text = user?.getFullName()
    }

    private fun initCard() {
        card_name_text_view.text = user?.getFullName()
        card_dni_text_view.text = user?.dni
        card_number_text_view.text = user?.card_number
        card_valid_text_view.text = user?.card_valid

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
}
