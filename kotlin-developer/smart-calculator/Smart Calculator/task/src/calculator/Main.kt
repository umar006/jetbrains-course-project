package calculator

fun main() {
    while (true) {
        val value = readLine()!!

        if (value == "/exit") break
        if (value == "") continue
        if (value == "/help") {
            println("The program calculates the sum and the subtraction depends on the input")
            continue
        }

        val inputList = value.split("\\s+".toRegex()).toMutableList()
        val operators: ArrayList<String> = ArrayList()
        val numbers: ArrayList<Int> = ArrayList()

        for (input in inputList) {
            if (input.last().isDigit()) numbers.add(input.toInt())
            else operators.add(input)
        }

        for (idx in operators.indices) {
            while (operators[idx].length > 1) {
                if ("++" in operators[idx]) operators[idx] = operators[idx].replace("++", "+")
                if ("--" in operators[idx]) operators[idx] = operators[idx].replace("--", "+")
                if ("+-" in operators[idx]) operators[idx] = operators[idx].replace("+-", "-")
                if ("-+" in operators[idx]) operators[idx] = operators[idx].replace("-+", "-")
            }
        }

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
