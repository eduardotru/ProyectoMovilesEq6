/*
    Campus Accesible. Map with accessible routes inside ITESM Monterrey.
	Copyright (C) 2018 - ITESM

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
    void insertEdge(Edge edge);

    @Insert
    void insertPunto(Punto punto);

    @Insert
    void insertAllEdificios(ArrayList<Edificio> edificios);

}
