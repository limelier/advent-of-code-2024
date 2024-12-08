package solvers

import common.grid.Grid
import common.grid.Pos

class Day08Solver(input: List<String>) : DaySolver {
    val grid = Grid(input.map { it.toCharArray().toTypedArray() }.toTypedArray())
    val frequenciesToAntennas = mutableMapOf<Char, MutableList<Pos>>()

    init {
        for (pos in grid.positions()) {
            if (grid[pos] != '.') {
                frequenciesToAntennas.getOrPut(grid[pos]!!) { mutableListOf() } += pos
            }
        }
    }

    override fun part1(): Any {
        val antinodes = mutableSetOf<Pos>()
        for (antennas in frequenciesToAntennas.values) {
            for ((i, a1) in antennas.withIndex()) {
                for (a2 in antennas.subList(i+1, antennas.size)) {
                    val delta = a2 - a1

                    val anti1 = a1 - delta
                    if (anti1 in grid) antinodes += anti1

                    val anti2 = a2 + delta
                    if (anti2 in grid) antinodes += anti2
                }
            }
        }

        return antinodes.size
    }

    override fun part2(): Any {
        val antinodes = mutableSetOf<Pos>()
        for (antennas in frequenciesToAntennas.values) {
            for ((i, a1) in antennas.withIndex()) {
                for (a2 in antennas.subList(i+1, antennas.size)) {
                    val delta = a2 - a1

                    // go one way...
                    var anti1 = a1
                    while (anti1 in grid) {
                        antinodes += anti1
                        anti1 -= delta
                    }

                    // then the other
                    var anti2 = a2
                    while (anti2 in grid) {
                        antinodes += anti2
                        anti2 += delta
                    }
                }
            }
        }

        return antinodes.size
    }
}
