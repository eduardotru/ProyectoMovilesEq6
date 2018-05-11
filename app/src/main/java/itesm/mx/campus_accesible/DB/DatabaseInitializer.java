package itesm.mx.campus_accesible.DB;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import itesm.mx.campus_accesible.Edificios.Edificio;
import itesm.mx.campus_accesible.Mapa.Edge;
import itesm.mx.campus_accesible.Mapa.Punto;
import itesm.mx.campus_accesible.R;

public class DatabaseInitializer {

    private static String name = "Punto";

    public static void populate(@NonNull final AppDatabase db, Activity activity) {
        populateWithData(db, activity);
    }

    private static void populateWithData(AppDatabase db, Activity activity) {
        Resources res = activity.getResources();
        String[] locations = res.getStringArray(R.array.locations);
        for (int i = 0; i < locations.length; i++) {
            String puntoName = name+i;
            String[] location = locations[i].split(" ");
            if (location.length == 2) {
                double latitude = Double.parseDouble(location[0]);
                double longitude = Double.parseDouble(location[1]);
                addPunto(db, puntoName,longitude,latitude);
            }
        }
        db.puntoModel().insertAllEdificios(edificiosLista());

        String[] edges = res.getStringArray(R.array.edges_array);
        for(int i = 0; i < edges.length ; i++){
            String[] edge = edges[i].split(" ");
            if(edge.length == 3){
                int to = Integer.parseInt(edge[0]);
                int from = Integer.parseInt(edge[1]);
                boolean accessible = Boolean.parseBoolean(edge[2]);

                addEdge(db, to,from,accessible);

            }
        }
    }

    private static ArrayList<Edificio> edificiosLista() {
        ArrayList<Edificio> edificioList = new ArrayList<Edificio>();

        Edificio e = new Edificio("Aulas 1", false, "A1-107", R.drawable.aulas1);
        edificioList.add(e);

        e = new Edificio("Aulas 2", false, "A2-127", R.drawable.aulas2);
        edificioList.add(e);

        e = new Edificio("Aulas 3", false, "A3-105", R.drawable.aulas3);
        edificioList.add(e);

        e = new Edificio("Aulas 6", false, "A6-118", R.drawable.aulas6);
        edificioList.add(e);

        e = new Edificio("Aulas 4", false, "A4-101", R.drawable.aulas_4);
        edificioList.add(e);

        e = new Edificio("Centrales", false, "CC-104", R.drawable.centrales);
        edificioList.add(e);

        e = new Edificio("Gimnasio", false, "GI-111", R.drawable.gimnasio);
        edificioList.add(e);

        e = new Edificio("Centro de Biotecnología", true, "Todos los baños son accesibles", R.drawable.centro_biotecnologia);
        edificioList.add(e);

        e = new Edificio("CETEC", true, "Todos los baños son accesibles", R.drawable.cetec);
        edificioList.add(e);

        e = new Edificio("CIAP", true, "Todos los baños son accesibles", R.drawable.ciap);
        edificioList.add(e);

        e = new Edificio("Rectoría", true, "Todos los baños son accesibles", R.drawable.rectoria);
        edificioList.add(e);

        e = new Edificio("Biblioteca", true, "Todos los baños son accesibles", R.drawable.biblioteca);
        edificioList.add(e);

        e = new Edificio("Carreta", true, "Todos los baños son accesibles", R.drawable.carreta);
        edificioList.add(e);

        return edificioList;
    }

    private static void addEdge(final AppDatabase db, final int to, final int from,
                                final boolean accessible){
        Edge edge = new Edge();
        edge.setTo(to);
        edge.setFrom(from);
        edge.setAccessible(accessible);
        db.puntoModel().insertEdge(edge);
    }

    private static void addPunto(final AppDatabase db, final String name, final double longitude,
                                  final double latitude){
        Punto punto = new Punto();
        punto.setName(name);
        punto.setLongitude_coordinate(longitude);
        punto.setLatitude_coordinate(latitude);
        db.puntoModel().insertPunto(punto);
        //return punto;
    }
}

