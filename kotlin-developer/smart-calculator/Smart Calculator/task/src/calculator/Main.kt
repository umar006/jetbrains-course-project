package calculator

fun main() {
    while (true) {
        val value = readLine()!!

        if (value == "/exit") break
        if (value == "") continue

        try {
            val (input1, input2) = value.split(" ").map { it.toInt() }
            println(input1 + input2)
        } catch (e: IndexOutOfBoundsException) {
            println("$value")
        }
    }
    println("Bye!")
}
