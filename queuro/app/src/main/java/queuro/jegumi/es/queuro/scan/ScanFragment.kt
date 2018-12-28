package queuro.jegumi.es.queuro.scan

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.vicpin.krealmextensions.queryLast
import com.vicpin.krealmextensions.save
import kotlinx.android.synthetic.main.scan_layout.*
import queuro.jegumi.es.queuro.R
import queuro.jegumi.es.queuro.model.Operation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ScanFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner

    val MY_PERMISSIONS_REQUEST_CAMERA = 120

    companion object {

        fun newInstance(): ScanFragment {
            val scanFragment = ScanFragment()
            val args = Bundle()
            scanFragment.arguments = args
            return scanFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.scan_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA)
            }
        } else {
            initScan()
        }
    }

    private fun initScan() {
        codeScanner = CodeScanner(activity, scanner_view)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                val array = it.text.split(";")
                val operation = Operation().queryLast()
                val operationNew = Operation()
                operationNew.id = operation!!.id + 1
                operationNew.name = array[0]
                operationNew.value = array[1].toDouble()
                operationNew.type = array[2].toInt()
                operationNew.area = array[3].toInt()

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                operationNew.date = current.format(formatter)
                operationNew.save()

                Toast.makeText(activity, "Pagados " + array[1] + "€ en " + array[0], Toast.LENGTH_LONG).show()
            }
        }
        codeScanner.startPreview()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initScan()
            }
        }
    }
}