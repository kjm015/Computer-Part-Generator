import kotlin.random.Random

fun main() {
    println("Hello, world!")
    println("I'm generating your graphics cards! Give me a second...\n")

    val cards = ArrayList<GraphicsCard>()

    for (i in 1..100) {
        cards.add(generateNvidiaCard())
    }

    cards.sortByDescending {
        it.generation
    }

    println("Done! Here are your cards:\n")

    cards.forEach {
        println(it.toString())
    }
}

val manufacturers = arrayListOf("Palit", "ASUS", "EVGA", "MSI", "Gigabyte", "Zotac", "PNY")
val initializers = arrayListOf("GT ", "GTX", "RTX")
val generations = arrayListOf(7, 9, 10, 16, 20)
val tiers = arrayListOf(10, 30, 50, 60, 70, 80, 90)
val extensions = arrayListOf("Ti", "Super")

fun generateNvidiaCard(): GraphicsCard {
    // Get random GPU manufacturer
    val manufacturer = manufacturers.shuffled().random()

    // Try to generate something random
    var generation = generations.shuffled().random()
    var tier = tiers.shuffled().random()
    var extension = ""

    // Find illegal generation and tier combinations
    while (
        (generation > 16 && tier < 60) ||
        (generation == 16 && tier !in 50..60) ||
        (generation == 10 && tier < 30) ||
        (generation == 9 && tier < 50)
    ) {
        generation = generations.shuffled().random()
        tier = tiers.shuffled().random()
    }

    // Get initializer based on tier and generation
    val initializer = when {
        generation > 16 && tier in 60..80 -> "RTX"
        generation < 20 && tier in 50..80 -> "GTX"
        tier > 80 -> "TITAN"
        else -> "GT"
    }

    if (Random.nextBoolean() || tier > 80) {
        extension = when (generation) {
            20 -> if (tier > 80) {
                "RTX"
            } else if (Random.nextBoolean() && tier == 80) {
                "Ti"
            } else {
                "Super"
            }

            16 -> if (Random.nextBoolean() && tier == 60) {
                "Ti"
            } else {
                "Super"
            }

            10 -> when (tier) {
                90 -> "XP"
                80 -> "Ti"
                70 -> "Ti"
                50 -> "Ti"
                else -> ""
            }
            9 -> when (tier) {
                90 -> "X"
                80 -> "Ti"
                else -> ""
            }
            7 -> when (tier) {
                90 -> if (Random.nextBoolean()) "Black" else "Z"
                80 -> "Ti"
                60 -> "Ti"
                50 -> "Ti"
                else -> ""
            }
            else -> ""
        }
    }

    return GraphicsCard(
        company = "Nvidia",
        manufacturer = manufacturer,
        embellishment = "",
        prefix = initializer,
        generation = generation,
        tier = tier,
        extension = extension,
        model = "",
        memoryAmountInGigabytes = 0
    )
}

