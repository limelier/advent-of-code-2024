package common.linkedList

data class Node<T>(
    var value: T,
    var prev: Node<T>? = null,
    var next: Node<T>? = null,
) {
    override fun toString(): String = "[$value]"

    fun nextUntil(predicate: (Node<T>) -> Boolean): Node<T>? {
        var node: Node<T>? = this.copy()
        while (node != null) {
            if (predicate(node)) break
            node = node.next
        }
        return node
    }

    fun nextUntilValue(predicate: (T) -> Boolean): Node<T>? = nextUntil { predicate(it.value) }

    fun prevUntil(predicate: (Node<T>) -> Boolean): Node<T>? {
        var node: Node<T>? = this.copy()
        while (node != null) {
            if (predicate(node)) break
            node = node.prev
        }
        return node
    }

    fun prevUntilValue(predicate: (T) -> Boolean): Node<T>? = prevUntil { predicate(it.value) }
}