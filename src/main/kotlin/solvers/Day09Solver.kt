package solvers

import common.linkedList.LinkedList
import common.linkedList.LinkedList.Companion.toLinkedList

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
        val list = LinkedList(segments)

        var fileNode = list.tail

        while (fileNode != null) {
            if (fileNode.value !is Segment.File) {
                fileNode = fileNode.prev
                continue
            }

            // find first empty of right size, searching up to file itself
            var emptyNode = list.head!!.nextUntil {
                it == fileNode || (it.value is Segment.Empty && it.value.size >= fileNode!!.value.size)
            }
            // none found -> go to next file
            if (emptyNode == fileNode) {
                fileNode = fileNode.prev
                continue
            }

            val splicedList = mutableListOf<Segment>((fileNode.value as Segment.File).copy())
            if (emptyNode!!.value.size > fileNode.value.size) {
                splicedList.add(Segment.Empty(emptyNode.value.size - fileNode.value.size))
            }
            list.splice(emptyNode, splicedList.toLinkedList())

            fileNode.value = Segment.Empty(fileNode.value.size)
        }

        // finally, calculate and return checksum
        val blocks = list.toBlocks()
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

private fun List<Int>.checksum(): Long = mapIndexed { index, id -> if (id == -1) 0 else index.toLong() * id }.sum()

private fun List<Segment>.toBlocks(): List<Int> = this.map { it.blocks() }.flatten()
