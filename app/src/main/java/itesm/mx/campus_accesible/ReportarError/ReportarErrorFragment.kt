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

package itesm.mx.campus_accesible.ReportarError

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import itesm.mx.campus_accesible.R


class ReportarErrorFragment : Fragment() {


    private var mListener: ReportarErrorListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_reportar_error, container, false)
        val btnErrorData = view.findViewById<Button>(R.id.btn_data)
        val btnErrorRuta = view.findViewById<Button>(R.id.btn_ruta)
        btnErrorData.setOnClickListener{
            mListener?.reportarDatos()
        }

        btnErrorRuta.setOnClickListener{
            mListener?.reportarRuta()
        }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ReportarErrorListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement ReportarErrorListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface ReportarErrorListener {
        fun reportarRuta()
        fun reportarDatos()

    }


    companion object {

        fun newInstance(): ReportarErrorFragment {
            val fragment = ReportarErrorFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
