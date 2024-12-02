package solvers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import solvers.Day02Solver.Companion.isSafe
import solvers.Day02Solver.Companion.isSafeDampened
import solvers.Day02Solver.Companion.without

class Day02SolverTest {
    val testInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().lines()
    val solver = Day02Solver(testInput)

    @Test
    fun `part 1 example should match`() {
        solver.part1() shouldBe 2

        solver.reports.map { it.isSafe() } shouldBe listOf(
            true,
            false,
            false,
            false,
            false,
            true,
        )
    }

    @Test
    fun `part 2 example should match`() {
        solver.part2() shouldBe 4

        solver.reports.map { it.isSafeDampened() } shouldBe listOf(
            true,
            false,
            false,
            true,
            true,
            true,
        )
    }

    @Test
    fun `dampener should work on first element`() {
        val report = listOf(1000, 1, 2, 3, 4, 5)
        report.isSafeDampened() shouldBe true
    }

    @Test
    fun `dampener should work on last element`() {
        val report = listOf(1, 2, 3, 4, 5, 1000)
        report.isSafeDampened() shouldBe true
    }

    @Test
    fun `without() removes right element`() {
        listOf(1, 2, 10, 3, 4).without(2) shouldBe listOf(1, 2, 3, 4)
    }

    @Test
    fun `without() works properly on first element`() {
        listOf(10, 1, 2, 3, 4).without(0) shouldBe listOf(1, 2, 3, 4)
    }

    @Test
    fun `without() works properly on last element`() {
        listOf(1, 2, 3, 4, 10).without(4) shouldBe listOf(1, 2, 3, 4)
    }
}