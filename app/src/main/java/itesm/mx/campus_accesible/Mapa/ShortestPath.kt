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

package itesm.mx.campus_accesible.Mapa

import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.math.pow

//////////////////////////////////////////////////////////
//Clase: ShortestPath
// Descripción:
//
// Autor: Eduardo Trujillo  A01187313
// Fecha de creación: 30/04/2018
// Fecha de última modificación: dd/mm/aaaa
//////////////////////////////////////////////////////////

class ShortestPath(var edgeList: ArrayList<Edge>, var nodes: ArrayList<Punto>) {
    fun shortestPath(from: LatLng, to: LatLng): ArrayList<LatLng> {
        val adjList = Array<ArrayList<EdgeUtil>>(nodes.size, { ArrayList() })
        for(edge in edgeList) {
            if(edge.accessible) {
                val dist = getDistance(nodes[edge.from-1], nodes[edge.to-1])
                adjList[edge.from-1].add(EdgeUtil(edge.from-1, edge.to-1, dist))
                adjList[edge.to-1].add(EdgeUtil(edge.to-1, edge.from-1, dist))
            }
        }
        val visited = BooleanArray(nodes.size, {false})
        val path = IntArray(nodes.size)
        val comp = EdgeUtilComparator()
        val pq = PriorityQueue<EdgeUtil>(100, comp)
        val fromIndex = findIndex(from)

        // Dijsktra Algorithm
        pq.add(EdgeUtil(-1, fromIndex, 0.0))
        while(!pq.isEmpty()) {
            val edge = pq.poll()
            if(!visited[edge.to]) {
                visited[edge.to] = true
                path[edge.to] = edge.from
                for(e in adjList[edge.to]) {
                    val newEdge = EdgeUtil(e.from, e.to, e.dist+edge.dist)
                    pq.add(newEdge)
                }
            }
        }

        val ret = ArrayList<LatLng>()
        var toIndex = findIndex(to)
        while(toIndex != -1) {
            val point = LatLng(nodes[toIndex].latitude_coordinate, nodes[toIndex].longitude_coordinate)
            ret.add(point)
            toIndex = path[toIndex]
        }
        return ret
    }

    fun findIndex(coord: LatLng): Int {
        var minDist = Double.MAX_VALUE
        var index = 0
        for(i in nodes.indices) {
            val point = Punto()
            point.longitude_coordinate = coord.longitude
            point.latitude_coordinate = coord.latitude
            if(getDistance(point, nodes[i]) < minDist) {
                minDist = getDistance(point, nodes[i])
                index = i
            }
        }
        return index
    }

    fun getDistance(from: Punto, to: Punto): Double {
        return (from.latitude_coordinate - to.latitude_coordinate).pow(2) +
                (from.longitude_coordinate - to.longitude_coordinate).pow(2)
    }
}

class EdgeUtil(var from: Int = 0, var to: Int = 0, var dist: Double = 0.0) {
}

class EdgeUtilComparator(): Comparator<EdgeUtil> {
    override fun compare(e1: EdgeUtil?, e2: EdgeUtil?): Int {
        if(e1!!.dist < e2!!.dist) {
            return -1
        } else if(e1.dist > e2.dist) {
            return 1
        }
        return 0
    }
}