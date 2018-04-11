package itesm.mx.campus_accesible.Mapa;

import android.support.annotation.NonNull;

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
                    {25.652536, -100.290787},{25.652536,-100.290463},{25.652687,-100.290451},
            };

}
