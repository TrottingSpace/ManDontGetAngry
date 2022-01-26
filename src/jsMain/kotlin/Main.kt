import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
//import androidx.compose.runtime.Composable
import kotlinx.browser.document
//import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlin.random.Random


/*fun numToField(num: Int): List<Int> {
    var i = 0
    var j = num
    while ((j - 11) >= 0) {
        j -= 11
        i += 1
    }
    return listOf(i, j)
}*/

fun main() {
    console.log("%c Welcome in \u0022Man, Don't Get Angry\u0022! ", "color: white; font-weight: bold; background-color: black;")
    val fieldColor: MutableList<MutableList<CSSColorValue>> = mutableListOf(*(0..10).map { mutableListOf(*(0..10).map { Color.white }.toTypedArray()) }.toTypedArray())
    val borderColor: MutableList<MutableList<CSSColorValue>> = mutableListOf(*(0..10).map { mutableListOf(*(0..10).map { Color.transparent }.toTypedArray()) }.toTypedArray())

    val fieldList: MutableList<MutableList<Int>> = mutableListOf(*(0..120).map { mutableListOf(0, 1) }.toTypedArray())
    for (i in 0..10) {
        for (j in 0..10) {
            fieldList[j+(i*11)][0] = i
            fieldList[j+(i*11)][1] = j
        }
    }
    //console.log(fieldList.toString())


    //colors for players 0, 1, 2, 3, 4  (4 is technically not a player)
    val playerColor: MutableList<CSSColorValue> = mutableListOf(Color.limegreen, Color.tomato, Color.dodgerblue, Color.gold, Color.dimgray)
    //coloring fields
    for (f in mutableListOf(4, 5, 6, 15, 17, 26, 28, 37, 39, 44, 45, 46, 47, 48, 50, 51, 52, 53, 54, 55, 65, 66, 67, 68, 69, 70, 72, 73, 74, 75, 76, 81, 83, 92, 94, 103, 105, 114, 115, 116)) {
        fieldColor[fieldList[f][0]][fieldList[f][1]] = Color.lightgray
        borderColor[fieldList[f][0]][fieldList[f][1]] = Color.dimgray
    }
    for (f in mutableListOf(6, 19, 20, 30, 31, 16, 27, 38, 49)) {
        fieldColor[fieldList[f][0]][fieldList[f][1]] = playerColor[0]
        borderColor[fieldList[f][0]][fieldList[f][1]] = Color.dimgray
    }
    for (f in mutableListOf(76, 96, 97, 107, 108, 61, 62, 63, 64)) {
        fieldColor[fieldList[f][0]][fieldList[f][1]] = playerColor[1]
        borderColor[fieldList[f][0]][fieldList[f][1]] = Color.dimgray
    }
    for (f in mutableListOf(114, 89, 90, 100, 101, 71, 82, 93, 104)) {
        fieldColor[fieldList[f][0]][fieldList[f][1]] = playerColor[2]
        borderColor[fieldList[f][0]][fieldList[f][1]] = Color.dimgray
    }
    for (f in mutableListOf(44, 12, 13, 23, 24, 56, 57, 58, 59)) {
        fieldColor[fieldList[f][0]][fieldList[f][1]] = playerColor[3]
        borderColor[fieldList[f][0]][fieldList[f][1]] = Color.dimgray
    }

    val playerPathGreen = listOf(10, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 16, 27, 38, 49, 60)
    val playerPathRed = listOf(120, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 64, 63, 62, 61, 60)
    val playerPathBlue = listOf(110, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 104, 93, 82, 71, 60)
    val playerPathYellow = listOf(0, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 56, 57, 58, 59, 60)

    val playerGreenPositions = mutableStateListOf(0, 0, 0, 0)
    val playerRedPositions = mutableStateListOf(0, 0, 0, 0)
    val playerBluePositions = mutableStateListOf(0, 0, 0, 0)
    val playerYellowPositions = mutableStateListOf(0, 0, 0, 0)



    var useDice by mutableStateOf(true)
    var diceCount by mutableStateOf(1)
    var diceThrow by mutableStateOf(0)
    var currentPlayer by mutableStateOf(0)

    //that stays here
    val sudokuDiv = document.getElementById("ManDontGetAngry_root")
    sudokuDiv?.setAttribute("style", "padding: 0px; border: none; aspect-ratio: 1;")

    renderComposable(rootElementId = "ManDontGetAngry_root") {
        Div({ style { padding(25.px) } }) {
            Table({
                style {
                    border(1.px, LineStyle.Solid, Color.blueviolet)
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
                                            style { height(100.percent); width(100.percent); backgroundColor(playerColor[currentPlayer]); padding(0.px); border(1.px, LineStyle.Solid, Color.black) }
                                            if (useDice) {
                                                onClick {
                                                    useDice = false
                                                    if (diceCount > 0) {
                                                        diceThrow = Random.nextInt(1, 7)
                                                        if (diceThrow != 6) { diceCount -= 1 }
                                                        when (currentPlayer) {
                                                            0 -> {
                                                                console.log("green", diceThrow)
                                                            }
                                                            else -> {
                                                                console.log("under development")
                                                            }
                                                        }
                                                        console.log(playerGreenPositions[0], diceCount)
                                                    }
                                                    else {
                                                        currentPlayer += 1
                                                        if (currentPlayer >= 4) {
                                                            currentPlayer = 0
                                                        }
                                                        diceCount = 1
                                                        diceThrow = 0
                                                        useDice = true
                                                    }
                                                }//onClick
                                            }
                                        }) {
                                            Text("$diceThrow")
                                        }//Button
                                    }
                                    87 -> {
                                        //manual player change
                                        Button({
                                            style { height(100.percent); width(100.percent); backgroundColor(rgb(255, 127, 0)); padding(0.px); border(1.px, LineStyle.Solid, Color.black) }
                                            onClick {
                                                currentPlayer += 1
                                                if (currentPlayer >= 4) {
                                                    currentPlayer = 0
                                                }
                                            }
                                        }) {
                                            Text(currentPlayer.toString())
                                        }//Button
                                    }
                                    in listOf(playerPathGreen[playerGreenPositions[0]], playerPathGreen[playerGreenPositions[1]], playerPathGreen[playerGreenPositions[2]], playerPathGreen[playerGreenPositions[3]]) -> {
                                        Button({
                                            style { height(100.percent); width(100.percent); backgroundColor(playerColor[0]); padding(0.px); border(1.px, LineStyle.Solid, Color.black) }
                                            if (currentPlayer == 0) {
                                                if (!useDice) {
                                                    onClick {
                                                        if ((playerGreenPositions[0] + diceThrow) > 45) {
                                                            playerGreenPositions[0] = 45//TODO this is simple win
                                                        }
                                                        else {
                                                            playerGreenPositions[0] += diceThrow
                                                        }
                                                        console.log(playerGreenPositions[0])
                                                        useDice = true
                                                    }
                                                }
                                            }
                                        }) {
                                            Text("G")
                                        }//Button
                                    }
                                    in listOf(playerPathRed[playerRedPositions[0]], playerPathRed[playerRedPositions[1]], playerPathRed[playerRedPositions[2]], playerPathRed[playerRedPositions[3]]) -> {
                                        Text("R")
                                    }
                                    in listOf(playerPathBlue[playerBluePositions[0]], playerPathBlue[playerBluePositions[1]], playerPathBlue[playerBluePositions[2]], playerPathBlue[playerBluePositions[3]]) -> {
                                        Text("B")
                                    }
                                    in listOf(playerPathYellow[playerYellowPositions[0]], playerPathYellow[playerYellowPositions[1]], playerPathYellow[playerYellowPositions[2]], playerPathYellow[playerYellowPositions[3]]) -> {
                                        Text("Y")
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