fun main() {
    val rep = readLine()!!.toInt()
    var temp = Int.MIN_VALUE
    var check = 0
    for (i in 1..rep) {
        val j = readLine()!!.toInt()
        if (j > temp) {
            temp = j
        } else {
            check++
        }
    }
    println(if (check == 0) "YES" else "NO")
}
