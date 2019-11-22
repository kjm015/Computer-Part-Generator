data class CPU(
    val company: String,
    val series: String,
    val tier: Int,
    val generation: Int,
    val serial: Int,
    val extension: String
) {
    override fun toString(): String {
        return if (company.equals("Intel", ignoreCase = true))
            "$company ${series}${tier}-${generation}${serial}${extension}"
        else
            "$company $series $tier ${generation}${serial}${extension}"
    }
}