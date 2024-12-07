package solvers

import common.grid.Dir
import common.grid.Grid
import common.grid.Pointer
import common.grid.Pos
import common.grid.taxicabDistance

class Day06Solver(input: List<String>) : DaySolver {
    private val data = input.map { it.toCharArray().toTypedArray() }.toTypedArray()
    private val visitedInPartOne = mutableListOf<Pos>()
    private val grid = Grid(data)
    private val teleportGrids: Map<Dir, Grid<Pos>> = generateTeleportGrids(grid)
    private val initialPosition = grid.positions().first { grid[it] == '^' }

    override fun part1(): Any {
        var guard = Pointer(initialPosition, Dir.UP)

        while (grid.contains(guard.pos)) {
            val next = guard.next()
            if (grid[next.pos] == '#') {
                guard = guard.turn(guard.dir.turnedRight())
            } else {
                if (grid[guard.pos] != 'X') {
                    visitedInPartOne += guard.pos
                    grid[guard.pos] = 'X'
                }
                guard = next
            }
        }

        return visitedInPartOne.size
    }

    override fun part2(): Any {
        val viableWallLocations = visitedInPartOne
            .subList(1, visitedInPartOne.size) // cannot place wall on starting position
            .count { wallPos ->
                var guard = Pointer(initialPosition, Dir.UP)
                val history = mutableSetOf<Pointer>()

                while (guard.pos in grid) {
                    if (guard in history) return@count true
                    history += guard

                    guard = Pointer(
                        if (guard.isFacing(wallPos)) {
                            val posInFrontOfWall = wallPos - guard.dir.delta
                            val posByTeleportGrid = teleportGrids[guard.dir]!![guard.pos]!!

                            val distanceToNewWall = taxicabDistance(guard.pos, posInFrontOfWall)
                            val distanceToOldWall = taxicabDistance(guard.pos, posByTeleportGrid)

                            if (distanceToNewWall < distanceToOldWall) posInFrontOfWall else posByTeleportGrid
                        } else {
                            teleportGrids[guard.dir]!![guard.pos]!!
                        },
                        guard.dir.turnedRight()
                    )
                }

                false
            }
        return viableWallLocations
    }
}

/**
 * Generate a teleport grid for every orthogonal direction
 *
 * A teleport grid for direction 'dir' is a grid of coordinates that a pointer at each position in [grid] facing 'dir'
 * would end up at if travelling forwards until it hit a wall or exited the [grid]'s bounds.
 */
private fun generateTeleportGrids(grid: Grid<Char>): Map<Dir, Grid<Pos>> = Dir.orthogonal.associate { dir ->
    val teleportGrid = Grid(Array(grid.size.y) { Array<Pos>(grid.size.x) { Pos(0, 0) } })

    // generate a wall of pointers at a grid's edge that will sweep backwards along dir
    val pointers: Array<Pointer> = when(dir) {
        Dir.UP -> Array(grid.size.x) { x -> Pointer(Pos(x, 0), Dir.UP) } // top edge
        Dir.RIGHT -> Array(grid.size.y) { y -> Pointer(Pos(grid.size.x - 1, y), Dir.RIGHT) } // right edge
        Dir.DOWN -> Array(grid.size.x) { x -> Pointer(Pos(x, grid.size.y - 1), Dir.DOWN) } // bottom edge
        Dir.LEFT -> Array(grid.size.y) { y -> Pointer(Pos(0, y), Dir.LEFT) } // left edge
        else -> throw IllegalArgumentException("don't need non-orthogonal dirs")
    }

    while (pointers[0].pos in grid) {
        for (i in pointers.indices) {
            val pointer = pointers[i]
            if (grid[pointer.pos] != '#') { // ignore walls
                val next = pointer.next()

                if (next.pos in grid) {
                    if (grid[next.pos] == '#') teleportGrid[pointer.pos] = pointer.pos  // if next is a wall, don't move
                    else teleportGrid[pointer.pos] = teleportGrid[next.pos]!! // otherwise, teleport dest. is same as next's
                } else {
                    teleportGrid[pointer.pos] = next.pos // if next is out of the grid, just go there
                }
            }

            pointers[i] = pointer.prev() // pointer wall moves backwards
        }
    }

    dir to teleportGrid
}


private fun Dir.turnedRight(): Dir = when(this) {
    Dir.UP -> Dir.RIGHT
    Dir.RIGHT -> Dir.DOWN
    Dir.DOWN -> Dir.LEFT
    Dir.LEFT -> Dir.UP
    else -> throw IllegalArgumentException("don't need non-orthogonal dirs")
}

private fun Pointer.isFacing(target: Pos): Boolean = when(this.dir) {
    Dir.UP -> pos.x == target.x && pos.y > target.y
    Dir.RIGHT -> pos.x < target.x && pos.y == target.y
    Dir.DOWN -> pos.x == target.x && pos.y < target.y
    Dir.LEFT -> pos.x > target.x && pos.y == target.y
    else -> throw IllegalArgumentException("don't need non-orthogonal dirs")
}

