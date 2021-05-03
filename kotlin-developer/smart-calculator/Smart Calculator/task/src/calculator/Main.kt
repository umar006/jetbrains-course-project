package calculator

fun main() {
    val (input1, input2) = readLine()!!.split(" ").map { it.toInt() }

    println(input1 + input2)
}
