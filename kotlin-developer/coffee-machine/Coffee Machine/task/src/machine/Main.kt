package machine
import java.util.Scanner

val sc = Scanner(System.`in`)

fun stockCalculator(cups: Int, stock: IntArray) {


//    val cupsStock = intArrayOf(waterAmount / water, milkAmount / milk, coffeeBeansAmount / coffeeBeans).minOf{it}
//
//    when {
//        cupsStock == cups -> println("Yes, I can make that amount of coffee")
//        cupsStock - cups >= 1 -> println("Yes, I can make that amount of coffee (and even ${cupsStock - cups} more than that")
//        else -> println("No, I can make only $cupsStock cups of coffee")
//    }
}

fun addStock(initialStock: IntArray): IntArray {
    print("Write how many ml of water do you want to add: ")
    val addWater = sc.nextInt()
    print("Write how many ml of milk do you want to add: ")
    val addMilk = sc.nextInt()
    print("Write how many grams of coffee beans do you want to add: ")
    val addCoffeeBeans = sc.nextInt()
    print("Write how many disposable cups of coffee do you want to add: ")
    val addCups = sc.nextInt()

    val (initWater, initMilk, initCofeeBeans, initCups, initMoney) = initialStock

    return intArrayOf(
        initWater + addWater,
        initMilk + addMilk,
        initCofeeBeans + addCoffeeBeans,
        initCups + addCups,
        initMoney
    )
}

fun displayStock(stock: IntArray) {
    println("""
        The coffee machine has:
        ${stock[0]} of water
        ${stock[1]} of milk
        ${stock[2]} of coffee beans
        ${stock[3]} of disposable cups
        $${stock[4]} of money
    """.trimIndent())
}

fun makeCoffee(coffee: Int, initialStock: IntArray): Pair<IntArray, Unit> {
    val (needWater, needMilk, needCoffeeBeans, needCups, needMoney) =
        when (coffee) {
            1 -> intArrayOf(250, 0, 16, 1, 4)
            2 -> intArrayOf(350, 75, 20, 1, 7)
            else -> intArrayOf(200, 100, 12, 1, 6)
        }
    val (initWater, initMilk, initCoffeeBeans, initCups, initMoney) = initialStock

    val water = initWater - needWater
    val milk = initMilk - needMilk
    val coffeeBeans = initCoffeeBeans - needCoffeeBeans

    return when {
        water < 0 -> Pair(initialStock, println("Sorry, not enough water!"))
        milk < 0 -> Pair(initialStock, println("Sorry, not enough milk"))
        coffeeBeans < 0 -> Pair(initialStock, println("Sorry, not enough coffee beans"))
        else -> Pair(intArrayOf(
            initWater - needWater,
            initMilk - needMilk,
            initCoffeeBeans - needCoffeeBeans,
            initCups - needCups,
            initMoney + needMoney
        ), println("I have enough resource, making you a coffee"))
    }
}

fun takeMoney(initialStock: IntArray): Pair<IntArray, Int> {
    val takeMoney = initialStock[initialStock.size - 1]
    initialStock[initialStock.size - 1] -= takeMoney

    return Pair(initialStock, takeMoney)
}

fun main() {
    // input is IntArray - water, milk, coffee beans, cups, and money
    var currentStock = intArrayOf(400, 540, 120, 9, 550)

    while (true) {
        print("\nWrite action (buy, fill, take, remaining, exit): ")
        val action = sc.next()
        if (action == "buy") {
            print("What do you want to buy? 1 - espresso, 2- latte, 3 - cappuccino, back - to main menu: ")
            val coffee = sc.next()
            if (coffee == "back")
                continue
            else {
                val coffeeToInt = coffee.toInt()
                val (sameStock, status) = makeCoffee(coffeeToInt, currentStock)
                currentStock = sameStock
            }
        } else if (action == "fill") {
            currentStock = addStock(currentStock)
        } else if (action == "take") {
            val (currentMoney, money) = takeMoney(currentStock)
            println("I gave you $$money")
            currentStock = currentMoney
        } else if (action == "remaining") {
            displayStock(currentStock)
        } else {
            break
        }
    }
//    val ingredientsStock = ingredientsStock()

//    print("Write how many cups of coffee you will need: ")
//    val cups = sc.nextInt()

//    ingredientCalculator(cups, ingredientsStock)
}
