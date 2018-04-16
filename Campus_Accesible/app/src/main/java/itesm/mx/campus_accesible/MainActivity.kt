package itesm.mx.campus_accesible

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import itesm.mx.campus_accesible.game.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GameFragmentListener {

    override fun goToMainMenu() {
        replaceFragment(GameStartFragment.newInstance())
    }

    override fun restartGame() {
        replaceFragment(GamePlayFragment.newInstance())
    }

    override fun gameOver(score: Int) {
        replaceFragment(GameOverFragment.newInstance(score))
    }

    override fun startGame() {
        replaceFragment(GamePlayFragment.newInstance())
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                replaceFragment(GameStartFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun replaceFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, frag).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val mapFragment = Fragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, mapFragment).commit()
    }
}
