import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
//import androidx.compose.runtime.Composable
import kotlinx.browser.document
//import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

//Color.limegreen

fun numToField(num: Int): List<Int> {
    var i = 0
    var j = num
    while ((j - 11) >= 0) {
        j -= 11
        i += 1
    }
    return listOf(i, j)
}

fun main() {
    val fieldColor: MutableList<MutableList<CSSColorValue>> = mutableListOf(*(0..10).map { mutableListOf(*(0..10).map { Color.white }.toTypedArray()) }.toTypedArray())

    val allFields: MutableList<MutableList<Int>> = mutableListOf(*(0..120).map { mutableListOf(0, 1) }.toTypedArray())
    for (i in 0..10) {
        for (j in 0..10) {
            allFields[j+(i*11)][0] = i
            allFields[j+(i*11)][1] = j
        }
    }
    console.log(allFields.toString())

    for (f in mutableListOf(4, 5, 6, 15, 17, 26, 28, 37, 39, 44, 45, 46, 47, 48, 50, 51, 52, 53, 54, 55, 65, 66, 67, 68, 69, 70, 72, 73, 74, 75, 76, 81, 83, 92, 94, 103, 105, 114, 115, 116)) {
        fieldColor[allFields[f][0]][allFields[f][1]] = Color.lightgray
    }

    val playerFields: MutableList<MutableList<MutableList<Int>>> = mutableListOf(*(0..4).map { mutableListOf(*(0..55).map { mutableListOf(0, 0) }.toTypedArray()) }.toTypedArray())
    val playerPathGreen = listOf(10, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 16, 27, 38, 49, 60)
    val playerPathRed = listOf(120, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 64, 63, 62, 61, 60)
    val playerPathBlue = listOf(110, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 104, 93, 82, 71, 60)
    val playerPathYellow = listOf(0, 44, 45, 46, 47, 48, 37, 26, 15, 4, 5, 6, 17, 28, 39, 50, 51, 52, 53, 54, 65, 76, 75, 74, 73, 72, 83, 94, 105, 116, 115, 114, 103, 92, 81, 70, 69, 68, 67, 66, 55, 56, 57, 58, 59, 60)



    var currentPlayer by mutableStateOf(1)
    var tempNum = 0

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
                            Td({
                                style { backgroundColor(fieldColor[i][j]); if (i==5 && j==5) { backgroundColor(Color.dimgray) } }
                                if (i==5 && j==5) {
                                    onClick {
                                        currentPlayer += 1
                                        if (currentPlayer >= 5) { currentPlayer = 1 }
                                    }
                                }
                                tempNum = j + (i * 11)
                            }) {
                                if (i==5 && j==5) {
                                    Text(currentPlayer.toString())
                                }
                                else {
                                    Text("$tempNum  ${numToField(tempNum)}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}