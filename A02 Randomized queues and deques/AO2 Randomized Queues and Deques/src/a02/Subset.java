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

import edu.princeton.cs.algs4.StdIn;

public class Subset {
	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);
		while (!StdIn.isEmpty()) {
			rq.enqueue(StdIn.readString());
			// System.out.println(StdIn.readString());
		}
		while (k > 0) {
			System.out.println(rq.dequeue());
			k--;
		}
	}
}