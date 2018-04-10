package itesm.mx.campus_accesible.Mapa;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Punto {

    @PrimaryKey
    private int p_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "x_coordinate")
    private double x_coordinate;

    @ColumnInfo(name = "y_coordinate")
    private double y_coordinate;

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

}
