package itesm.mx.campus_accesible.Mapa

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

//////////////////////////////////////////////////////////
//Clase: Edge
// Descripción:
//   <breve   descripción de la clase>
// Autor: Eduardo Trujillo  A01187313
// Fecha de creación: 30/04/2018
// Fecha de última modificación: dd/mm/aaaa
//////////////////////////////////////////////////////////

@Entity
class Edge(var from: Int = 0,
           var to: Int = 0,
           var accessible: Boolean = true, @PrimaryKey(autoGenerate = true) var e_id: Long? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readValue(Long::class.java.classLoader) as? Long) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(from)
        parcel.writeInt(to)
        parcel.writeByte(if (accessible) 1 else 0)
        parcel.writeValue(e_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Edge> {
        override fun createFromParcel(parcel: Parcel): Edge {
            return Edge(parcel)
        }

        override fun newArray(size: Int): Array<Edge?> {
            return arrayOfNulls(size)
        }
    }
}