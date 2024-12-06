package solvers

import common.grid.Dir
import common.grid.Grid
import common.grid.Pos

class Day06Solver(input: List<String>) : DaySolver {
    private val data = input.map { it.toCharArray().toTypedArray() }.toTypedArray()
    private val visitedInPartOne = mutableListOf<Pos>()
    private val part1Grid = Grid(data.deepClone())
    private val initialPosition = part1Grid.positions().first { part1Grid[it] == '^' }

    override fun part1(): Any {
        var guard = initialPosition
        var dir = Dir.UP

        while (part1Grid.contains(guard)) {
            val next = guard + dir.delta
            if (part1Grid[next] == '#') {
                dir = dir.turnRight()
            } else {
                if (part1Grid[guard] != 'X') {
                    visitedInPartOne += guard
                    part1Grid[guard] = 'X'
                }
                guard = next
            }
        }

        println(part1Grid.printableString())
        return visitedInPartOne.size
    }

    override fun part2(): Any {
        val grid = Grid(data.deepClone())
        return visitedInPartOne
            .subList(1, visitedInPartOne.size) // exclude starting position
            .count { obstaclePosition ->
                grid[obstaclePosition] = '#'

                var guard = initialPosition
                var dir = Dir.UP
                val visited: MutableSet<Pair<Pos, Dir>> = mutableSetOf()

                var itLoops = true
                while (guard to dir !in visited) {
                    visited += guard to dir

                    // if guard exits grid, there is no loop
                    if (!grid.contains(guard)) {
                        itLoops = false
                        break
                    }

                    val next = guard + dir.delta
                    if (grid[next] == '#') {
                        dir = dir.turnRight()
                    } else {
                        guard = next
                    }
                }

                grid[obstaclePosition] = '.'

                itLoops
            }
    }
}

private fun Array<Array<Char>>.deepClone(): Array<Array<Char>> = this.map { it.clone() }.toTypedArray()

private fun Dir.turnRight(): Dir = when(this) {
    Dir.UP -> Dir.RIGHT
    Dir.RIGHT -> Dir.DOWN
    Dir.DOWN -> Dir.LEFT
    Dir.LEFT -> Dir.UP
    else -> throw IllegalArgumentException("don't need non-orthogonal dirs")
}