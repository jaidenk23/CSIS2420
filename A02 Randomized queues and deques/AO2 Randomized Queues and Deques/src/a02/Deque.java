/**
 * @author Jaiden Kazemini
 * @author Christopher Keating
 * 
 * A02 Part 2 Randomized Queues and Deques
 * CSIS2420
 * 
 * 
 */



package a02;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node first; // 8 bytes
	private Node last; // 8 bytes
	private int size; // 4 bytes

	private class Node { // 16 Byte object overhead+8 Extra overhead of byte internal classes+8+8+8=48
							// bytes, n The number of nodes is 48. n bytes
		private Node preNode; // Reference to the previous node
		private Item item;
		private Node nextNode; // Reference to the latter node
	}

	private class ListIterator implements Iterator<Item> {
		// 16 Byte object overhead+8 Extra overhead of byte internal classes+8=32 bytes
		private Node curr = first;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return curr != null;
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if (curr == null)
				throw new NoSuchElementException("there are no more items!");
			Item item = curr.item;
			curr = curr.nextNode;
			return item;
		}
		// remove No design, parent class Iterator Of remove The method is thrown
		// UnsupportedOperationException("remove");
	}

	public Deque() {
		// construct an empty deque
		size = 0;
		first = null;
		last = null;
	}

	public boolean isEmpty() {
// is the deque empty?
		return (size == 0);
	}

	public int size() {
// return the number of items on the deque
		return size;
	}

	public void addFirst(Item item) {
// add the item to the front
		valivate(item);
		Node newNode = new Node();
		newNode.item = item;
		if (size == 0) { // The situation of empty queues
			newNode.preNode = null;
			newNode.nextNode = null;
			first = newNode;
			last = newNode;
		} else {
			newNode.preNode = null;
			newNode.nextNode = first;
			first.preNode = newNode;
			first = newNode;
		}
		size++;
	}

	public void addLast(Item item) {
// add the item to the end
		valivate(item);
		Node newNode = new Node();
		newNode.item = item;
		if (size == 0) { // The situation of empty queues
			newNode.preNode = null;
			newNode.nextNode = null;
			first = newNode;
			last = newNode;
		} else {
			last.nextNode = newNode;
			newNode.preNode = last;
			newNode.nextNode = null;
			last = newNode;
		}
		size++;
	}

	public Item removeFirst() {
// remove and return the item from the front
		if (size == 0)
			throw new NoSuchElementException("the deque is empty!");
		Item returnItem = null;
		if (size == 1) {
			returnItem = first.item;
			first = null;
			last = null;
		} else {
			Node oldfirst = first;
			returnItem = oldfirst.item;
			first = oldfirst.nextNode;
			first.preNode = null;
			oldfirst.nextNode = null;
			oldfirst.item = null;
		}
		size--;
		return returnItem;
	}

	public Item removeLast() {
// remove and return the item from the end
		if (size == 0)
			throw new NoSuchElementException("the deque is empty!");
		Item returnItem = null;
		if (size == 1) {
			returnItem = first.item;
			first = null;
			last = null;
		} else {
			Node oldlast = last;
			returnItem = oldlast.item;
			last = oldlast.preNode;
			last.nextNode = null;
			oldlast.preNode = null;
			oldlast.item = null;
		}
		size--;
		return returnItem;
	}

	public Iterator<Item> iterator() {
// return an iterator over items in order from front to end
		return new ListIterator();
	}

	private void valivate(Item item) {
		if (item == null)
			throw new IllegalArgumentException("the item is null!");
	}

	public static void main(String[] args) {
// unit testing (optional)
		Deque<String> queue = new Deque<String>();
		//System.out.println(queue.size);
		queue.addLast("a");
		queue.addFirst("b");
		queue.addLast("c");
		queue.addFirst("d");
		queue.addFirst("e");
		queue.addFirst("f");
		queue.addLast("g");
		queue.addFirst("h");
		queue.addFirst("i");
		//System.out.println(queue.size);
		Iterator<String> iter = queue.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
}