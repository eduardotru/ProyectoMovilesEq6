package itesm.mx.campus_accesible.Mapa

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

//////////////////////////////////////////////////////////
//Clase: Edge
// Descripción:
//   <breve   descripción de la clase>
// Autor: Eduardo Trujillo  A01187313
// Fecha de creación: 30/04/2018
// Fecha de última modificación: dd/mm/aaaa
//////////////////////////////////////////////////////////

@Entity
class Edge(@PrimaryKey(autoGenerate = true) var e_id: Long,
           var from: Int,
           var to: Int,
           var accessible: Boolean) {
}