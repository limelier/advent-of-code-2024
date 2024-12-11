package solvers

class Day11Solver(input: List<String>) : DaySolver {
    val initialStones = input.first().split(" ").map { it.toLong() }

    override fun part1(): Any {
        var stones = initialStones

        repeat(25) {
            stones = stones.flatMap { blink(it) }
        }

        return stones.size
    }

    override fun part2(): Any {
        return initialStones.sumOf { it.countAfter(75) }
    }
}

private fun blink(stone: Long): List<Long> = when {
    stone == 0L -> listOf(1)

    stone.toString().length % 2 == 0 -> {
        val digits = stone.toString()
        val length = digits.length
        val first = digits.substring(0, length/2).toLong()
        val second = digits.substring(length/2, length).toLong()
        listOf(first, second)
    }

    else -> listOf(stone * 2024)
}

private fun Long.countAfter(blinks: Int): Long = if (blinks <= 0) 1 else {
    if (this < LOOKUP_SIZE) {
        val lookedUp = lookup[blinks - 1][this.toInt()]
        if (lookedUp == -1L) {
            val computed = blink(this).sumOf { it.countAfter(blinks - 1) }
            lookup[blinks - 1][this.toInt()] = computed
            computed
        } else {
            lookedUp
        }
    } else {
        blink(this).sumOf { it.countAfter(blinks - 1) }
    }
}

private const val LOOKUP_SIZE = 100_000
private val lookup = Array(75) { LongArray(LOOKUP_SIZE) { -1L } }