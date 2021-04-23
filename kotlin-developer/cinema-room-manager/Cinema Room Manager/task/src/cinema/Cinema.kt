package cinema
import java.util.Scanner

val sc = Scanner(System.`in`)

fun fixInput(seats: Array<Array<String>>): Array<Array<String>> {
    val rows = seats.size - 1
    val cols = seats[0].size - 1

    var numberSeats = 1
    for (row in 0..rows) {
        for (col in 0..cols) {
            if (row == 0) {
                seats[row][col] = " "
                break
            } else {
                if (seats[row][col] == "0") {
                    seats[row][col] = "$numberSeats"
                    numberSeats++
                } else {
                    seats[row][col] = "S"
                }
            }
        }
    }
    return seats
}

fun printSeats(seats: Array<Array<String>>) {
    println("Cinema:")
    for (r in seats) {
        for (c in r) {
            print("$c ")
        }
        println()
    }
    println()
}

fun buyTicket(): Pair<Int, Int> {
    println("Enter a row number:")
    val row = sc.nextInt()

    println("Enter a seat number in that row:")
    val col = sc.nextInt()

    return Pair(row, col)
}

fun ticketPrice(seats: Array<Array<String>>, seatRow: Int): Int {
    val rows = seats.size - 1
    val cols = seats[0].size - 1

    val totalSeats = rows * cols

    return if (totalSeats < 60) {
        10
    } else {
        if (seatRow <= rows / 2) 10 else 8
    }
}

fun displayMenu(): Int {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("0. Exit")

    val choice = sc.nextInt()
    println()

    return choice
}

fun main() {
    println("Enter the number of rows:")
    val rows = sc.nextInt()

    println("Enter the number of seats in each row:")
    val cols = sc.nextInt()
    println()

    var seats = Array<Array<String>>(rows + 1){Array<String>(cols + 1){"$it"} }
    seats = fixInput(seats)

    while (true) {
        val choice = displayMenu()
        if (choice == 1) {
            printSeats(seats)
        } else if (choice == 2) {
            // Buy a ticket
            val (seatRow, seatCol) = buyTicket()
            seats[seatRow][seatCol] = "B"

            val tickerPrice = ticketPrice(seats, seatRow)
            println("Ticket price: $$tickerPrice\n")
        } else {
            break
        }
    }
}