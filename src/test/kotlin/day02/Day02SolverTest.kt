package day02

import day02.Day02Solver.Companion.isSafe
import day02.Day02Solver.Companion.without
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02SolverTest {
    val testInputUrl = javaClass.getResource("test.txt")!!
    val solver = Day02Solver(testInputUrl)

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

        solver.reports.map { it.isSafe(useDampener = true) } shouldBe listOf(
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
        report.isSafe(useDampener = true) shouldBe true
    }

    @Test
    fun `dampener should work on last element`() {
        val report = listOf(1, 2, 3, 4, 5, 1000)
        report.isSafe(useDampener = true) shouldBe true
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