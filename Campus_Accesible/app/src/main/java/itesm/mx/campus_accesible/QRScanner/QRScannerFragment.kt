package itesm.mx.campus_accesible.QRScanner

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import itesm.mx.campus_accesible.R

class QRScannerFragment : Fragment() {

    private var mListener: QRScannerDetectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qrscanner, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is QRScannerDetectedListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement QRScannerDetectedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface QRScannerDetectedListener {
        fun qrScannerDetected(content: String)
    }

    companion object {

        fun newInstance(): QRScannerFragment {
            val fragment = QRScannerFragment()
            return fragment
        }
    }
}// Required empty public constructor
