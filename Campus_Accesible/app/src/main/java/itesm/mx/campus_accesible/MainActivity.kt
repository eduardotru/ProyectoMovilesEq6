package itesm.mx.campus_accesible

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.vision.barcode.Barcode
import itesm.mx.campus_accesible.QRScanner.QRScannerFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest
import android.net.Uri
import android.support.design.widget.NavigationView
import com.google.android.gms.maps.SupportMapFragment
import itesm.mx.campus_accesible.Mapa.AppDatabase
import itesm.mx.campus_accesible.Mapa.DatabaseInitializer
import itesm.mx.campus_accesible.Mapa.Punto
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), MapFragment.OnFragmentInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener, AppDatabase.DatabaseDelegate, QRScannerFragment.QRScannerListener {

    private var mDb: AppDatabase? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                startMapFragment()
                return true
            }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, MapActivity::class.java);
                startActivity(intent);
                return true
            }
            R.id.navigation_notifications -> {
                return true
            }
        }
        return false
    }

    fun startMapFragment() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mDb = AppDatabase.getInstance(applicationContext)
        populateDB()

        val puntos = ArrayList<Punto>(mDb!!.puntoModel().all)
        val mapFragment = MapFragment.newInstance(puntos);

        mapFragment.getMapAsync(this)
    }

    override fun onFragmentInteraction(uri: Uri?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun replaceFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, frag).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        setSupportActionBar(my_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_qr_scanner -> {
                openQRScanner()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun openQRScanner() {
        val qrfrag = QRScannerFragment.newInstance()
        replaceFragment(qrfrag)
    }

    override fun qrScannerDetected(barcode: Barcode) {
        if(barcode.valueFormat == Barcode.TEXT) {
            Toast.makeText(this, barcode.displayValue, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.barcode_failed, Toast.LENGTH_SHORT).show()
        }
    }

    override fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA) {
            if(grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openQRScanner()
            } else {
                Toast.makeText(this, R.string.camera_permission_failed, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    companion object {
        val REQUEST_CAMERA = 1

    }

    private fun populateDB() {
        DatabaseInitializer.populate(mDb!!)
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    override fun fetchDestination(longitude: Double, latitude: Double): ArrayList<Punto> {
        return ArrayList<Punto>(mDb!!.puntoModel().getDestination(longitude, latitude))
    }
}
