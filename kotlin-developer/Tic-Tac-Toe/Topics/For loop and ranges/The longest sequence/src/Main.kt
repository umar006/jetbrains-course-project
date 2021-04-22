fun main() {
    val rep = readLine()!!.toInt()
    var temp = 0
    var count = 0
    var max = 0
    for (i in 1..rep) {
        val j = readLine()!!.toInt()
        if (j >= temp) {
            temp = j
            count += 1
        } else {
            temp = j
            count = 1
        }
        if (count > max) {
            max = count
        }
    }
    println(max)
}
