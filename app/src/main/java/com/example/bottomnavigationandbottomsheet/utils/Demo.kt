package com.example.bottomnavigationandbottomsheet.utils

class Demo {
    fun binarySearch(arr: IntArray, target: Int): Int {
        var low: Int = 0
        var high: Int = arr.size - 1
        while (low <= high) {
            val mid = low + (high - low) / 2
            when {
                arr[mid] == target -> return mid
                arr[mid] < target -> low = mid + 1
                else -> high = mid - 1
            }
        }
        return -1
    }
    fun main() {
        val array = intArrayOf(3, 5, 4, 2, 8, 7, 1)
        val array1 = array.sortedArray()
        val target = 7
        val result = binarySearch(array1, target)
        if (result == -1) {
            println("Not found !!!!")
        } else {
            println("$target found at index $result in the sorted array.")
        }
    }
}

class Node<T>(val value: T) {
    var next: Node<T>? = null
}

fun <T> reverseLinkedList(head: Node<T>?): Node<T>? {
    var current = head
    var previous: Node<T>? = null
    var next: Node<T>?

    while (current != null) {
        next = current.next
        current.next = previous
        previous = current
        current = next
    }
    return previous
}

fun <T> printLinkedList(head: Node<T>?) {
    var current = head
    while (current != null) {
        print("${current.value} ->")
        current = current.next
    }
    println("null")
}

fun main() {
    val node1 = Node(1)
    val node2 = Node(2)
    val node3 = Node(3)
    val node4 = Node(4)
    val node5 = Node(5)

    node1.next = node2
    node2.next = node3
    node3.next = node4
    node4.next = node5

    println("Original List:::")
    printLinkedListNew(node1)

    val reverselist = reverseLinkedList(node1)

    println("Reverse Linked List:::")
    printLinkedListNew(reverselist)

}

fun <T> reverseLinkedListNew( head:Node<T>?):Node<T>?{
    var current=head
    var previous:Node<T>?=null
    var next:Node<T>?

    while (current!=null)
    {
        next=current.next
        current.next=previous
        previous=current
        current=next

    }
    return previous
}

fun <T> printLinkedListNew(head: Node<T>?)
{
    var current=head
    while (current!=null)
    {
     print("${current.value}->")
     current=current.next
    }
    print("null")
}
