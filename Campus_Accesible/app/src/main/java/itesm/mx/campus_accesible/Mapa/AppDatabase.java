package itesm.mx.campus_accesible.Mapa;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Punto.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "campusaccesible-db";

    public abstract PuntoDao puntoModel();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if ( INSTANCE == null ){
            INSTANCE = Room.inMemoryDatabaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }




}