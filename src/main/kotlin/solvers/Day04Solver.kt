package solvers

import common.grid.Dir
import common.grid.Grid

class Day04Solver(input: List<String>) : DaySolver {
    private val grid = Grid(input.map { it.toCharArray().toTypedArray() }.toTypedArray())

    override fun part1(): Any {
        val word = "XMAS"
        return grid.positions().sumOf { pos ->
            if (grid[pos] != word.first()) { 0 }
            else { Dir.entries.count { dir ->
                for ((index, ch) in word.withIndex()) {
                    if (index == 0) continue
                    if (grid[pos + dir.delta * index] != ch) return@count false
                }
                true
            }}
        }
    }

    override fun part2(): Any {
        return grid.positions().count { pos ->
            if (grid[pos] != 'A') { false }
            else {
                val upLeft = grid[pos + Dir.UP_LEFT.delta]
                val upRight = grid[pos + Dir.UP_RIGHT.delta]
                val downLeft = grid[pos + Dir.DOWN_LEFT.delta]
                val downRight = grid[pos + Dir.DOWN_RIGHT.delta]

                val firstDiagonal = upLeft == 'M' && downRight == 'S' || upLeft == 'S' && downRight == 'M'
                val secondDiagonal = upRight == 'M' && downLeft == 'S' || upRight == 'S' && downLeft == 'M'

                firstDiagonal && secondDiagonal
            }
        }
    }
}