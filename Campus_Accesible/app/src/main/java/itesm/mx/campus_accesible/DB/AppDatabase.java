package itesm.mx.campus_accesible.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import itesm.mx.campus_accesible.Edificios.Edificio;
import itesm.mx.campus_accesible.Mapa.Edge;
import itesm.mx.campus_accesible.Mapa.Punto;

import java.util.ArrayList;

@Database(entities = {Punto.class, Edificio.class, Edge.class}, version = 3, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "campusaccesible-db";

    public abstract CampusAccesibleDao puntoModel();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if ( INSTANCE == null ){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public interface DatabaseDelegate {
        ArrayList<Punto> fetchDestination(double longitude, double lattitude);
    }

}