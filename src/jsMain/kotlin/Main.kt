import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
//import androidx.compose.runtime.Composable
import kotlinx.browser.document
//import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlin.random.Random

//playerPath[player][field number]
val playerPath = listOf(
    listOf(7, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 16, 27, 38, 49, 60),
    listOf(87, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 64, 63, 62, 61, 60),
    listOf(113, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 104, 93, 82, 71, 60),
    listOf(33, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 56, 57, 58, 59, 60)
)
//player home fields 0000111122223333
val playerHomes = listOf(19, 20, 30, 31, 96, 97, 107, 108, 89, 90, 100, 101, 12, 13, 23, 24)


class Game {
    private val progressList = (0..15).map { 0 }.toMutableStateList()
    private val positionList = (0..15).map { playerHomes[it] }.toMutableStateList()
    fun positionsGet(): List<Int> { return positionList }
    var throwing = false
    var player by mutableStateOf(0)
        private set
    fun nextPlayer() { if (player==3) { player=0 } else { player+=1 } }
    var dice by mutableStateOf(0)
        private set
    fun diceThrow() { dice = Random.nextInt(1, 7) }
    fun kick(currentPiece: Int) {
        val fieldNumber = playerPath[currentPiece/4][progressList[currentPiece]]
        val indexList = (0..15).map { it }.toMutableList()
        for (i in 0..3) {
            indexList.removeAt((currentPiece/4)*4)
        }
        //console.log(">>", indexList.toString())
        indexList.forEach { if (positionList[it]==fieldNumber) { progressList[it]=0; positionList[it] = playerHomes[it] } }
    }
    fun progressGet(piece: Int): Int { if ((0..15).contains(piece)) { return progressList[piece] }; return 0 }
    fun progressAdd(piece: Int) {
        if ((0..15).contains(piece) && dice != 0) {
            progressList[piece] += dice
            if (progressList[piece] > 45) { progressList[piece] = 45 }
            positionList[piece] = playerPath[piece/4][progressList[piece]]
        }
    }
    //nested class
    class Piece(private val index:Int) {
        val player = index/4
        private val pieceNumber = index%4
        var inGame = (1..44).contains(game.progressList[index])
            private set
        val name: String = "$player/$pieceNumber"
    }
}

val game: Game = Game()
val gamePiece = (0..15).map { Game.Piece(it) }

fun main() {
    console.log("%c Welcome in \u0022Man, Don't Get Angry\u0022! ", "color: white; font-weight: bold; background-color: black;")
    val fieldColor: MutableList<MutableList<CSSColorValue>> = (0..10).map { (0..10).map { Color.white }.toMutableList() }.toMutableList()
    val borderColor: MutableList<MutableList<CSSColorValue>> = (0..10).map { (0..10).map { Color.transparent }.toMutableList() }.toMutableList()

    val fieldList: List<List<Int>> = (0..120).map { listOf(it/11, it%11) }
    //console.log(fieldList.toString())

    //game.kick(1, 3)

    //listOf(1, 3, 5, 7).forEach { console.log(it) }

    /*console.log(game.progressGet(2))
    game.diceThrow()
    //game.progressAdd(2)
    console.log(game.progressGet(2))
    console.log(gamePiece[1].name)*/


    //colors for players 0, 1, 2, 3, 4  (4 is technically not a player)
    val playerColor: MutableList<CSSColorValue> = mutableListOf(Color.limegreen, Color.tomato, Color.dodgerblue, Color.gold, Color.dimgray)
    //coloring fields
    listOf(4, 5, 6, 15, 17, 26, 28, 37, 39, 44, 45, 46, 47, 48, 50, 51, 52, 53, 54, 55, 65, 66, 67, 68, 69, 70, 72, 73, 74, 75, 76, 81, 83, 92, 94, 103, 105, 114, 115, 116).map { fieldColor[it/11][it%11] = Color.lightgray; borderColor[it/11][it%11] = Color.dimgray }
    listOf(6, 19, 20, 30, 31, 16, 27, 38, 49).map { fieldColor[it/11][it%11] = playerColor[0]; borderColor[it/11][it%11] = Color.dimgray }
    listOf(76, 96, 97, 107, 108, 61, 62, 63, 64).map { fieldColor[it/11][it%11] = playerColor[1]; borderColor[it/11][it%11] = Color.dimgray }
    listOf(114, 89, 90, 100, 101, 71, 82, 93, 104).map { fieldColor[it/11][it%11] = playerColor[2]; borderColor[it/11][it%11] = Color.dimgray }
    listOf(44, 12, 13, 23, 24, 56, 57, 58, 59).map { fieldColor[it/11][it%11] = playerColor[3]; borderColor[it/11][it%11] = Color.dimgray }

    listOf(36, 40, 80, 84).map { fieldColor[it/11][it%11] = Color.pink; borderColor[it/11][it%11] = Color.deeppink }//todo testing





    //"ManDontGetAngry_root" div style applying
    document.getElementById("ManDontGetAngry_root")?.setAttribute("style", "padding: 0px; border: none; aspect-ratio: 1;")

    renderComposable(rootElementId = "ManDontGetAngry_root") {
        Div({ style { padding(25.px) } }) {
            Table({
                style {
                    border(1.px, LineStyle.Solid, Color.darkgray)
                    textAlign("center")
                    //property("vertical-align", "center")
                    property("table-layout", "fixed")
                    property("border-spacing", "0px")
                    property("width", "100%")
                    property("height", "100%")
                    property("aspect-ratio", "1")
                    backgroundColor(Color.lightgray)
                }
            }) {
                for (i in 0..10) {
                    Tr {
                        for (j in 0..10) {
                            val fieldNum = j + (i * 11)
                            Td({
                                style { backgroundColor(fieldColor[i][j]); border(1.px, LineStyle.Solid, borderColor[i][j]); padding(0.px) }
                            }) {
                                when (fieldNum) {
                                    60 -> {
                                        Button({
                                            style { height(100.percent); width(100.percent); backgroundColor(playerColor[game.player]); padding(0.px); border(1.px, LineStyle.Solid, Color.black) }
                                            onClick {
                                                game.diceThrow()
                                            }//onClick
                                        }) {
                                            Text("[ ${game.dice} ]")
                                        }//Button
                                    }
                                    87 -> {
                                        //manual player change
                                        Button({
                                            style { height(100.percent); width(100.percent); backgroundColor(rgb(255, 127, 0)); padding(0.px); border(1.px, LineStyle.Solid, Color.black) }
                                            onClick {
                                                game.nextPlayer()
                                            }
                                        }) {
                                            Text("< ${game.player} >")
                                        }//Button
                                    }
                                    in game.positionsGet() -> {
                                        //game.positionsGet().indexOf(fieldNum)
                                        for (k in 0..15) {
                                            if (game.positionsGet()[k]==fieldNum) {
                                                Button({
                                                    style { height(100.percent); width(100.percent); backgroundColor(playerColor[gamePiece[k].player]); padding(0.px); border(1.px, LineStyle.Solid, Color.black) }
                                                    onClick {
                                                        console.log("| ${gamePiece[k].name} |")
                                                        game.progressAdd(k)
                                                        console.log(game.progressGet(k))
                                                        game.kick(k)
                                                    }
                                                }) {
                                                    Text("| ${gamePiece[k].name} |")
                                                }//Button
                                            }
                                        }
                                    }
                                    in listOf(36, 40, 80, 84) -> {
                                        Text("W.I.P.")
                                    }
                                    else -> {
                                        Text("#$fieldNum   ${fieldList[fieldNum][0]},${fieldList[fieldNum][1]}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}