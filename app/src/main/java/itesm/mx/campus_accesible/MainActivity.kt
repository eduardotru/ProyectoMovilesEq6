/*
    Campus Accesible. Map with accessible routes inside ITESM Monterrey.
	Copyright (C) 2018 - ITESM

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package itesm.mx.campus_accesible


import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import itesm.mx.campus_accesible.game.*
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.vision.barcode.Barcode
import itesm.mx.campus_accesible.DB.DatabaseInitializer
import itesm.mx.campus_accesible.Mapa.MapFragment
import itesm.mx.campus_accesible.Mapa.Punto
import itesm.mx.campus_accesible.QRScanner.QRScannerFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.view.View
import itesm.mx.campus_accesible.Creditos.CreditsFragment
import itesm.mx.campus_accesible.DB.AppDatabase
import itesm.mx.campus_accesible.Mapa.Edge
import itesm.mx.campus_accesible.QRScanner.QRScannerListener

import itesm.mx.campus_accesible.Edificios.DetalleFragment
import itesm.mx.campus_accesible.Edificios.Edificio
import itesm.mx.campus_accesible.ReportarError.ReportarErrorFragment

class MainActivity : AppCompatActivity(), MapFragment.OnFragmentInteractionListener, GameFragmentListener,
BottomNavigationView.OnNavigationItemSelectedListener, AppDatabase.DatabaseDelegate, QRScannerListener,
        CreditsFragment.CreditsListener, ReportarErrorFragment.ReportarErrorListener {


    private var lastFragment: String? = null

    private var mDb: AppDatabase? = null
    private lateinit var mDrawerLayout: DrawerLayout
    private var hashmapEdificios = HashMap <String, Edificio>()

    override fun goToMainMenu() {
        replaceFragment(GameStartFragment.newInstance(), "StartFragment")
    }

    override fun restartGame() {
        replaceFragment(GamePlayFragment.newInstance(), "PlayFragment")
    }

    override fun gameOver(score: Int, timer: Int) {
        replaceFragment(GameOverFragment.newInstance(score, timer), "GameOver")
    }

    override fun startGame() {
        replaceFragment(GamePlayFragment.newInstance(), "PlayFragment")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val frag = supportFragmentManager.findFragmentById(R.id.fragment_container)
        when (item.itemId) {
            R.id.navigation_bug -> {
                if(!(frag is  ReportarErrorFragment)) {
                    replaceFragment(ReportarErrorFragment.newInstance(), "ErrorFragment")
                }
                return true
            }
            R.id.navigation_map -> {
                if(!(frag is MapFragment)) {
                    var puntos = ArrayList<Punto>(mDb!!.puntoModel().all)
                    var edges = ArrayList<Edge>(mDb!!.puntoModel().allEdges)
                    val map_fragment = MapFragment.newInstance(puntos,edges)
                    replaceFragment(map_fragment, "MapFragment")
                }
                return true
            }
            R.id.navigation_game -> {
                if(!(frag is GameStartFragment) && !(frag is GamePlayFragment) && !(frag is GameOverFragment)) {
                    replaceFragment(GameStartFragment.newInstance(), "StartFragment")
                }
                return true
            }
        }
        return false
    }


    override fun onFragmentInteraction(arr: DoubleArray?) {
        val longitude = arr!!.get(0)
        val latitude = arr!!.get(1)

    }

    fun replaceFragment(frag: Fragment, tag: String) {
        lastFragment = tag
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, frag, tag).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById(R.id.drawer_layout);

        val navigationView = findViewById <NavigationView>(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            mDrawerLayout.closeDrawers()

            val edificio = hashmapEdificios[menuItem.title]

            if(menuItem.title == "Créditos") {
                replaceFragment(CreditsFragment.newInstance(), "CreditsFragment")
            }
            else if (edificio != null)
            {
                val frag = DetalleFragment.newInstance(edificio)
                replaceFragment(frag, "DetailFragment")
            }

            true
        }


        mDrawerLayout.addDrawerListener(
                object : DrawerLayout.DrawerListener {

                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                        // Respond when the drawer's position changes
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        // Respond when the drawer is opened
                    }

                    override fun onDrawerClosed(drawerView: View) {
                        // Respond when the drawer is closed
                    }

                    override fun onDrawerStateChanged(newState: Int) {
                        // Respond when the drawer motion state changes
                    }
                }
        )

        setSupportActionBar(my_toolbar)
        navigation.setOnNavigationItemSelectedListener(this)

        mDb = AppDatabase.getInstance(applicationContext)
        populateDB()

        val menu = navigationView.menu
        val listEdificios = mDb?.puntoModel()?.allEdificios
        for (edificio in listEdificios!!) {
            menu.add(edificio.nombre)
            hashmapEdificios[edificio.nombre] = edificio
        }
        menu.add("Créditos")
        if(savedInstanceState != null && savedInstanceState.getBoolean("boolLastFrag")) {
            lastFragment = savedInstanceState.getString("LastFragment")
        } else {
            navigation.selectedItemId = R.id.navigation_map
        }
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)

    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if(lastFragment != null) {
            outState?.putBoolean("boolLastFrag", true)
            outState?.putString("LastFragment", lastFragment)
        } else {
            outState?.putBoolean("boolLastFrag", false)
        }
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
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun openQRScanner() {
        val qrfrag = QRScannerFragment.newInstance()
        replaceFragment(qrfrag, "QRFragment")
    }

    override fun qrScannerDetected(barcode: Barcode) {
        val name = barcode.rawValue
        val edificio = hashmapEdificios[name]
        if(barcode.valueFormat == Barcode.TEXT && edificio != null) {
            replaceFragment(DetalleFragment.newInstance(edificio), "DetailFragment")
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


        val prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            DatabaseInitializer.populate(mDb!!, this)
            prefs.edit().putBoolean("firstrun", false).apply()
        }

    }


    override fun fetchDestination(longitude: Double, latitude: Double): ArrayList<Punto> {
        return ArrayList<Punto>(mDb!!.puntoModel().getDestination(longitude, latitude))
    }


    override fun reportarDatos() {
        enviarCorreo("Error en Datos de la aplicación Android", "Error encontrado: ")
    }

    override fun reportarRuta() {
        enviarCorreo("Error en la Ruta de la aplicación Android", "Error encontrado: ")

    }

    private fun enviarCorreo(asunto: String , texto: String) {
        val to = arrayOf(resources.getString(R.string.email_recipient))
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.setData(Uri.parse("mailto:"))
        emailIntent.setType("text/plain")

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto)
        emailIntent.putExtra(Intent.EXTRA_TEXT, texto)

        try {
            startActivity(Intent.createChooser(emailIntent, "Seleccione una aplicación "))
            finish()
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "No se pudo enviar el correo", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(frag is MapFragment) {
            super.onBackPressed()
        } else {
            navigation.selectedItemId = R.id.navigation_map
        }
    }

}
