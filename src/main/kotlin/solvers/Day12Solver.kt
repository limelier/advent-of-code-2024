package solvers

import common.grid.Dir
import common.grid.Grid
import common.grid.Pointer
import common.grid.Pos

class Day12Solver(input: List<String>) : DaySolver {
    val plantMap = Grid(input.map { it.toCharArray().toTypedArray() }.toTypedArray())
    val visited = Grid(Array(plantMap.size.y) { Array(plantMap.size.x) { false } })

    override fun part1(): Any {
        var totalPrice = 0

        for (pos in plantMap.positions()) {
            if (visited[pos]!!) continue
            val stats = floodFill(pos)
            totalPrice += stats.tiles.size * stats.fences.size
            println("${plantMap[pos]} -> $stats")
        }

        return totalPrice
    }

    override fun part2(): Any {
        TODO()
    }

    private fun floodFill(pos: Pos): RegionStats {
        visited[pos] = true

        val neighbors = Dir.orthogonal.map { it to pos + it.delta }
        val (same, different) = neighbors.partition { plantMap[it.second] == plantMap[pos] }

        val neighborStats = same.filter { visited[it.second] == false }.map { floodFill(it.second) }

        return RegionStats(
            fences = (different.map { Fence(pos, it.first) } + neighborStats.flatMap { it.fences }).toSet(),
            tiles = (neighborStats.flatMap { it.tiles } + pos).toSet()
        )
    }
}

private data class RegionStats(
    val fences: Set<Fence>,
    val tiles: Set<Pos>,
)

private typealias Fence = Pointer
