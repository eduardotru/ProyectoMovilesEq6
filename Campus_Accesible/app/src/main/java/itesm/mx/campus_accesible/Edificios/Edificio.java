package itesm.mx.campus_accesible.Edificios;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jime on 4/15/18.
 */

@Entity
public class Edificio {
    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "elevador")
    private boolean elevador;

    @ColumnInfo(name = "bano")
    private String bano;

    @ColumnInfo(name = "imagen")
    private byte[] imagen;

    @PrimaryKey(autoGenerate = true)
    private int e_id;


    //Setters y Getters
    public void setNombre() { this.nombre = nombre; }
    public String getNombre() { return nombre; }

    public void setElevador() {this.elevador = elevador; }
    public boolean getElevador() {return elevador; }

    public void setBano() {this.bano = bano; }
    public String getBano() { return bano; }

    public void setImagen() {this.imagen = imagen; }
    public byte[] getImagen() {return imagen; }

    public void setE_id() {this.e_id = e_id; }
    public int getE_id() {return e_id; }




}
