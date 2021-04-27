package machine
import java.util.Scanner

val sc = Scanner(System.`in`)

class Stock(
    var water: Int = 400,
    var milk: Int = 540,
    var coffeeBeans: Int = 120,
    var cups: Int = 9,
    var money: Int = 550
) {
    fun fillStock() {
        print("Write how many ml of water do you want to add: ")
        water += sc.nextInt()
        print("Write how many ml of milk do you want to add: ")
        milk += sc.nextInt()
        print("Write how many grams of coffee beans do you want to add: ")
        coffeeBeans += sc.nextInt()
        print("Write how many disposable cups of coffee do you want to add: ")
        cups += sc.nextInt()
    }

    fun takeAllMoney() {
        println("I gave you $$money")
        money = 0
    }

    fun getAllStock(): IntArray {
        return intArrayOf(water, milk, coffeeBeans, cups, money)
    }

    fun printStock() {
        println("""
        The coffee machine has:
        $water of water
        $milk of milk
        $coffeeBeans of coffee beans
        $cups of disposable cups
        $$money of money
    """.trimIndent())
    }
}

fun checkStock(stock: Stock, needStock: IntArray) {
    val (initWater, initMilk, initCoffeeBeans, initCups, initMoney) = stock.getAllStock()
    val (needWater, needMilk, needCoffeeBeans, needCups, needMoney) = needStock

    val water = initWater - needWater
    val milk = initMilk - needMilk
    val coffeeBeans = initCoffeeBeans - needCoffeeBeans
    val cups = initCups - needCups
    val money = initMoney + needMoney

    println(
        when {
            water < 0 -> "Sorry, not enough water"
            milk < 0 -> "Sorry, not enough milk"
            coffeeBeans < 0 -> "Sorry, not enough coffee beans"
            else -> {
                stock.water = water
                stock.milk = milk
                stock.coffeeBeans = coffeeBeans
                stock.cups = cups
                stock.money = money
                "I have enough resource, making you a coffee!"
            }
        }
    )
}

fun makeCoffee(coffee: String, stock: Stock) {
    val needStock =
        when (coffee) {
            "1" -> intArrayOf(250, 0, 16, 1, 4)
            "2" -> intArrayOf(350, 75, 20, 1, 7)
            else -> intArrayOf(200, 100, 12, 1, 6)
        }
    return checkStock(stock, needStock)
}

fun main() {
    val stock = Stock(
        water = 400,
        milk = 540,
        coffeeBeans = 120,
        cups = 9,
        money = 550
    )

    while (true) {
        print("\nWrite action (buy, fill, take, remaining, exit): ")
        val action = sc.next()

        if (action == "buy") {
            print("What do you want to buy? 1 - espresso, 2- latte, 3 - cappuccino, back - to main menu: ")
            val coffee = sc.next()
            if (coffee == "back") continue
            else makeCoffee(coffee, stock)
        } else if (action == "fill") {
            stock.fillStock()
        } else if (action == "take") {
            stock.takeAllMoney()
        } else if (action == "remaining") {
            stock.printStock()
        } else {
            break
        }
    }
}
