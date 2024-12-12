package common.grid

data class Pointer(
    val pos: Pos,
    val dir: Dir,
) {
    fun next(): Pointer = Pointer(pos + dir.delta, dir)
    fun prev(): Pointer = Pointer(pos - dir.delta, dir)
    fun turn(newDir: Dir): Pointer = Pointer(pos, newDir)

    override fun toString(): String = "$dir$pos"
}