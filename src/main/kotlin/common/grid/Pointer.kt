package common.grid

data class Pointer(
    val pos: Pos,
    val dir: Dir,
) {
    fun next(): Pointer = Pointer(pos + dir.delta, dir)
}