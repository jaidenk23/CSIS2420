/**
 * @author Jaiden Kazemini
 * CSIS2420
 */
package books;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

//the class Book implements the interface Comparable
public class Book implements Comparable<Book> {

	static String csvFile = "C:\\Users\\Jaiden\\Desktop\\Semester 3\\CSIS2420\\books.csv";
	public static final String delimiter = ",";
	private String title;
	private String author;
	private int year;

//Constructor method with parameters String title
//String author, and int year
	public Book(String title, String author, int year) {
//"this" keyword assigns the value of each parameter
//to the field of the same name
		this.title = title;
		this.author = author;
		this.year = year;

	}

//return private String title
	public String getTitle() {
		return title;

	}

//return private String author
	public String getAuthor() {
		return author;

	}

//return private int year
	public int getYear() {
		return year;

	}

//returns a String in the form of
//"name by author (year)"
	@Override
	public String toString() {
		return title + " " + "by" + " " + author + " " + "(" + year + ")";

	}

//the following method reads in the file
//and checks each line using regex's, then prints 
//an error for any lines that don't contain what 
//the regex's check for.
	public static List<Book> getList(String file) {
		File f1 = new File(csvFile);
		FileReader fr = null;

		try {
			fr = new FileReader(f1);
		} catch (FileNotFoundException e) {
		}

		BufferedReader br = new BufferedReader(fr);

		int lines = 0;

		List<Book> bookList = new ArrayList<Book>();

		String line;
		String[] arr;

		try {
			while ((line = br.readLine()) != null) {
				line = line.trim();
				lines++;
				arr = line.split(delimiter);

				if (arr.length > 2 && arr[0].toString().matches("\\b((?!=|\\,|\\.).)+(.)\\b")
						&& arr[1].toString().matches("^[a-zA-Z .]*$") && arr[2].toString().matches("[0-9]{4}")) {
					bookList.add(new Book(arr[0], arr[1], Integer.parseInt(arr[2])));
				}

				else {
					System.err.println("Error reading in: " + line);
				}
			}
		}

		catch (Exception e1) {
		}

		try {
			br.close();
		} catch (IOException e) {
		}
//prints out the number of books read in from the csv file
		System.out.println("Number of books read in: " + lines);
		return bookList;

	}
//I wasn't able to complete this method which would've used a Comparator
//to sort the book list in natural order in order to then sort it in
//reverse natural order in the BookApp class.
	@Override
	public int compareTo(Book arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
