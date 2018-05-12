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

package itesm.mx.campus_accesible.Edificios;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by jime on 4/15/18.
 */

@Entity
public class Edificio implements Serializable{
    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "elevador")
    private boolean elevador;

    @ColumnInfo(name = "bano")
    private String bano;

    @ColumnInfo(name = "imagen")
    private int imagen;

    @PrimaryKey(autoGenerate = true)
    private int e_id;

    public Edificio (String nombre, boolean elevador, String bano, int imagen){
        this.nombre = nombre;
        this.elevador = elevador;
        this.bano = bano;
        this.imagen = imagen;
    }


    public Edificio(){}

    //Setters y Getters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean getElevador() {return elevador; }
    public void setElevador(boolean elevador) {this.elevador = elevador; }

    public void setBano(String bano) {this.bano = bano; }
    public String getBano() { return bano; }

    public void setImagen(int imagen) {this.imagen = imagen; }
    public int getImagen() {return imagen; }

    public void setE_id(int e_id) {this.e_id = e_id; }
    public int getE_id() {return e_id; }




}
