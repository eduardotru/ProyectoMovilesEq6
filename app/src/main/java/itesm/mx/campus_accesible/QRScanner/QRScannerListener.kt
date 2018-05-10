package itesm.mx.campus_accesible.QRScanner

import com.google.android.gms.vision.barcode.Barcode

/**
 * Created by LuisErick on 5/6/2018.
 */
interface QRScannerListener {
    fun qrScannerDetected(barcode: Barcode)
    fun requestCameraPermission()
}