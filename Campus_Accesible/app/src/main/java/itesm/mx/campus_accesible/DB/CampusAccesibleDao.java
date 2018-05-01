package itesm.mx.campus_accesible.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import itesm.mx.campus_accesible.Edificios.Edificio;
import itesm.mx.campus_accesible.Mapa.Edge;
import itesm.mx.campus_accesible.Mapa.Punto;

@Dao
public interface CampusAccesibleDao {

    @Query("SELECT * FROM punto")
    List<Punto> getAll();

    @Query("SELECT * FROM punto WHERE NOT(longitude_coordinate = :y_val AND latitude_coordinate = :x_val)")
    List<Punto> getDestination(double x_val , double y_val);

    @Query("SELECT * FROM edificio Order by nombre")
    List<Edificio> getAllEdificios();

    @Query("SELECT * FROM edge")
    List<Edge> getAllEdges();

    @Insert
    void insertAllPuntos(ArrayList<Punto> puntos);

    @Insert
    void insertPunto(Punto punto);

    @Insert
    void insertAllEdificios(ArrayList<Edificio> edificios);

}
