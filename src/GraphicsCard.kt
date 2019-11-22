data class GraphicsCard(
    var company: String,

    var manufacturer: String,
    var embellishment: String = "",

    var prefix: String,

    var generation: Int,
    var tier: Int,

    var extension: String = "",

    var model: String = "",
    var memoryAmountInGigabytes: Int
) {
    override fun toString(): String = if (!prefix.equals("TITAN", ignoreCase = true)) {
        "$company $prefix $generation$tier $extension"
    } else if (prefix.equals("TITAN", ignoreCase = true) && generation < 10) {
        "$company GTX $prefix $extension"
    } else {
        "$company $prefix $extension"
    }
}
