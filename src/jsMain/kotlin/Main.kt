//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.runtime.Composable
import kotlinx.browser.document
//import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

//Color.limegreen

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

    val myTempList: MutableList<Int> = mutableListOf(0)
    console.log(myTempList.size, "\n")


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
                            Td({ style { backgroundColor(fieldColor[i][j]) } }) {
                                Text((j+(i*11)).toString())
                            }
                        }
                    }
                }
            }
        }
    }
}