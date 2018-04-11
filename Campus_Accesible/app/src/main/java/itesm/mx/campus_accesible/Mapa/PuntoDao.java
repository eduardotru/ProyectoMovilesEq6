package itesm.mx.campus_accesible.Mapa;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PuntoDao {

    @Query("SELECT * FROM punto")
    List<Punto> getAll();

    @Query("SELECT * FROM punto WHERE NOT longitude_coordinate = :y_val AND latitude_coordinate = :x_val")
    List<Punto> getDestination(double x_val , double y_val);

    @Insert
    void insertAll(List<Punto> puntos);

    @Insert
    void insertPunto(Punto punto);

}