package itesm.mx.campus_accesible.DB;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import itesm.mx.campus_accesible.Edificios.Edificio;
import itesm.mx.campus_accesible.Mapa.Punto;

public class DatabaseInitializer {

    private static String name = "Punto";


    public static void populate(@NonNull final AppDatabase db) {
        populateWithData(db);
    }

    private static void populateWithData(AppDatabase db) {
        for(int i = 0 ; i < pointsCoordinates.length; i++){
            String puntoName = name+i;
            double longitude = pointsCoordinates[i][0];
            double latitude = pointsCoordinates[i][1];
            addPunto(db, puntoName,longitude,latitude);
        }
        db.puntoModel().insertAllEdificios(edificiosLista());
    }

    private static ArrayList<Edificio> edificiosLista() {
        ArrayList<Edificio> edificioList = new ArrayList<Edificio>();

        Edificio e = new Edificio("Aulas 1", false, "A1-107", null);
        edificioList.add(e);

        e = new Edificio("Aulas 2", false, "A2-127", null);
        edificioList.add(e);

        e = new Edificio("Aulas 3", false, "A3-105", null);
        edificioList.add(e);

        e = new Edificio("Aulas 6", false, "A6-118", null);
        edificioList.add(e);

        e = new Edificio("Aulas 4", false, "A1-101", null);
        edificioList.add(e);

        e = new Edificio("Centrales", false, "CC-104", null);
        edificioList.add(e);

        e = new Edificio("Gimnasio", false, "GI-111", null);
        edificioList.add(e);

        e = new Edificio("Centro de Biotecnología", true, "Todos los baños son accesibles", null);
        edificioList.add(e);

        e = new Edificio("CETEC", true, "Todos los baños son accesibles", null);
        edificioList.add(e);

        e = new Edificio("CIAP", true, "Todos los baños son accesibles", null);
        edificioList.add(e);

        e = new Edificio("Rectoría", true, "Todos los baños son accesibles", null);
        edificioList.add(e);

        e = new Edificio("Biblioteca", true, "Todos los baños son accesibles", null);
        edificioList.add(e);

        return edificioList;
    }

    private static Punto addPunto(final AppDatabase db, final String name, final double longitude,
                                  final double latitude){
        Punto punto = new Punto();
        punto.setName(name);
        punto.setLongitude_coordinate(longitude);
        punto.setLatitude_coordinate(latitude);
        db.puntoModel().insertPunto(punto);
        return punto;
    }


    //  Contiene las coordenadas de los puntos de inicio/destino dentro del campus
    private static double pointsCoordinates[][] =
            {
                    {25.652536,-100.290787},{25.652536,-100.290463},{25.652687,-100.290451},
                    {25.652524,-100.289956},{25.652582,-100.290078},{25.65264,-100.290171},
                    {25.65272,-100.290143},{25.652757,-100.290057},{25.652848,-100.29007},
                    {25.652519,-100.289788},{25.652606,-100.289462},{25.652787,-100.289398},
                    {25.652671,-100.289113},{25.652504,-100.289105},{25.652386,-100.289089}
            };

}

