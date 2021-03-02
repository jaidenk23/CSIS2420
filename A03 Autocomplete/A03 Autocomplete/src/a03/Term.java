package a03;

import java.util.Comparator;

/**
 * @author Mathew Merkley, Jaiden Kazemini 
 * Generic Term class implements Comparable term. 
 * class used for sorting and storing Term
 */
public class Term implements Comparable<Term> {
	private String query = null;
	private double weight = 0;

	/**
	 * @param query  String
	 * @param weight Double
	 */
	public Term(String query, double weight) {
		if (query == null) {
			throw new NullPointerException();
		}
		if (weight < 0) {
			throw new IllegalArgumentException();
		}
		this.query = query;
		this.weight = weight;
	}

	/**
	 * 
	 * @return Comparator<Term>
	 */
	public static Comparator<Term> byReverseWeightOrder() {
		return new ReverseCompare();
	}

	/**
	 * 
	 * @author Mathew Merkley, Jaiden Kazemini private class used to reverse a
	 *         compare Needs two Term objects
	 */
	private static class ReverseCompare implements Comparator<Term> {

		@Override
		public int compare(Term o1, Term o2) {
			if (o1.weight < o2.weight) {
				return 1;
			} else if (o1.weight > o2.weight) {
				return -1;
			} else {
				return 0;
			}
		}

	}

	/**
	 * 
	 * @param r int length you want to search in a word
	 * @return
	 */
	public static Comparator<Term> byPrefixOrder(int r) {
		return new PrefixOrder(r);
	}

	/**
	 * 
	 * @author Mathew Merkley, Jaiden Kazemini Used to sort String words
	 */
	private static class PrefixOrder implements Comparator<Term> {
		int r = 0;

		/**
		 * 
		 * @param r length wanting to search
		 */
		public PrefixOrder(int r) {
			if (r < 0) {
				throw new IllegalArgumentException();
			}

			this.r = r;
		}

		@Override
		public int compare(Term o1, Term o2) {
			String one = o1.query;
			String two = o2.query;
			int smallest;

			if (one.length() < two.length()) {
				smallest = one.length();
			} else {
				smallest = two.length();
			}

			if (smallest >= r) {
				return one.substring(0, r).compareTo(two.substring(0, r));
			} else if (one.substring(0, smallest).compareTo(two.substring(0, smallest)) == 0) {
				if (one.length() == smallest) {
					return -1;
				} else {
					return 1;
				}
			} else {
				return one.substring(0, smallest).compareTo(two.substring(0, smallest));
			}
		}

	}

	@Override
	public String toString() {
		return weight + "\t" + query;
	}

	@Override
	public int compareTo(Term o) {
		return this.query.compareTo(o.query);
	}

}
