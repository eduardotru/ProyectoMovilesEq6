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

package itesm.mx.campus_accesible.game

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import itesm.mx.campus_accesible.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GameOverFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GameOverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameOverFragment : Fragment(), View.OnClickListener {

    val PREFS_FILENAME = "itesm.mx.campus_accesible.prefs"
    val HIGH_SCORE = "high_score"
    var prefs: SharedPreferences? = null

    var high_score: Int = 0

    private var mListener: GameFragmentListener? = null

    private var score = 0
    private var timer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            score = arguments!!.getInt("score",0)
            timer = arguments!!.getInt("timer",0)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)

        var seconds = timer % 60
        var minutes: Int = (timer / 60) % 60
        var secondsString = if (seconds < 10) "0${seconds}" else "${seconds}"
        var minutesString = if (minutes < 10) "0${minutes}" else "${minutes}"
        var resultsText = "Cartas: ${score}/5\n"
        resultsText = resultsText + "Tiempo: ${minutesString}:${secondsString}\n"

        prefs = this.activity?.getSharedPreferences(PREFS_FILENAME, 0)
        if (prefs != null) {
            high_score = prefs!!.getInt(HIGH_SCORE, 3600)
            if (timer < high_score!! && score == 5) {
                resultsText = resultsText + "¡Felicidades! ¡Nuevo Record!\n"
                high_score = timer
                prefs!!.edit().putInt(HIGH_SCORE, high_score!!).commit()
            } else {
                resultsText = resultsText + "¡Intenta otra vez!\n"
            }
        }

        seconds = high_score % 60
        minutes = (high_score / 60) % 60
        secondsString = if (seconds < 10) "0${seconds}" else "${seconds}"
        minutesString = if (minutes < 10) "0${minutes}" else "${minutes}"
        resultsText = resultsText + "Tiempo Record: ${minutesString}:${secondsString}"

        (view.findViewById<TextView>(R.id.results)).text = resultsText
        (view.findViewById<Button>(R.id.btn_try_again)).setOnClickListener(this)

        return view
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_try_again -> mListener!!.restartGame()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is GameFragmentListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement GameFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {

        fun newInstance(score: Int, timer: Int): GameOverFragment {
            val fragment = GameOverFragment()
            val args = Bundle()
            args.putInt("score", score)
            args.putInt("timer", timer)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
