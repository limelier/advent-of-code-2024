package common.grid

import kotlin.math.abs

data class Pair(
    val x: Int,
    val y: Int,
) {
    operator fun plus(other: Pair): Pair = Pair(x + other.x, y + other.y)
    operator fun minus(other: Pair): Pair = Pair(x - other.x, y - other.y)
    operator fun times(scalar: Int): Pair = Pair(x * scalar, y * scalar)

    override fun toString(): String = "($x,$y)"
}

fun taxicabDistance(first: Pair, second: Pair): Int = abs(first.x - second.x) + abs(first.y - second.y)

typealias Pos = Pair
typealias Delta = Pair