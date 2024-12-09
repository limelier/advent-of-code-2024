import solvers.Day09Solver
import solvers.DaySolver
import java.io.File
import kotlin.time.measureTime

fun main() {
    val input = File("input.txt")
        .readText()
        .trimEnd() // remove trailing newline
        .lines()

    val time = measureTime {
        val solver: DaySolver = Day09Solver(input)

        try {
            println("Part 1:\n${solver.part1()}")
        } catch (_: NotImplementedError) {
            println("Part 1 not done")
        }

        println()

        try {
            println("Part 2:\n${solver.part2()}")
        } catch (_: NotImplementedError) {
            println("Part 2 not done")
        }
    }
    println()
    println("Execution took $time")
}
