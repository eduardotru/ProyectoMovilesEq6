package itesm.mx.campus_accesible.QRScanner

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import itesm.mx.campus_accesible.R
import java.util.jar.Manifest

class QRScannerFragment : Fragment(), SurfaceHolder.Callback {

    private var mListener: QRScannerListener? = null
    private lateinit var cameraPreview: SurfaceView
    private lateinit var cameraSource: CameraSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_qrscanner, container, false)
        cameraPreview = view.findViewById(R.id.surface_view)
        createCameraSource()
        return view
    }

    private fun createCameraSource() {
        val barcodeDetector =  BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.QR_CODE).build()
        cameraSource = CameraSource.Builder(context, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600,1024)
                .build()

        cameraPreview.holder.addCallback(this)

        barcodeDetector.setProcessor(object: Detector.Processor<Barcode> {
            override fun release() {

            }
            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                val barcodes = detections?.detectedItems
                if (barcodes != null && barcodes.size() > 0) {
                    mListener?.qrScannerDetected(barcodes.valueAt(0))
                }
            }
        })
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        if(ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mListener?.requestCameraPermission()
        } else {
            cameraSource.start(cameraPreview.holder)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        //do nothing
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        cameraSource.stop()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is QRScannerListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement QRScannerListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {

        fun newInstance(): QRScannerFragment {
            val fragment = QRScannerFragment()
            return fragment
        }
    }
}// Required empty public constructor
