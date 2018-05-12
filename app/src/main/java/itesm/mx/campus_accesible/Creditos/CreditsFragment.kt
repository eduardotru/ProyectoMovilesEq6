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
