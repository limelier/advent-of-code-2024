package solvers

class Day07Solver(input: List<String>) : DaySolver {
    private val equations: List<Equation> = input.map { line ->
        val (before, after) = line.split(": ")
        val testValue = before.toLong()
        val terms = after.split(" ").map { it.toLong() }
        Equation(testValue, terms)
    }

    override fun part1(): Any {
        return equations
            .filter { it.possible(::add, ::mul) }
            .sumOf { it.testValue }
    }

    override fun part2(): Any {
        return equations
            .filter { it.possible(::add, ::mul, ::cat) }
            .sumOf { it.testValue }
    }
}

private data class Equation(
    val testValue: Long,
    val terms: List<Long>
) {
    fun applyOperator(operator: Operator): Equation =
        Equation(testValue, listOf(operator(terms[0], terms[1])) + terms.subList(2, terms.size))

    fun possible(vararg operators: Operator): Boolean = if (terms.size == 1) {
        testValue == terms[0]
    } else {
        testValue >= terms[0] && operators.any { op -> applyOperator(op).possible(*operators) }
    }
}

private typealias Operator = (first: Long, second: Long) -> Long

private fun add(first: Long, second: Long): Long = first + second
private fun mul(first: Long, second: Long): Long = first * second
private fun cat(first: Long, second: Long): Long = (first.toString() + second.toString()).toLong()