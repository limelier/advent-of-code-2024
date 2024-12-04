package common.grid

enum class Dir(
    val delta: Delta,
) {
    UP(Delta(0, 1)),
    DOWN(Delta(0, -1)),
    LEFT(Delta(-1, 0)),
    RIGHT(Delta(1, 0)),
    UP_LEFT(Delta(-1, 1)),
    UP_RIGHT(Delta(1, 1)),
    DOWN_LEFT(Delta(-1, -1)),
    DOWN_RIGHT(Delta(1, -1)),
    ;

    val orthogonal get() = listOf(UP, DOWN, LEFT, RIGHT)
    val diagonal get() = listOf(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT)
}