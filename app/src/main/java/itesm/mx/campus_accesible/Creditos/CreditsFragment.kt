package itesm.mx.campus_accesible.Creditos

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import itesm.mx.campus_accesible.R

class CreditsFragment : Fragment() {

    private var mListener: CreditsListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credits, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is CreditsListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement CreditsListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface CreditsListener {
    }

    companion object {
        fun newInstance(): CreditsFragment {
            val fragment = CreditsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
