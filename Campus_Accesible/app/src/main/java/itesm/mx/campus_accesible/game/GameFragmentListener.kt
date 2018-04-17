package itesm.mx.campus_accesible.game

/**
 * Created by LuisErick on 4/15/2018.
 */
interface GameFragmentListener {
    fun startGame()
    fun gameOver(score: Int)
    fun goToMainMenu()
    fun restartGame()
}