package itesm.mx.campus_accesible


import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.vision.barcode.Barcode
import itesm.mx.campus_accesible.Mapa.AppDatabase
import itesm.mx.campus_accesible.Mapa.DatabaseInitializer
import itesm.mx.campus_accesible.Mapa.MapFragment
import itesm.mx.campus_accesible.Mapa.Punto
import itesm.mx.campus_accesible.QRScanner.QRScannerFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), MapFragment.OnFragmentInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener, AppDatabase.DatabaseDelegate, QRScannerFragment.QRScannerListener {

    private var mDb: AppDatabase? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_bug -> {

                return true
            }
            R.id.navigation_map -> {
                var puntos = ArrayList<Punto>(mDb!!.puntoModel().all)
                val map_fragment = MapFragment.newInstance(puntos)
                replaceFragment(map_fragment)
                return true
            }
            R.id.navigation_game -> {
                return true
            }
        }
        return false
    }


    override fun onFragmentInteraction(arr: DoubleArray?) {
        val longitude = arr!!.get(0)
        val latitude = arr!!.get(1)

    }

    fun replaceFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, frag).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(my_toolbar)
        navigation.setOnNavigationItemSelectedListener(this)

        mDb = AppDatabase.getInstance(applicationContext)
        populateDB()
        navigation.selectedItemId = R.id.navigation_map
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


    override fun fetchDestination(longitude: Double, latitude: Double): ArrayList<Punto> {
        return ArrayList<Punto>(mDb!!.puntoModel().getDestination(longitude, latitude))
    }
}
