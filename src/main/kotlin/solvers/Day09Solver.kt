package solvers

class Day09Solver(input: List<String>) : DaySolver {
    private val segments: List<Segment>
    private val maxId: Int

    init {
        segments = mutableListOf()

        var isFile = true
        for ((i, c) in input[0].withIndex()) {
            val size = c.digitToInt()
            segments += if (isFile) Segment.File(size, i / 2) else Segment.Empty(size)
            isFile = !isFile
        }

        maxId = (segments.last { it is Segment.File } as Segment.File).id
    }

    override fun part1(): Any {
        val blocks = segments.toBlocks().toMutableList()

        var freeSpacePointer = blocks.indexOfFirst { it == -1 }
        var fileBlockPointer = blocks.size - 1

        while (freeSpacePointer < fileBlockPointer) {
            if (blocks[freeSpacePointer] != -1) {
                freeSpacePointer++
            } else if (blocks[fileBlockPointer] == -1) {
                fileBlockPointer--
            } else {
                blocks[freeSpacePointer] = blocks[fileBlockPointer].also {
                    blocks[fileBlockPointer] = blocks[freeSpacePointer]
                }
                freeSpacePointer++
                fileBlockPointer--
            }
        }

        return blocks.checksum()
    }

    override fun part2(): Any {
        // head.data will NEVER be Segment.Empty
        val head = segments.toDoublyLinkedList()

        var file: Node? = let {
            var node = head
            while (node.next != null) node = node.next!!
            while (node.data !is Segment.File) node = node.prev!!
            node
        }

        outer@ while (file != null) {
            if (file.data !is Segment.File) {
                file = file.prev
                continue
            }

            // find first empty of right size, searching up to file itself
            var empty: Node = head
            while (empty != file) {
                if (empty.data is Segment.Empty && empty.data.size >= file.data.size)
                    break
                empty = empty.next!!
            }
            // none found -> go to next file
            if (empty == file) {
                file = file.prev
                continue
            }

            // insert new file into empty
            val newFile = Node(file.data, prev = empty.prev, next = empty.next)
            empty.prev?.next = newFile

            // create a new empty with the remainder of the empty space, if any.
            if (file.data.size < empty.data.size) {
                val newEmpty = Node(Segment.Empty(empty.data.size - file.data.size), newFile, empty.next)
                newFile.next = newEmpty
                empty.next?.prev = newEmpty
            } else {
                empty.next?.prev = newFile
            }

            // empty has been orphaned, will be cleaned up by GC

            // replace file with empty
            val fileEmpty = Node(Segment.Empty(file.data.size), file.prev, file.next)
            file.prev?.next = fileEmpty
            file.next?.prev = fileEmpty

            // file has been orphaned, will be cleaned up by GC

            file = file.prev
            while (file != null && file.data !is Segment.File) file = file.prev
        }

        // finally, calculate and return checksum
        val blocks = mutableListOf<Int>()
        var node: Node? = head
        while (node != null) {
            blocks += node!!.data.blocks()
            node = node!!.next
        }
        return blocks.checksum()
    }
}

private abstract class Segment(
    open val size: Int
) {
    abstract fun blocks(): List<Int>

    data class File(override val size: Int, val id: Int) : Segment(size) {
        override fun blocks(): List<Int> = List(size) { id }
        override fun toString(): String = "[#$id:$size]"
    }
    data class Empty(override val size: Int) : Segment(size) {
        override fun blocks(): List<Int> = List(size) { -1 }
        override fun toString(): String = "<$size>"
    }
}

private class Node(
    val data: Segment,
    var prev: Node? = null,
    var next: Node? = null,
) {
    override fun toString(): String = "$data-" + (next?.toString() ?: "")
}

private fun List<Segment>.toDoublyLinkedList(): Node {
    val head = Node(first())
    var tail = head

    subList(1, size).forEach {
        val node = Node(it, prev = tail)
        tail.next = node
        tail = node
    }

    return head
}

private fun List<Int>.checksum(): Long = mapIndexed { index, id -> if (id == -1) 0 else index.toLong() * id }.sum()

private fun List<Segment>.toBlocks(): List<Int> = this.map { it.blocks() }.flatten()
