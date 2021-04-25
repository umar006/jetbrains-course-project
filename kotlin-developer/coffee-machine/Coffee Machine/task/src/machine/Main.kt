package machine
import java.util.Scanner

val sc = Scanner(System.`in`)

fun ingredientCalculator(cups: Int, ingredientsStock: IntArray) {
    val (waterStock, milkStock, coffeeBeansStock) = ingredientsStock
    val (water, milk, coffeeBeans) = intArrayOf(200, 50, 15)

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
        ${stock[4]} of money
    """.trimIndent())
}

fun makeCoffee(coffee: Int, initialStock: IntArray): IntArray {
    val (needWater, needMilk, needCoffeeBeans, needCups, needMoney) =
        when (coffee) {
            1 -> intArrayOf(250, 0, 16, 1, 4)
            2 -> intArrayOf(350, 75, 20, 1, 7)
            else -> intArrayOf(200, 100, 12, 1, 6)
        }
    val (initWater, initMilk, initCofeeBeans, initCups, initMoney) = initialStock

    return intArrayOf(
        initWater - needWater,
        initMilk - needMilk,
        initCofeeBeans - needCoffeeBeans,
        initCups - needCups,
        initMoney + needMoney
    )
}

fun takeMoney(initialStock: IntArray): Pair<IntArray, Int> {
    val takeMoney = initialStock[initialStock.size - 1]
    initialStock[initialStock.size - 1] -= takeMoney

    return Pair(initialStock, takeMoney)
}

fun main() {
    // input is IntArray - water, milk, coffee beans, cups, and money
    val initialStock = intArrayOf(400, 540, 120, 9, 550)
    displayStock(initialStock)

    print("\nWrite action (buy, fill, take): ")
    val action = sc.next()
    if (action == "buy") {
        print("What do you want to buy? 1 - espresso, 2- latte, 3 - cappuccino: ")
        val coffee = sc.nextInt()
        val currentStock = makeCoffee(coffee, initialStock)
        displayStock(currentStock)
    } else if (action == "fill") {
        val currentStock = addStock(initialStock)
        displayStock(currentStock)
    } else if (action == "take") {
        val (currentStock, money) = takeMoney(initialStock)
        println("I gave you $$money")
        displayStock(currentStock)
    }
//    val ingredientsStock = ingredientsStock()

//    print("Write how many cups of coffee you will need: ")
//    val cups = sc.nextInt()

//    ingredientCalculator(cups, ingredientsStock)
}
