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

package itesm.mx.campus_accesible.game

/**
 * Created by LuisErick on 4/15/2018.
 */
interface GameFragmentListener {
    fun startGame()
    fun gameOver(score: Int, timer: Int)
    fun goToMainMenu()
    fun restartGame()
}