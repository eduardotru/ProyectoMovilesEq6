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
