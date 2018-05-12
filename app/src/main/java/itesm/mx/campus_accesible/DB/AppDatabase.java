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