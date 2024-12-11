package solvers

import common.grid.Dir
import common.grid.Grid
import common.grid.Pos

class Day10Solver(input: List<String>) : DaySolver {
    private val map = Grid(input.map { it.map { if (it == '.') 100 else it.digitToInt() }.toTypedArray() }.toTypedArray())
    private val trailheads = map.positions().filter { map[it] == 0 }

    override fun part1(): Any {
        return trailheads.sumOf { it.reachablePeaks().size }
    }

    override fun part2(): Any {
        return trailheads.sumOf { it.rating() }
    }

    fun Pos.reachablePeaks(): Set<Pos> {
        val height = map[this]!!
        if (height == 9) return setOf(this)

        return Dir.orthogonal
            .map { this + it.delta }
            .filter { map[it] == height + 1}
            .flatMapTo(mutableSetOf()) { it.reachablePeaks() }
    }

    fun Pos.rating(): Int {
        val height = map[this]!!
        if (height == 9) return 1

        return Dir.orthogonal
            .map { this + it.delta }
            .filter { map[it] == height + 1}
            .sumOf { it.rating() }
    }
}
