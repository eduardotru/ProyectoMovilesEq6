package itesm.mx.campus_accesible.game

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView

import itesm.mx.campus_accesible.R
import kotlinx.android.synthetic.main.fragment_game_play.*

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

    private var mListener: OnFragmentInteractionListener? = null

    private var firstCardText: String? = null
    private var score = 0
    private var solvedCards: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            firstCardText = arguments!!.getString("firstCardText")
            score = arguments!!.getInt("score")
            solvedCards = arguments!!.getStringArrayList("solvedCards")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val viewCreated = inflater.inflate(R.layout.fragment_game_play, container, false)

        val gridView = viewCreated.findViewById<GridView>(R.id.gridView)
        gridView.adapter = CardAdapter(this.activity!!)

        gridView.onItemClickListener = this

        return viewCreated
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
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
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GamePlayFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): GamePlayFragment {
            val fragment = GamePlayFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val cardSelected = gridView.adapter.getItem(p2) as String
        if (solvedCards.indexOf(cardSelected) == -1) {
            if (firstCardText == null) {
                firstCardText = cardSelected
            } else {
                if (cardSelected == firstCardText) {
                    score++
                    solvedCards.add(cardSelected)
                    // Update the score text.
                    view!!.findViewById<TextView>(R.id.tv_score)
                } else {
                    firstCardText = null
                }
            }
        }

        if (score == gridView.adapter.count) {
            // The game is over.
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
