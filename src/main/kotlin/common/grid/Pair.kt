package common.grid

data class Pair(
    val x: Int,
    val y: Int,
) {
    operator fun plus(other: Pair): Pair = Pair(x + other.x, y + other.y)
    operator fun times(scalar: Int): Pair = Pair(x * scalar, y * scalar)
}

typealias Pos = Pair
typealias Delta = Pair