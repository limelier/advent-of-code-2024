package solvers

import common.grid.Dir
import common.grid.Grid
import common.grid.Pointer
import common.grid.Pos

class Day06Solver(input: List<String>) : DaySolver {
    private val data = input.map { it.toCharArray().toTypedArray() }.toTypedArray()
    private val visitedInPartOne = mutableListOf<Pos>()
    private val grid = Grid(data)
    private val initialPosition = grid.positions().first { grid[it] == '^' }

    override fun part1(): Any {
        var guard = initialPosition
        var dir = Dir.UP

        while (grid.contains(guard)) {
            val next = guard + dir.delta
            if (grid[next] == '#') {
                dir = dir.turnRight()
            } else {
                if (grid[guard] != 'X') {
                    visitedInPartOne += guard
                    grid[guard] = 'X'
                }
                guard = next
            }
        }

        println(grid.printableString())
        return visitedInPartOne.size
    }

    override fun part2(): Any {
        // only positions that the guard visits normally should be considered for the extra obstacles
        return visitedInPartOne
            .subList(1, visitedInPartOne.size) // exclude starting position
            .count { obstaclePosition ->
                grid[obstaclePosition] = '#'

                var moveTortoise = false
                var tortoise = Pointer(initialPosition, Dir.UP)
                var hare = Pointer(initialPosition, Dir.UP)

                var loop = false
                while (hare.pos in grid) {
                    val hareNext = hare.next()
                    hare = if (grid[hareNext.pos] == '#' || hareNext.pos == obstaclePosition) {
                        hare.copy(dir = hare.dir.turnRight())
                    } else {
                        hareNext
                    }

                    if (moveTortoise) {
                        val tortoiseNext = tortoise.next()
                        tortoise = if (grid[tortoiseNext.pos] == '#' || tortoiseNext.pos == obstaclePosition) {
                            tortoise.copy(dir = tortoise.dir.turnRight())
                        } else {
                            tortoiseNext
                        }
                        moveTortoise = false
                    } else {
                        moveTortoise = true
                    }

                    if (hare == tortoise) {
                        loop = true
                        break
                    }
                }

                grid[obstaclePosition] = '.'
                loop
            }
    }
}

private fun Dir.turnRight(): Dir = when(this) {
    Dir.UP -> Dir.RIGHT
    Dir.RIGHT -> Dir.DOWN
    Dir.DOWN -> Dir.LEFT
    Dir.LEFT -> Dir.UP
    else -> throw IllegalArgumentException("don't need non-orthogonal dirs")
}
