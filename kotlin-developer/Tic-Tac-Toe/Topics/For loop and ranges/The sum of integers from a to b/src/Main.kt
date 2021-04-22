fun main() {
    val one = readLine()!!.toInt()
    val two = readLine()!!.toInt()
    var sum = 0
    for(i in one..two) {
        sum += i
    }
    println(sum)
}
