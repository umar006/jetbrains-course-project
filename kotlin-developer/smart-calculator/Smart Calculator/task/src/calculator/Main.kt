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
            val invalIdent = "^[a-zA-Z]+\\d|^\\d+|=[\\x00-\\x7F]*=".toRegex()
            val unknownVar = "(?<==)[a-zA-Z]+".toRegex()

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

        if (value.trim() in store.keys) {
            println(store[value.trim()])
            continue
        }
        if (store[value.trim()] == null && !value.contains("[-+*/()]".toRegex())) {
            println("Unknown variable")
            continue
        }
        if ("**" in value || "//" in value) {
            println("Invalid expression")
            continue
        }

        var input = value.replace("\\s+".toRegex(), " ")
        while ("++" in input || "--" in input || "+-" in input || "-+" in input
            || "**" in input || "//" in input) {
            input = input.replace("++", "+")
            input = input.replace("--", "+")
            input = input.replace("+-", "-")
            input = input.replace("-+", "-")
        }

        if ("(" in input) input = input.replace("(", "( ")
        if (")" in input) input = input.replace(")", " )")

        val infix = input.split(" ")
        val operators: ArrayList<String> = ArrayList()
        val numbers: ArrayList<Int> = ArrayList()

        val isDigit = "[\\d]".toRegex()
        val isLetter = "[a-zA-Z]".toRegex()
        val isOperator = "[-+*/]".toRegex()
        val highPrecedence = "[*/]".toRegex()
        val lowPrecedence = "[-+]".toRegex()

        val postfix = mutableListOf<String>()
        val operatorStack = mutableListOf<String>()
        for (finput in infix) {
            when {
                finput.last().isDigit() -> numbers.add(finput.toInt())
                finput.last().isLetter() -> numbers.add(store[finput]!!)
                else -> operators.add(finput)
            }

            if (finput.contains(isDigit) || finput.contains(isLetter)) {
                postfix.add(finput)
                continue
            }

            if ((operatorStack.isEmpty() || operatorStack.last() == "(")
                && finput.contains(isOperator)) {
                    operatorStack.add(finput)
                    continue
            }

            if (operatorStack.isNotEmpty() && operatorStack.last().contains(lowPrecedence)
                && finput.contains(highPrecedence)) {
                    operatorStack.add(finput)
                    continue
            }

            if (operatorStack.isNotEmpty()
                && ((operatorStack.last().contains(lowPrecedence) && finput.contains(lowPrecedence))
                        || (operatorStack.last().contains(highPrecedence) && finput.contains(highPrecedence))
                        || operatorStack.last().contains(highPrecedence) && finput.contains(lowPrecedence))
            ) {
                postfix.add(operatorStack.removeLast())
                while (operatorStack.isNotEmpty() && (operatorStack.last() != "("
                    || (operatorStack.last().contains(highPrecedence) && finput.contains(highPrecedence))
                    || (operatorStack.last().contains(lowPrecedence) && finput.contains(lowPrecedence)))
                ) postfix.add(operatorStack.removeLast())
                operatorStack.add(finput)
                continue
            }

            if (finput == "(") {
                operatorStack.add(finput)
                continue
            }

            if (finput == ")") {
                if (!operatorStack.contains("(")) { println("Invalid expression"); break }
                while (operatorStack.last() != "(") {
                    postfix.add(operatorStack.removeLast())
                }
                operatorStack.removeLast()
                continue
            }
        }
        while (operatorStack.isNotEmpty()) postfix.add(operatorStack.removeLast())

        if (numbers.size == 0 || numbers.size == operators.size
            || (numbers.size > 1 && operators.size == 0)
        ) { println("Invalid expression"); continue }

        var answer = 0
        val answerStack = mutableListOf<Int>()
//        println(postfix)
        while (postfix.isNotEmpty()) {
            when {
                postfix.first().contains(isDigit) -> answerStack.add(postfix.removeFirst().toInt())
                postfix.first().contains(isLetter) -> answerStack.add(store[postfix.removeFirst()]!!)
                postfix.first().contains(isOperator) -> {
                    val one = answerStack.removeLast()
                    val two = answerStack.removeLast()
                    when (postfix.first()) {
                        "*" -> answer = two * one
                        "/" -> answer = two / one
                        "+" -> answer = two + one
                        "-" -> answer = two - one
                    }
                    postfix.removeFirst()
                    answerStack.add(answer)
                }
            }
        }
        println(answer)
    }
    println("Bye!")
}
