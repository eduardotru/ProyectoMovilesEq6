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