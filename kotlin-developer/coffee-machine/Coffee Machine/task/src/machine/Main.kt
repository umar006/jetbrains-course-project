package machine
import java.util.Scanner

val sc = Scanner(System.`in`)

fun ingredientCalculator(cups: Int, ingredientsAmount: IntArray) {
    val (waterAmount, milkAmount, coffeeBeansAmount) = ingredientsAmount

    val baseIngredients = intArrayOf(200, 50, 15)

    val cupsStock = intArrayOf(waterAmount / 200, milkAmount / 50, coffeeBeansAmount / 15).minOf{it}

    when {
        cupsStock == cups -> println("Yes, I can make that amount of coffee")
        cupsStock - cups >= 1 -> println("Yes, I can make that amount of coffee (and even ${cupsStock - cups} more than that")
        else -> println("No, I can make only $cupsStock cups of coffee")
    }

//    println("For $cups of coffee you will need:")
//    println("$water ml of water")
//    println("$milk ml of milk")
//    println("$coffeeBeans g of coffee beans")
}

fun amountOfIngredients(): IntArray {
    print("Write how many ml of water the coffee machine has: ")
    val water = sc.nextInt()
    print("Write how many ml of milk the coffee machine has: ")
    val milk = sc.nextInt()
    print("Write how many grams of coffee beans the coffee machine has: ")
    val coffeeBeans = sc.nextInt()

    return intArrayOf(water, milk, coffeeBeans)
}

fun main() {
    val ingredientsAmount = amountOfIngredients()

    print("Write how many cups of coffee you will need: ")
    val cups = sc.nextInt()

    ingredientCalculator(cups, ingredientsAmount)
}
