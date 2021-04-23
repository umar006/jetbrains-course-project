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

fun selectSeat(seats: Array<Array<String>>): Pair<Int, Int> {
    while (true) {
        println("Enter a row number:")
        val row = sc.nextInt()
        println("Enter a seat number in that row:")
        val col = sc.nextInt()
        println()

        try {
            if (seats[row][col] != "B") return Pair(row, col)
            else println("That ticket has already been purchased")
            println()
        } catch (e: ArrayIndexOutOfBoundsException) {
            println("Wrong input!")
            println()
        }
    }
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

fun statistic(purchasedTickets: Int, percentageTickets: Double, income: Int, totalIncome: Int) {
    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: ${"%.2f".format(percentageTickets)}%")
    println("Current income: $$income")
    println("Total income: $$totalIncome")
    println()
}

fun displayMenu(): Int {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
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

    var seats = Array(rows + 1){Array(cols + 1){"$it"} }
    seats = fixInput(seats)

    var purchasedTickets = 0
    var income = 0
    while (true) {
        val choice = displayMenu()
        if (choice == 1) {
            printSeats(seats)
        } else if (choice == 2) {
            // Select a seat
            val (seatRow, seatCol) = selectSeat(seats)
            seats[seatRow][seatCol] = "B"

            // Purchased ticket
            val ticketPrice = ticketPrice(seats, seatRow)
            purchasedTickets++
            income += ticketPrice
            println("Ticket price: $$ticketPrice")
            println()
        } else if (choice == 3) {
            val totalSeats = rows * cols
            val percentageTickets: Double = purchasedTickets.toDouble() * 100 / totalSeats
            val totalIncome =
                if (totalSeats < 60) totalSeats * 10
                else rows / 2 * cols * 10 + (totalSeats - rows / 2 * cols) * 8

            statistic(purchasedTickets, percentageTickets, income, totalIncome)
        } else {
            break
        }
    }
}