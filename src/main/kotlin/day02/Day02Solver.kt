package day02

import common.DaySolver
import java.net.URL
import kotlin.math.abs

fun main() {
    Day02Solver().print()
}

class Day02Solver(testInputUrl: URL? = null) : DaySolver(testInputUrl) {
    internal val reports = input
        .lines()
        .map { it.split(' ').map { it.toInt()} }

    override fun part1(): Any {
        return reports.count{ it.isSafe() }
    }

    override fun part2(): Any {
        reports.forEach {
            if (it.isSafe(useDampener = true) != it.isSafeDampenedNaive()) {
                println("smart approach doesn't work for $it")
            }
        }
        return reports.count { it.isSafeDampenedNaive() }
    }

    companion object {
        internal fun List<Int>.isSafe(useDampener: Boolean = false): Boolean {
            if (size <= 1) return true
            val firstSign = sign(this[1] - this[0])

            for (i in 1..size-1) {
                val diff = this[i] - this[i-1]
                if (abs(diff) !in 1..3 || sign(diff) != firstSign) {
                    return if (useDampener) {
                        this.without(i).isSafe(useDampener = false) ||
                                this.without(i-1).isSafe(useDampener = false)
                    } else {
                        false
                    }
                }
            }

            return true
        }

        private fun List<Int>.isSafeDampenedNaive(): Boolean {
            return isSafe(useDampener = false) || indices.any { this.without(it).isSafe(useDampener = false) }
        }

        internal fun List<Int>.without(index: Int): List<Int> =
            subList(0, index) + subList(index+1, size)

        private fun sign(int: Int): Int {
            return if (int == 0) 0
            else if (int < 0) -1
            else 1
        }
    }
}