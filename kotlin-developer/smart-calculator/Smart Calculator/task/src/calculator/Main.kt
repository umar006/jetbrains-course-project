package calculator

import java.lang.NumberFormatException
import java.math.BigInteger

fun main() {
    val store = mutableMapOf<String, String>()

    while (true) {
        val input = readLine()!!

        if (input == "/exit") break
        if (input == "") continue
        if (input == "/help") {
            println("The program calculates the sum and the subtraction depends on the input")
            continue
        }
        if (input.first() == '/') { println("Unknown command"); continue }

        // check, is the input a variable assignment
        if (isVariable(input, store)) continue

        // check, is the input a variable, is variable in store, and print it if true
        // otherwise print "Unknown variable"
        if (!input.contains("[-+*/()]".toRegex())) {
            if (input.trim() in store.keys) {
                println(store[input.trim()])
            } else {
                println("Unknown variable")
            }
            continue
        }

        // check if input have more than two multiply or division symbol
        if ("**" in input || "//" in input) {
            println("Invalid expression")
            continue
        }

        // assign formattedInput
        val formattedInput = formatInput(input)

        // split formattedInput, assign to infix variable
        val infix = formattedInput.split(" ")

        val operators = mutableListOf<String>()
        val numbers = mutableListOf<String>()
        for (finput in infix) {
            when {
                finput.last().isDigit() -> numbers.add(finput)
                finput.last().isLetter() -> numbers.add(store[finput]!!)
                else -> operators.add(finput)
            }
        }
        if (numbers.size == 0 || numbers.size == operators.size
            || (numbers.size > 1 && operators.size == 0)
        ) { println("Invalid expression"); continue }

        // convert infix to postfix
        val postfix = infixToPostfix(infix)

        // calculate postfix and print it
        val answer = try {
            postfixToAnswer(postfix, store)
        } catch (e: NumberFormatException) {
            postfixToAnswerBI(postfix, store)
        }
        println(answer)
    }
    println("Bye!")
}

fun isVariable(value: String, store: MutableMap<String, String>): Boolean {
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
                store[variable[0]] = variable[1]
            }
        }
        return true
    }
    return false
}

fun formatInput(input: String): String {
    // replace two or more whitespace with a whitespace
    var formattedInput = input.replace("\\s+".toRegex(), " ")
    // replace two or more operators with single operators, based on arithmetic rule
    while ("++" in formattedInput || "--" in formattedInput || "+-" in formattedInput || "-+" in formattedInput) {
        formattedInput = formattedInput.replace("++", "+")
        formattedInput = formattedInput.replace("--", "+")
        formattedInput = formattedInput.replace("+-", "-")
        formattedInput = formattedInput.replace("-+", "-")
    }
    // give whitespace after "(" and before ")"
    if ("(" in formattedInput) formattedInput = formattedInput.replace("(", "( ")
    if (")" in formattedInput) formattedInput = formattedInput.replace(")", " )")

    return formattedInput
}

fun infixToPostfix(infix: List<String>): MutableList<String> {
    val isDigit = "[\\d]".toRegex()
    val isLetter = "[a-zA-Z]".toRegex()
    val isOperator = "[-+*/]".toRegex()
    val highPrecedence = "[*/]".toRegex()
    val lowPrecedence = "[-+]".toRegex()

    val postfix = mutableListOf<String>()
    val operatorStack = mutableListOf<String>()
    for (finput in infix) {

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

    return postfix
}

fun postfixToAnswer(postfix: MutableList<String>, store: MutableMap<String, String>): Int {
    val isDigit = "[\\d]".toRegex()
    val isLetter = "[a-zA-Z]".toRegex()
    val isOperator = "[-+*/]".toRegex()

    var answer = 0
    val answerStack = mutableListOf<Int>()
    while (postfix.isNotEmpty()) {
        when {
            postfix.first().contains(isDigit) -> {
                postfix[0].toInt()
                answerStack.add(postfix.removeFirst().toInt())
            }
            postfix.first().contains(isLetter) -> {
                store[postfix[0]]!!.toInt()
                answerStack.add(store[postfix.removeFirst()]!!.toInt())
            }
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
    return answer
}

fun postfixToAnswerBI(postfix: MutableList<String>, store: MutableMap<String, String>): BigInteger {
    val isDigit = "[\\d]".toRegex()
    val isLetter = "[a-zA-Z]".toRegex()
    val isOperator = "[-+*/]".toRegex()

    var answer = BigInteger("0")
    val answerStack = mutableListOf<BigInteger>()
    while (postfix.isNotEmpty()) {
        when {
            postfix.first().contains(isDigit) -> answerStack.add(postfix.removeFirst().toBigInteger())
            postfix.first().contains(isLetter) -> answerStack.add(store[postfix.removeFirst()]!!.toBigInteger())
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
    return answer
}
