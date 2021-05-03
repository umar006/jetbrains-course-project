package calculator

fun main() {
    while (true) {
        val value = readLine()!!

        if (value == "/exit") break
        if (value == "") continue
        if (value == "/help") {
            println("The program calculates the sum of numbers")
            continue
        }

        val inputList: List<Int> = value.split(" ").map { it.toInt() }
        var sum = 0
        for (input in inputList) sum += input
        println(sum)
    }
    println("Bye!")
}
