package books;

import java.util.List;
import java.util.Collections;

/**
 * 
 * @author Jaiden Kazemini CSIS2420
 *
 */
public class BookApp {

	static String csvFile = "C:\\Users\\Jaiden\\Desktop\\Semester 3\\CSIS2420\\books.csv";
//the below method sorts the book list, then prints it. It then 
//Sorts it in reverse order and prints it out.  
	public static void main(String[] args) {
		List<Book> books = Book.getList(csvFile);
		Collections.sort(books);
		System.out.println("\n\nSorted book list: ");

		for (Book book : books) {
			System.out.println(book);
		}

		System.out.println("\n\nReverse order book list: ");

		Collections.reverse(books);

		for (Book book : books) {
			System.out.println(book);
		}
	}
}
