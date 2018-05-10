package itesm.mx.campus_accesible.Edificios;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import itesm.mx.campus_accesible.R;

/**
 * Created by jime on 4/14/18.
 */

public class DetalleFragment extends Fragment {

    private Edificio edificio;

    public static DetalleFragment newInstance(Edificio edificio) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle args = new Bundle();
        args.putSerializable("Edificio",edificio);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();



        if(args != null) {
            edificio =(Edificio)args.getSerializable("Edificio");
        } else {
            edificio = new Edificio();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        TextView tvNombre = (TextView) view.findViewById(R.id.text1);
        TextView tvBanos = (TextView) view.findViewById(R.id.text_valorBanos);
        TextView tvElevador = (TextView) view.findViewById(R.id.text_valorElevador);
        ImageView ivImagen = (ImageView) view.findViewById(R.id.image_edificio);

        tvNombre.setText(edificio.getNombre());
        tvBanos.setText(edificio.getBano());

        if(edificio.getElevador())
        {
            tvElevador.setText(" Sí");
        }
        else {
            tvElevador.setText(" NO");
            tvElevador.setTextColor(Color.RED);
        }

        ivImagen.setImageResource(edificio.getImagen());

        return view;

    }
}


