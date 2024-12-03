package solvers

import kotlin.collections.flatten

class Day03Solver(private val input: List<String>) : DaySolver {
    private val mul = """mul\((?<left>\d+),(?<right>\d+)\)""".toRegex()
    private val instruction = """(${mul.pattern})|(do\(\))|(don't\(\))""".toRegex()

    override fun part1(): Any {
        return input.map {
            mul.findAll(it).toList()
        }
            .flatten()
            .sumOf { it.mul() }
    }

    override fun part2(): Any {
        val matches = input.map {
            instruction.findAll(it).toList()
        }.flatten()

        var sum = 0
        var enabled = true
        matches.forEach { when(it.value) {
            "do()" -> {
                enabled = true
            }
            "don't()" -> {
                enabled = false
            }
            else -> if (enabled) {
                sum += it.mul()
            }
        }}

        return sum
    }

    private fun MatchResult.mul(): Int = groups["left"]!!.value.toInt() * groups["right"]!!.value.toInt()
}