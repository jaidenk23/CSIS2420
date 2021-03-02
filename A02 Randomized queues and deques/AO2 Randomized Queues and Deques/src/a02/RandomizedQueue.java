/**
 * @author Jaiden Kazemini
 * @author Christopher Keating
 * 
 * A02 Part 1 Randomized Queues and Deques
 * CSIS2420
 * 
 * 
 */

package a02;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] rqArrays;
	private int size;

	private class RandomIterator implements Iterator<Item> {
		private int rank; // rank Record the number of conveniences
		private Item[] iterArrays; // The two iterators must be independent of each other and have their own random
									// order

		public RandomIterator() {
			rank = size;
			iterArrays = (Item[]) new Object[rank];
			for (int i = 0; i < size; i++) {
				iterArrays[i] = rqArrays[i];
			}
		}

		@Override
		public boolean hasNext() {
// TODO Auto-generated method stub
			return (rank > 0);
		}

		@Override
		public Item next() {
// TODO Auto-generated method stub
			if (rank == 0)
				throw new NoSuchElementException("there are no more items!");
			int r = StdRandom.uniform(0, rank); // Random selection of elements in a location to return
			rank--;
			Item item = iterArrays[r];
			iterArrays[r] = iterArrays[rank];
			iterArrays[rank] = item; // Place the traversed elements at the end of the queue so that the next
										// iteration will not be selected
			return item;
		}
	}

	public RandomizedQueue() {
		// construct an empty randomized queue
		rqArrays = (Item[]) new Object[1];
		size = 0;
	}

	private void valivate(Item item) {
		if (item == null)
			throw new IllegalArgumentException("the item is null!");
	}

	public boolean isEmpty() {
// is the queue empty?
		return (size == 0);
	}

	public int size() {
// return the number of items on the queue
		return size;
	}

	private void resize(int cap) {
		Item[] temp = (Item[]) new Object[cap];
		for (int i = 0; i < size; i++)
			temp[i] = rqArrays[i];
		rqArrays = temp;
	}

	public void enqueue(Item item) {
// add the item
		valivate(item);
		rqArrays[size++] = item;
		if (size == rqArrays.length)
			resize(2 * rqArrays.length);
	}

	public Item dequeue() {
// remove and return a random item
// Randomly select a location to swap elements of that location with elements at the end of the queue
// dequeue Random at the end of the element remove Purpose of Elements
		if (size == 0)
			throw new NoSuchElementException("the RandomizeQueue is empty!");
		int r = StdRandom.uniform(0, size);
		size--;
		Item delItem = rqArrays[r];
		rqArrays[r] = rqArrays[size];
		rqArrays[size] = null;
		if (size > 0 && size == rqArrays.length / 4)
			resize(rqArrays.length / 2);
		return delItem;
	}

	public Item sample() {
// return (but do not remove) a random item
		if (size == 0)
			throw new NoSuchElementException("the RandomizeQueue is empty!");
		return rqArrays[StdRandom.uniform(0, size)];
	}

	public Iterator<Item> iterator() {
// return an independent iterator over items in random order
		return new RandomIterator();
	}

	public static void main(String[] args) {
// unit testing (optional)
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.enqueue("a");
		rq.enqueue("b");
		rq.enqueue("c");
		rq.enqueue("d");
		rq.enqueue("e");
		rq.enqueue("f");
		rq.enqueue("g");
		rq.dequeue();
		Iterator<String> iter1 = rq.iterator();
		Iterator<String> iter2 = rq.iterator();
		while (iter1.hasNext()) {
			System.out.print(iter1.next() + ",");
		}
		System.out.println();
		while (iter2.hasNext()) {
			System.out.print(iter2.next() + ",");
		}
		System.out.println();

	}
}