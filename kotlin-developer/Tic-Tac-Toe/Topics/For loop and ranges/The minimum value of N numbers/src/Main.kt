fun main() {
    val rep = readLine()!!.toInt()
    var temp = Int.MAX_VALUE
    for (i in 1..rep) {
        val j = readLine()!!.toInt()
        if (j < temp) {
            temp = j
        }
    }
    println(temp)
}
