package solvers

import kotlin.math.abs

class Day02Solver(input: List<String>) : DaySolver {
    val reports = input.map { it.split(' ').map { it.toInt()} }

    override fun part1(): Any {
        return reports.count{ it.isSafe() }
    }

    override fun part2(): Any {
        return reports.count { it.isSafeDampened() }
    }

    companion object {
        fun List<Int>.isSafe(): Boolean {
            if (size <= 1) return true
            val firstSign = sign(this[1] - this[0])

            for (i in 1..size-1) {
                val diff = this[i] - this[i-1]
                if (abs(diff) !in 1..3 || sign(diff) != firstSign) {
                    return false
                }
            }

            return true
        }

        fun List<Int>.isSafeDampened(): Boolean {
            return isSafe() || indices.any { this.without(it).isSafe() }
        }

        fun List<Int>.without(index: Int): List<Int> =
            subList(0, index) + subList(index+1, size)

        fun sign(int: Int): Int = when {
            int > 0 -> 1
            int < 0 -> -1
            else -> 0
        }
    }
}