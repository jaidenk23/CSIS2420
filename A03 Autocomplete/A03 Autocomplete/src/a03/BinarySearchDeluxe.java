package a03;

import java.util.Comparator;

/**
 * @author Mathew Merkley, Jaiden Kazemini BinarySearchDeluxe is used to find
 *         the first index in an array from a key, also the last index in an
 *         array from a key
 */

public class BinarySearchDeluxe {

	/**
	 * 
	 * @param a          Array of Key
	 * @param key        Used to search the array
	 * @param comparator Comparator object for searching
	 * @return int index of first index matching key, or -1 for none matching
	 */
	public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
		if (a == null || key == null || comparator == null) {
			throw new java.lang.NullPointerException();
		}
		int min = 0;
		int max = a.length - 1;

		if (comparator.compare(a[0], key) == 0) {
			return 0;
		}
		while (min <= max) {
			int mid = min + (max - min) / 2;
			if (comparator.compare(key, a[mid]) < 0) {
				max = mid - 1;
			} else if (comparator.compare(key, a[mid]) > 0) {
				min = mid + 1;
			} else if (comparator.compare(a[mid - 1], a[mid]) == 0) {
				max = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;

	}

	/**
	 * 
	 * @param a          Array to search
	 * @param key        Key used for searching and matching
	 * @param comparator Comparator object for searching
	 * @return int index of last match, -1 for none matching
	 */
	public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
		if (a == null || key == null || comparator == null) {
			throw new java.lang.NullPointerException();
		}
		int min = 0;
		int max = a.length - 1;

		if (comparator.compare(a[max], key) == 0) {
			return max;
		}
		while (min <= max) {
			int mid = min + (max - min) / 2;
			if (comparator.compare(key, a[mid]) < 0) {
				max = mid - 1;
			} else if (comparator.compare(key, a[mid]) > 0) {
				min = mid + 1;
			} else if (comparator.compare(a[mid + 1], a[mid]) == 0) {
				min = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;

	}

}
