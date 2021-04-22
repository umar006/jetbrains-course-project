package tictactoe
import java.util.Scanner


fun printBoard(board: Array<Array<Char>>) {
    println("---------")
    println("| ${board[0].joinToString(" ")} |")
    println("| ${board[1].joinToString(" ")} |")
    println("| ${board[2].joinToString(" ")} |")
    println("---------")
}

fun arrayToString(arr: Array<Array<Char>>): String {
    val strs = arr.contentDeepToString()
    var str = ""
    for (i in strs) {
        if (i in "XO_") {
            str += i
        }
    }
    return str
}

fun gameCondition(board: Array<Array<Char>>): Pair<Array<Boolean>, Array<Int>> {
    // Horizontal
    val rowH = arrayOf(
        "${board[0][0]}, ${board[0][1]}, ${board[0][2]}",
        "${board[1][0]}, ${board[1][1]}, ${board[1][2]}",
        "${board[2][0]}, ${board[2][1]}, ${board[2][2]}"
    )
    // Vertical
    val rowV = arrayOf(
        "${board[0][0]}, ${board[1][0]}, ${board[2][0]}",
        "${board[0][1]}, ${board[1][1]}, ${board[2][1]}",
        "${board[0][2]}, ${board[1][2]}, ${board[2][2]}"
    )
    // Diagonal
    val rowD = arrayOf(
        "${board[0][0]}, ${board[1][1]}, ${board[2][2]}",
        "${board[0][2]}, ${board[1][1]}, ${board[2][0]}",
    )

    val str = arrayToString(board)

    // Rule
    var x = 0
    var o = 0
    var e = 0
    for (s in str) {
        x += if (s == 'X') 1 else 0
        o += if (s == 'O') 1 else 0
        e += if (s == '_') 1 else 0
    }

    val xInRow = "X, X, X"
    val oInRow = "O, O, O"

    val xRow = xInRow in rowH || xInRow in rowV || xInRow in rowD
    val oRow = oInRow in rowH || oInRow in rowV || oInRow in rowD

    return Pair(arrayOf(xRow, oRow), arrayOf(x, o, e))
}

fun result(gameCondition: Pair<Array<Boolean>, Array<Int>>): String {
    val (row, count) = gameCondition
    val (xRow, oRow) = row
    val (x, o, e) = count

    // Game condition
    val draw = e == 0 && !xRow && !oRow
    val xWins = xRow && !oRow
    val oWins = oRow && !xRow
    val impossible = (xRow && oRow) || x - o > 1 || o - x > 1

    return when {
        draw -> "Draw"
        xWins -> "X wins"
        oWins -> "O wins"
        impossible -> "Impossible"
        else -> "Next"
    }
}

fun main() {
    val sc = Scanner(System.`in`)
    val input = "_________"

    val board = arrayOf(
        arrayOf(input[0], input[1], input[2]),
        arrayOf(input[3], input[4], input[5]),
        arrayOf(input[6], input[7], input[8])
    )
    println("---------")
    println("| ${board[0].joinToString(" ")} |")
    println("| ${board[1].joinToString(" ")} |")
    println("| ${board[2].joinToString(" ")} |")
    println("---------")

    var changePlayer = true
    while (true) {
        val rowChar = sc.next().first()
        val colChar = sc.next().first()
        println("Enter the coordinated: $rowChar $colChar")

        if (!rowChar.isLetter() && !colChar.isLetter()) {
            var row = rowChar.toString().toInt()
            var col = colChar.toString().toInt()

            if (row in 1..3 && col in 1..3) {
                row--
                col--

                if (board[row][col] == '_') {
                    if (changePlayer) {
                        board[row][col] = 'X'

                        changePlayer = false
                    } else {
                        board[row][col] = 'O'

                        changePlayer = true
                    }
                    printBoard(board)

                    when (result(gameCondition(board))) {
                        "Draw" -> {
                            println("Draw")
                            break
                        }
                        "X wins" -> {
                            println("X wins")
                            break
                        }
                        "O wins" -> {
                            println("O wins")
                            break
                        }
                        "Impossible" -> {
                            println("Impossible")
                            break
                        }
                        "Error" -> {
                            continue
                        }
                    }
                } else println("This cell is occupied! choose another one!")
            } else println("Coordinates should be from 1 to 3")
        } else println("You should enter numbers!")
    }
}
