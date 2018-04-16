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
class GamePlayFragment : Fragment(), AdapterView.OnItemClickListener {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: GameFragmentListener? = null

    private var firstCardIndex: Int? = null
    private var firstCardView: View? = null
    private var firstCardText: String? = null
    private var score = 0
    private var solvedCards: ArrayList<String> = ArrayList<String>()
    private var tvScore: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            firstCardText = arguments!!.getString("firstCardText", null)
            score = arguments!!.getInt("score", 0)
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

        val c = gridView.adapter.count

        gridView.onItemClickListener = this

        tvScore = viewCreated.findViewById<TextView>(R.id.tv_score)
        tvScore!!.text = score.toString()

        return viewCreated
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
                    (gridView.adapter as CardAdapter).taken[p2] = true
                    score++
                    tvScore!!.text = score.toString()
                    solvedCards.add(cardSelected)
                    // Update the score text.
                    view!!.findViewById<TextView>(R.id.tv_score)
                } else {
                    (gridView.adapter as CardAdapter).taken[firstCardIndex!!] = false
                    (gridView.adapter as CardAdapter).taken[p2] = false
                    val firstImageView = firstCardView!!.findViewById<ImageView>(R.id.ivCard)
                    val firstTextContent = firstCardView!!.findViewById<TextView>(R.id.desc)
                    firstImageView.setImageResource(R.drawable.card_hidden)
                    firstTextContent.visibility = INVISIBLE

                    imageView.setImageResource(R.drawable.card_hidden)
                    textContent.visibility = INVISIBLE
                }
                firstCardText = null
                firstCardView = null
            }
        }

        if (score == gridView.adapter.count / 2) {
            // The game is over.
            mListener!!.gameOver(score);
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // If the orientation changes.
        outState.putString("firstCardText", firstCardText)
        outState.putInt("score", score)
        outState.putStringArrayList("solvedCards", solvedCards)
    }
}// Required empty public constructor
