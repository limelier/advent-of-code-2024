package common.grid

data class Grid<T>(
    private val data: Array<Array<T>>
) {
    init {
        if (data.any { row -> row.size != data.first().size })
            throw IllegalArgumentException("data needs to be rectangular!")
    }

    val size = Pair(data.first().size, data.size)
    val ys = data.indices
    val xs = data.first().indices

    fun positions(): Sequence<Pos> = sequence { xs.forEach { x -> ys.forEach { y -> yield(Pos(x, y)) }}}
    fun contains(pos: Pos): Boolean = pos.x in xs && pos.y in ys

    operator fun get(pos: Pos): T? {
        if (!contains(pos)) return null
        return data[pos.y][pos.x]
    }

    operator fun set(pos: Pos, value: T) {
        if (!contains(pos)) throw IllegalArgumentException("pos is out of bounds")
        data[pos.y][pos.x] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grid<*>

        if (!data.contentDeepEquals(other.data)) return false
        if (size != other.size) return false
        if (ys != other.ys) return false
        if (xs != other.xs) return false

        return true
    }
    override fun hashCode(): Int {
        var result = data.contentDeepHashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + ys.hashCode()
        result = 31 * result + xs.hashCode()
        return result
    }

    fun printableString(rowSeparator: CharSequence = "\n", colSeparator: CharSequence = ""): String {
        return data.joinToString(rowSeparator) { row ->
            row.joinToString(colSeparator)
        }
    }
}
