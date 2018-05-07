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
    private byte[] imagen;

    @PrimaryKey(autoGenerate = true)
    private int e_id;

    public Edificio (String nombre, boolean elevador, String bano, byte[] imagen){
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

    public void setImagen(byte[] bano) {this.imagen = imagen; }
    public byte[] getImagen() {return imagen; }

    public void setE_id(int e_id) {this.e_id = e_id; }
    public int getE_id() {return e_id; }




}
