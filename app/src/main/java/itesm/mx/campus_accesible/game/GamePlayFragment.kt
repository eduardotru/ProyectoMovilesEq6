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
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView

import itesm.mx.campus_accesible.R
import kotlinx.android.synthetic.main.fragment_game_play.*
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GamePlayFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GamePlayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GamePlayFragment : Fragment(), AdapterView.OnItemClickListener, GameTimerDelegate {

    private var mListener: GameFragmentListener? = null

    private var firstCardIndex: Int? = null
    private var firstCardView: View? = null
    private var firstCardText: String? = null
    private var score = 0
    private var solvedCards: ArrayList<String> = ArrayList<String>()
    private var tvScore: TextView? = null
    private var tvTimer: TextView? = null
    private var timer = 0
    private var fixedGameTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            firstCardText = arguments!!.getString("firstCardText", null)
            score = arguments!!.getInt("score", 0)
            timer = arguments!!.getInt("timer", 0)
            if (arguments!!.getStringArrayList("solvedCards") != null) {
                solvedCards = arguments!!.getStringArrayList("solvedCards")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val viewCreated = inflater.inflate(R.layout.fragment_game_play, container, false)

        val gridView = viewCreated.findViewById<GridView>(R.id.gridView)
        gridView.adapter = CardAdapter(this.activity!!)

        gridView.onItemClickListener = this

        tvScore = viewCreated.findViewById<TextView>(R.id.tv_score)
        tvScore!!.text = "Cartas: " + score.toString() + "/5"

        tvTimer = viewCreated.findViewById<TextView>(R.id.tv_timer)
        tvTimer!!.text = "Tiempo: 00:00"

        return viewCreated
    }

    override fun onPause() {
        super.onPause()

        if (fixedGameTimer != null) {
            fixedGameTimer!!.cancel()
        }
    }

    override fun onResume() {
        super.onResume()

        if (fixedGameTimer == null) {
            fixedGameTimer = fixedRateTimer(name = "game-timer",
                    initialDelay = 1000, period = 1000) {
                val delegate: GameTimerDelegate = this@GamePlayFragment
                delegate.countTimer()
            }
        }
    }

    override fun countTimer() {
        if (timer < 3599) {
            timer++
            val seconds = timer % 60
            val minutes: Int = (timer / 60) % 60
            val secondsString = if (seconds < 10) "0${seconds}" else "${seconds}"
            val minutesString = if (minutes < 10) "0${minutes}" else "${minutes}"
            tvTimer!!.text = "Tiempo: ${minutesString}:${secondsString}"
        } else {
            mListener!!.gameOver(score, timer)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is GameFragmentListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance(): GamePlayFragment {
            val fragment = GamePlayFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val cardSelected = gridView.adapter.getItem(p2) as String
        if (solvedCards.indexOf(cardSelected) == -1) {
            val imageView = p1!!.findViewById<ImageView>(R.id.ivCard)
            imageView.setImageResource(R.drawable.card)
            val textContent = p1.findViewById<TextView>(R.id.desc)
            textContent.visibility = VISIBLE
            if (firstCardText == null) {
                firstCardIndex = p2
                (gridView.adapter as CardAdapter).taken[p2] = true
                firstCardText = cardSelected
                firstCardView = p1
            } else {
                if (cardSelected == firstCardText) {
                    if (firstCardIndex != p2) {
                        (gridView.adapter as CardAdapter).taken[p2] = true
                        score++
                        tvScore!!.text = "Cartas: " + score.toString() + "/5"
                        solvedCards.add(cardSelected)
                        // Update the score text.
                        view!!.findViewById<TextView>(R.id.tv_score)

                        firstCardText = null
                        firstCardView = null
                    }
                } else {
                    (gridView.adapter as CardAdapter).taken[firstCardIndex!!] = false
                    (gridView.adapter as CardAdapter).taken[p2] = false
                    val firstImageView = firstCardView!!.findViewById<ImageView>(R.id.ivCard)
                    val firstTextContent = firstCardView!!.findViewById<TextView>(R.id.desc)
                    firstImageView.setImageResource(R.drawable.card_hidden)
                    firstTextContent.visibility = INVISIBLE

                    imageView.setImageResource(R.drawable.card_hidden)
                    textContent.visibility = INVISIBLE

                    firstCardText = null
                    firstCardView = null
                }
            }
        }

        if (score == gridView.adapter.count / 2) {
            // The game is over.
            mListener!!.gameOver(score, timer)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // If the orientation changes.
        outState.putString("firstCardText", firstCardText)
        outState.putInt("score", score)
        outState.putInt("timer", timer)
        outState.putStringArrayList("solvedCards", solvedCards)
    }


}// Required empty public constructor
