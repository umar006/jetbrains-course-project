package machine
import java.util.Scanner

val sc = Scanner(System.`in`)

fun ingredientCalculator(cups: Int) {
    val ingredients = listOf<Int>(200, 50, 15)
    val (water, milk, coffeeBeans) = ingredients.map{it * cups}

    println("For $cups of coffee you will need:")
    println("$water ml of water")
    println("$milk ml of milk")
    println("$coffeeBeans g of coffee beans")
}

fun main() {
    print("Write how many cups of coffee you will need: ")
    val cups = sc.nextInt()
    ingredientCalculator(cups)
}
