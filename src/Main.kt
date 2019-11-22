import kotlin.random.Random

fun main() {
    println("Hello, world!")
    println("I'm generating your graphics cards! Give me a second...\n")

    val cards = ArrayList<GraphicsCard>()

    // Generate 100 cards
    for (i in 1..100) {
        cards.add(generateNvidiaCard())
    }

    // Sort cards by generation
    cards.sortByDescending {
        it.generation
    }

    println("Done! Here are your cards:\n")

    // Print out the card details
    cards.forEach {
        println(it.toString())
    }

    println("\nI'm also going to give you some processors.")

    val processors = ArrayList<CPU>()

    for (i in 1..20) {
        processors.add(generateRyzenProcessor())
    }

    println("Here you go!\n")

    processors.sortedBy { it.generation }.sortedBy { it.tier }.forEach {
        println(it)
    }
}


fun generateRyzenProcessor(): CPU {
    val tiers = arrayListOf(3, 5, 7, 9)
    val generations = arrayListOf(1, 2, 3)
    val extensions = arrayListOf("", "X", "G", "GE")

    var tier = tiers.shuffled().random()
    var generation = generations.shuffled().random()

    while ((generation < 3 && tier > 7)) {
        tier = tiers.shuffled().random()
        generation = generations.shuffled().random()
    }

    val serial = when (tier) {
        3 -> when {
            generation < 3 && Random.nextBoolean() -> 300
            else -> 200
        }

        5 -> when {
            Random.nextBoolean() -> 400
            Random.nextBoolean() -> 500
            else -> 600
        }

        7 -> if (Random.nextBoolean() || generation == 2) 700 else 800

        9 -> if (Random.nextBoolean()) 900 else 950

        else -> 0
    }

    val extension = when (tier) {
        3 -> when {
            serial == 200 && generation < 2 -> ""
            serial == 200 && generation > 1 -> "G"
            serial == 300 -> "X"
            else -> ""
        }

        5 -> when {
            generation < 2 && serial == 400 -> ""
            generation > 1 && serial == 400 -> "G"
            generation < 3 && serial == 500 -> "X"
            serial == 600 || (generation > 2 && serial == 500) -> if (Random.nextBoolean()) "X" else ""
            else -> ""
        }

        7 -> if ((generation > 2 || serial > 700) || Random.nextBoolean()) "X" else ""

        9 -> if (serial == 900 && Random.nextBoolean()) "" else "X"

        else -> ""
    }


    return CPU(
        company = "AMD",
        series = "Ryzen",
        tier = tier,
        generation = generation,
        serial = serial,
        extension = extension
    )
}

fun generateNvidiaCard(): GraphicsCard {
    val manufacturers = arrayListOf("Palit", "ASUS", "EVGA", "MSI", "Gigabyte", "Zotac", "PNY")
    val generations = arrayListOf(7, 9, 10, 16, 20)
    val tiers = arrayListOf(10, 30, 50, 60, 70, 80, 90)

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

