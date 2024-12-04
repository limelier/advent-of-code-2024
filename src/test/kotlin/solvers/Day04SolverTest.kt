package solvers

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class Day04SolverTest {
    @Test
    fun `part 1 works on sample`() {
        val sample = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()
        val solver = Day04Solver(sample.lines())

        solver.part1() shouldBe 18
    }

    @Test
    fun `part 1 works on single word horizontally`() {
        val data = """
            ......
            .XMAS.
            ......
        """.trimIndent()
        val solver = Day04Solver(data.lines())

        solver.part1() shouldBe 1
    }

    @Test
    fun `part 2 works on sample`() {
        val sample = """
            .M.S......
            ..A..MSMS.
            .M.S.MAA..
            ..A.ASMSM.
            .M.S.M....
            ..........
            S.S.S.S.S.
            .A.A.A.A..
            M.M.M.M.M.
            ..........
        """.trimIndent()
        val solver = Day04Solver(sample.lines())

        solver.part2() shouldBe 9
    }
}