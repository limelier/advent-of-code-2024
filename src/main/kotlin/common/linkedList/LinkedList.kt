package common.linkedList

class LinkedList<T>() : List<T> {
    override var size = 0
    var head: Node<T>? = null
    var tail: Node<T>? = head

    constructor(collection: Collection<T>) : this() { collection.forEach { add(it) } }

    private fun add(value: T) {
        if (head == null) {
            head = Node(value)
            tail = head
        } else {
            val newNode = Node(value)
            newNode.prev = tail
            tail!!.next = newNode
            tail = newNode
        }

        size++
    }

    fun nodeWith(value: T): Node<T>? {
        val node = head
        while (node != null) {
            if (node.value == value) return node.copy()
        }
        return null
    }

    fun nodeAt(index: Int): Node<T> {
        if (index < 0 || index >= size) throw IllegalArgumentException("index out of bounds")

        return if (index <= size / 2) {
            var node = head!!
            val i = 0
            while (i != index) {
                node = node.next!!
            }
            node.copy()
        } else {
            var node = tail!!
            val i = size - 1
            while (i != index) {
                node = node.prev!!
            }
            node.copy()
        }
    }

    /**
     * Replace a [node] in this list with new nodes for elements in [collection].
     *
     * Assumes that [node] is part of this list.
     */
    fun splice(node: Node<T>, collection: Collection<T>) {
        val list = collection.toLinkedList()

        if (node == head) head = list.head
        if (node == tail) tail = list.tail
        node.prev?.next = list.head
        node.next?.prev = list.tail
        list.head?.prev = node.prev
        list.tail?.next = node.next

        size = size - 1 + collection.size
    }

    override fun contains(element: T): Boolean = nodeWith(element) != null

    override fun containsAll(elements: Collection<T>): Boolean = elements.all { this.contains(it) }

    override fun get(index: Int): T = nodeAt(index).value

    override fun indexOf(element: T): Int {
        val node = head
        var index = 0
        while (node != null) {
            if (node.value == element) return index
            index++
        }
        return -1
    }

    override fun isEmpty(): Boolean = size == 0

    override fun iterator(): Iterator<T> = LinkedListIterator<T>(head, null, 0)

    override fun lastIndexOf(element: T): Int {
        val node = tail
        var index = size - 1
        while (node != null) {
            if (node.value == element) return index
            index--
        }
        return -1
    }

    override fun listIterator(): ListIterator<T> = LinkedListIterator<T>(head, null, 0)

    override fun listIterator(index: Int): ListIterator<T> {
        val node = nodeAt(index)
        return LinkedListIterator(node, node.prev?.prev, index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        val list = LinkedList<T>()
        list.head = nodeAt(fromIndex)
        list.tail = nodeAt(toIndex - 1)
        list.size = toIndex - fromIndex
        return list
    }

    companion object {
        private class LinkedListIterator<T>(
            private var nextNode: Node<T>?,
            private var prevNode: Node<T>?,
            private var index: Int,
        ) : ListIterator<T> {
            override fun hasNext(): Boolean {
                return nextNode != null
            }

            override fun hasPrevious(): Boolean {
                return prevNode != null
            }

            override fun next(): T {
                if (!hasNext()) throw NoSuchElementException("iterator has no next element")
                return nextNode!!.value.also {
                    nextNode = nextNode!!.next
                    prevNode = prevNode?.prev
                    index++
                }
            }

            override fun nextIndex(): Int {
                return index + 1
            }

            override fun previous(): T {
                if (!hasPrevious()) throw NoSuchElementException("iterator has no previous element")
                return prevNode!!.value.also {
                    prevNode = prevNode!!.prev
                    nextNode = prevNode!!.next
                    index--
                }
            }

            override fun previousIndex(): Int {
                return index - 1
            }
        }

        fun <T> Collection<T>.toLinkedList(): LinkedList<T> = LinkedList(this)
    }
}

