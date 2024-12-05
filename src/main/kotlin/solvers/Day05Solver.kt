package solvers

class Day05Solver(input: List<String>) : DaySolver {
    private val pageComparator: Comparator<Int>
    private val correctlyOrdered: List<List<Int>>
    private val incorrectlyOrdered: List<List<Int>>

    init {
        val separator = input.indexOf("")

        pageComparator = input
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
        val incorrectlyOrdered = mutableListOf<List<Int>>()
        updates.forEach { list ->
            if (list.isSortedWith(pageComparator)) {
                correctlyOrdered += list
            } else {
                incorrectlyOrdered += list
            }
        }

        this.correctlyOrdered = correctlyOrdered
        this.incorrectlyOrdered = incorrectlyOrdered
    }

    override fun part1(): Any {
        return correctlyOrdered.sumOf { it.middle() }
    }

    override fun part2(): Any {
        return incorrectlyOrdered.sumOf { it.toMutableList().getFromSortedWith(it.size / 2, pageComparator) }
    }
}

private class PageComparator(
    private val orderingRules: Set<Pair<Int, Int>>
) : Comparator<Int> {
    override fun compare(p0: Int?, p1: Int?): Int {
        return if (p0 to p1 in orderingRules) -1
        else if (p1 to p0 in orderingRules) 1
        else 0
    }
}

private fun List<Int>.isSortedWith(comparator: Comparator<Int>): Boolean = this
    .asSequence()
    .zipWithNext()
    .all { comparator.compare(it.first, it.second) == -1}

private fun List<Int>.middle(): Int = this[size / 2]

/**
 * Find the element at [index] if list was sorted by [comparator]
 */
private tailrec fun MutableList<Int>.getFromSortedWith(index: Int, comparator: Comparator<Int>): Int {
    if (index !in indices) throw IllegalArgumentException("index $index not in list of size $size")

    // quickselect algorithm
    val pivotIndex = partition(comparator)

    return if (pivotIndex == index)
        this[pivotIndex]
    else if (pivotIndex > index)
        subList(0, pivotIndex).getFromSortedWith(index, comparator)
    else
        subList(pivotIndex + 1, size).getFromSortedWith(index - pivotIndex - 1, comparator)
}

/**
 * Partition around random pivot by [comparator], with smaller elements before and larger after, and return the pivot's
 * index.
 */
private fun MutableList<Int>.partition(comparator: Comparator<Int>): Int {
    val pivotIndex = indices.random()
    val pivot = this[pivotIndex]

    var i = 0
    var j = size - 1
    while (i < j) {
        while (comparator.compare(this[i], pivot) == -1) i++
        while (comparator.compare(this[j], pivot) == 1) j--
        this[i] = this[j].also { this[j] = this[i] }
    }
    return j
}