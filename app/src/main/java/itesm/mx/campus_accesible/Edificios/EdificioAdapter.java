package itesm.mx.campus_accesible.Edificios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jime on 4/15/18.
 */

public class EdificioAdapter extends ArrayAdapter<Edificio> {

    public EdificioAdapter(Context context, ArrayList<Edificio> edificios) {
        super(context, 0,edificios);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Edificio edificio = getItem(position);

        //convertView --> vista a reusar, si es nulo se crea
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView tvNombre = (TextView) convertView.findViewById(android.R.id.text1);

        tvNombre.setText(edificio.getNombre());

        return convertView;
    }

}


