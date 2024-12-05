package solvers

class Day05Solver(input: List<String>) : DaySolver {
    private val correctlyOrdered: List<List<Int>>
    private val correctedIncorrectlyOrdered: List<List<Int>>

    init {
        val separator = input.indexOf("")

        val pageComparator = input
            .subList(0, separator)
            .map { line ->
                val pages = line.split('|').map { it.toInt() }
                pages[0] to pages[1]
            }
            .toSet()
            .let { PageComparator(it) }

        val updates = input
            .subList(separator + 1, input.size)
            .map { line -> line.split(",").map { it.toInt() } }

        val correctlyOrdered = mutableListOf<List<Int>>()
        val correctedIncorrectlyOrdered = mutableListOf<List<Int>>()
        updates.forEach { list ->
            val sorted = list.sortedWith(pageComparator)
            if (list == sorted) {
                correctlyOrdered += list
            } else {
                correctedIncorrectlyOrdered += sorted
            }
        }

        this.correctlyOrdered = correctlyOrdered
        this.correctedIncorrectlyOrdered = correctedIncorrectlyOrdered
    }

    override fun part1(): Any {
        return correctlyOrdered.sumOf { it.middle() }
    }

    override fun part2(): Any {
        return correctedIncorrectlyOrdered.sumOf { it.middle() }
    }
}

private class PageComparator(
    private val orderingRules: Set<Pair<Int, Int>>
) : Comparator<Int> {
    override fun compare(p0: Int?, p1: Int?): Int {
        if (p0 to p1 in orderingRules) return -1
        if (p1 to p0 in orderingRules) return 1
        return 0
    }
}

private fun List<Int>.middle(): Int = this[size / 2]