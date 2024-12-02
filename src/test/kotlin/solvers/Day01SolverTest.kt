package solvers

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class Day01SolverTest {
    val testInput = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent().lines()
    val solver = Day01Solver(testInput)

    @Test
    fun `part 1 example should match`() {
        solver.part1() shouldBe 11
    }

    @Test
    fun `part 2 example should match`() {
        solver.part2() shouldBe 31
    }
}