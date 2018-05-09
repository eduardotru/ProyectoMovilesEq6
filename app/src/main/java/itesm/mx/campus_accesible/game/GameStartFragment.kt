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
 * [GameStartFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GameStartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameStartFragment : Fragment(), View.OnClickListener {

    override fun onClick(p0: View?) {
        mListener!!.startGame()
    }

    private var mListener: GameFragmentListener? = null

    val PREFS_FILENAME = "itesm.mx.campus_accesible.prefs"
    val HIGH_SCORE = "high_score"
    var prefs: SharedPreferences? = null

    var high_score: Int = 3600

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_start, container, false)

        prefs = this.activity?.getSharedPreferences(PREFS_FILENAME, 0)
        if (prefs != null) {
            high_score = prefs!!.getInt(HIGH_SCORE, 3600)
            var tvHighscore: TextView = view!!.findViewById<TextView>(R.id.tv_highscore)

            val seconds = high_score % 60
            val minutes = (high_score / 60) % 60
            val secondsString = if (seconds < 10) "0${seconds}" else "${seconds}"
            val minutesString = if (minutes < 10) "0${minutes}" else "${minutes}"
            val high_score_text = "${minutesString}:${secondsString}"
            tvHighscore.setText(high_score_text)
        }

        val buttonStart = view!!.findViewById<Button>(R.id.btn_game_start)
        buttonStart.setOnClickListener(this)

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is GameFragmentListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnGameFragmentListener")
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
        fun newInstance(): GameStartFragment {
            val fragment = GameStartFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
