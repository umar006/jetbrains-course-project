package calculator

fun main() {
    val store = mutableMapOf<String, Int>()

    while (true) {
        val value = readLine()!!

        if (value == "/exit") break
        if (value == "") continue
        if (value == "/help") {
            println("The program calculates the sum and the subtraction depends on the input")
            continue
        }
        if (value.first() == '/') { println("Unknown command"); continue }

        if ("=" in value) {
            val formattedValue = value.replace("\\s+".toRegex(), "")
            val variable = formattedValue.split("=")

            val invalAssign = "(?<==)([a-zA-Z]+\\d|\\d+[a-zA-Z])|=[a-zA-Z]\\d=|=\\d[a-zA-Z]=".toRegex()
            val invalIdent = "^[a-zA-Z]+\\d|^\\d+|[^\\w=]".toRegex()
            val unknownVar = "(?<=\\=)[a-zA-Z]+".toRegex()

            when {
                formattedValue.contains(invalAssign) -> println("Invalid assignment")
                formattedValue.contains(invalIdent) -> println("Invalid identifier")
                formattedValue.contains(unknownVar) -> {
                    when (store[variable[1]]) {
                        null -> println("Unknown variable")
                        else -> store[variable[0]] = store[variable[1]]!!
                    }
                }
                else -> {
                    store[variable[0]] = variable[1].toInt()
                }
            }
            continue
        }

        if (value in store.keys) {
            println(store[value])
            continue
        }
        if (store[value] == null && !value.contains("[+-]".toRegex())) {
            println("Unknown variable")
            continue
        }

        var input = value.replace("\\s+".toRegex(), " ")
        while ("++" in input || "--" in input || "+-" in input || "-+" in input) {
            input = input.replace("++", "+")
            input = input.replace("--", "+")
            input = input.replace("+-", "-")
            input = input.replace("-+", "-")
        }

        val formattedInput = input.split(" ")
        val operators: ArrayList<String> = ArrayList()
        val numbers: ArrayList<Int> = ArrayList()

        for (finput in formattedInput) {
            when {
                finput.last().isDigit() -> numbers.add(finput.toInt())
                finput.last().isLetter() -> numbers.add(store[finput]!!)
                else -> operators.add(finput)
            }
        }

        if (numbers.size == 0) { println("Invalid expression"); continue }
        if (numbers.size == operators.size) { println("Invalid expression"); continue }
        if (numbers.size > 1 && operators.size == 0) { println("Invalid expression"); continue }

        var result = numbers[0]
        for (ix in operators.indices) {
            when (operators[ix]) {
                "+" -> result += numbers[ix + 1]
                "-" -> result -= numbers[ix + 1]
            }
        }
        println(result)
    }
    println("Bye!")
}
