package solvers

class Day07Solver(input: List<String>) : DaySolver {
    private val equations: List<Equation> = input.map { line ->
        val (before, after) = line.split(": ")
        val testValue = before.toLong()
        val terms = after.split(" ").map { it.toLong() }
        Equation(testValue, terms.subList(1, terms.size), terms[0])
    }

    private val partitions = equations.partition { it.possible(::add, ::mul) }
    val part1 = partitions.first.sumOf { it.testValue }
    // part 2: if an equation is possible with two operators, it's possible with three. only check the ones that aren't
    val part2 = part1 + partitions.second.filter { it.possible(::add, ::mul, ::cat) }.sumOf { it.testValue }

    override fun part1(): Any {
        return part1
    }

    override fun part2(): Any {
        return part2
    }
}

private data class Equation(
    val testValue: Long,
    val terms: List<Long>,
    val accumulator: Long,
) {
    fun applyOperator(operator: Operator): Equation = Equation(
        testValue,
        terms.subList(1, terms.size),
        operator(accumulator, terms[0])
    )

    fun possible(vararg operators: Operator): Boolean = if (terms.isEmpty()) {
        testValue == accumulator
    } else {
        testValue >= accumulator && operators.any { op -> applyOperator(op).possible(*operators) }
    }
}

private typealias Operator = (first: Long, second: Long) -> Long

private fun add(first: Long, second: Long): Long = first + second
private fun mul(first: Long, second: Long): Long = first * second
private fun cat(first: Long, second: Long): Long = (first.toString() + second.toString()).toLong()