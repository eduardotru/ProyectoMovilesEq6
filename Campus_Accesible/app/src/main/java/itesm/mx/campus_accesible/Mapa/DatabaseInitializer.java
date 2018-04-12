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
                    {25.652536,-100.290787},{25.652536,-100.290463},{25.652687,-100.290451},
                    {25.652524,-100.289956},{25.652582,-100.290078},{25.65264,-100.290171},
                    {25.65272,-100.290143},{25.652757,-100.290057},{25.652848,-100.29007},
                    {25.652519,-100.289788},{25.652606,-100.289462},{25.652787,-100.289398},
                    {25.652671,-100.289113},{25.652504,-100.289105},{25.652386,-100.289089}
            };

}

