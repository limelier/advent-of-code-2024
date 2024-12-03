package solvers

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class Day03SolverTest {

    @Test
    fun `part 1 works on the sample`() {
        val solver = Day03Solver(listOf(
            "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
        ))

        solver.part1() shouldBe 161
    }

    @Test
    fun `part 2 works on the sample`() {
        val solver = Day03Solver(listOf(
            "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
        ))

        solver.part2() shouldBe 48
    }
}