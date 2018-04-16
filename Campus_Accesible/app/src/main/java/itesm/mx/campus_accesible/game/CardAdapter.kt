package itesm.mx.campus_accesible.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import itesm.mx.campus_accesible.R
import java.util.*
import java.util.Collections.shuffle

/**
 * Created by LuisErick on 4/15/2018.
 */

class CardAdapter(context: Context) : BaseAdapter() {
    val context = context

    val textContents: ArrayList<String> = arrayListOf("abc", "def", "ghi", "jkl", "mno",
                                                      "abc", "def", "ghi", "jkl", "mno")

    init {
        Collections.shuffle(textContents)
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val cardView = LayoutInflater.from(context).inflate(R.layout.card, p2, false)
        val cardDescription = cardView.findViewById<TextView>(R.id.desc)
        cardDescription.text = getItem(p0).toString()

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