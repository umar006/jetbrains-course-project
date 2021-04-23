package cinema
import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)

    println("Enter the number of rows:")
    val rows = sc.nextInt()
    println("> $rows")

    println("Enter the number of seats in each row:")
    val cols = sc.nextInt()
    println("> $cols")

    val seats = rows * cols
    val income =
        if (seats < 60) {
            seats * 10
        } else {
            val halfSeats = rows / 2 * cols
            halfSeats * 10 + (seats - halfSeats) * 8
        }

    println("Total income:\n$$income")
}