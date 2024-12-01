package common

import java.net.URL

abstract class DaySolver(
    resourceUrlOverride: URL? = null
) {
    internal val input =
        (resourceUrlOverride ?: javaClass.getResource("input.txt")!!)
        .readText()
        .trim() // remove trailing newline

    abstract fun part1(): Any
    abstract fun part2(): Any

    fun print() {
        try {
            println("Part 1: ${part1()}")
        } catch (_: NotImplementedError) {
            println("Part 1 not done")
        }

        try {
            println("Part 2: ${part2()}")
        } catch (_: NotImplementedError) {
            println("Part 2 not done")
        }
    }
}