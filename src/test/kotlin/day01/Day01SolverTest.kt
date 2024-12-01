package day01

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class Day01SolverTest {
    val testInputUrl = javaClass.getResource("test.txt")!!
    val solver = Day01Solver(testInputUrl)

    @Test
    fun `part 1 example should match`() {
        solver.part1() shouldBe 11
    }

    @Test
    fun `part 2 example should match`() {
        solver.part2() shouldBe 31
    }
}