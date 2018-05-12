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
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import itesm.mx.campus_accesible.R
import java.util.*
import java.util.Collections.shuffle

/**
 * Created by LuisErick on 4/15/2018.
 */

class CardAdapter(context: Context) : BaseAdapter() {
    val context = context

    val textContents: ArrayList<String> = arrayListOf("Discapacidad Motriz: Movimiento y control del cuerpo",
            "Discapacidad Sensorial: Auditiva",
            "Discapacidad Sensorial: Visual",
            "Discapacidad Intelectual",
            "Discapacidad Psicosocial: Depresion, Esquizofrenia, etc.",
            "Discapacidad Motriz: Movimiento y control del cuerpo",
            "Discapacidad Sensorial: Auditiva",
            "Discapacidad Sensorial: Visual",
            "Discapacidad Intelectual",
            "Discapacidad Psicosocial: Depresion, Esquizofrenia, etc.")
    val taken: ArrayList<Boolean> = arrayListOf(false, false, false, false, false,
                                                false, false, false, false, false)

    init {
        Collections.shuffle(textContents)
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val cardView = LayoutInflater.from(context).inflate(R.layout.card, p2, false)
        val imageView = cardView.findViewById<ImageView>(R.id.ivCard)
        val cardDescription = cardView.findViewById<TextView>(R.id.desc)
        cardDescription.text = getItem(p0).toString()

        if (taken[p0]) {
            imageView.setImageResource(R.drawable.card)
            cardDescription.visibility = VISIBLE
        }

        return cardView
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return textContents.size
    }

    override fun getItem(p0: Int): Any {
        return textContents[p0]
    }

}