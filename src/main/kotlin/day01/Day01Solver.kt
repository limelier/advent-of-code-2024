package day01

import common.DaySolver
import java.net.URL
import kotlin.collections.map
import kotlin.collections.unzip
import kotlin.math.abs
import kotlin.text.lines
import kotlin.text.split
import kotlin.text.toInt
import kotlin.text.toRegex
import kotlin.to

fun main() {
    Day01Solver().print()
}

class Day01Solver(
    resourceUrlOverride: URL? = null
) : DaySolver(resourceUrlOverride) {
    val lists = input
        .lines()
        .map { it.split("""\s+""".toRegex()) }
        .map { it[0].toInt() to it[1].toInt() }
        .unzip()
    // destructuring declarations are only allowed for local variables/values ;-;
    val left = lists.first
    val right = lists.second

    override fun part1(): Any {
        val leftSorted = left.sorted()
        val rightSorted = right.sorted()

        val distance = (leftSorted zip rightSorted)
            .sumOf { abs(it.first - it.second) }

        return distance
    }

    override fun part2(): Any {
        return left.sumOf { leftNum ->
            leftNum * right.count { it == leftNum }
        }
    }
}