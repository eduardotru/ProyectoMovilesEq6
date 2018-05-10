package itesm.mx.campus_accesible.Mapa;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Punto implements Parcelable {

    public Punto() {

    }

    @PrimaryKey(autoGenerate = true)
    private int p_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "longitude_coordinate")
    private double longitude_coordinate;

    @ColumnInfo(name = "latitude_coordinate")
    private double latitude_coordinate;

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

    public double getLatitude_coordinate() {
        return latitude_coordinate;
    }

    public void setLatitude_coordinate(double latitude_coordinate) {
        this.latitude_coordinate = latitude_coordinate;
    }

    public double getLongitude_coordinate() {
        return longitude_coordinate;
    }

    public void setLongitude_coordinate(double longitude_coordinate) {
        this.longitude_coordinate = longitude_coordinate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(p_id);
        parcel.writeString(name);
        parcel.writeDouble(latitude_coordinate);
        parcel.writeDouble(longitude_coordinate);
    }

    public static final Parcelable.Creator<Punto> CREATOR = new Parcelable.Creator<Punto>() {
        public Punto createFromParcel(Parcel in) {
            return new Punto(in);
        }
        public Punto[] newArray(int size) {
            return new Punto[size];
        }
    };

    private Punto(Parcel in) {
        p_id = in.readInt();
        name = in.readString();
        latitude_coordinate = in.readDouble();
        longitude_coordinate = in.readDouble();
    }
}
