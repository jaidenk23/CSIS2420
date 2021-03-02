package m01;

import edu.princeton.cs.algs4.StdOut;

/**
 * CSIS-2420 Midterm1
 * 
 * @author StarterCode + ...............
 * @author Jaiden Kazemini
 * 
 * Maybe I made this assignment too simple, but I thought since
 * the instructions weren't too complex that I'd go a more simpler
 * route.
 * 
 */

public class Midterm1 {

	public static void main(String[] args) {
		// The below code is an array containing each container's ID number,
		// length, weight, and country of origin.
		Container[] containers = { new Container(1234, 20, 1.9, "China"), new Container(1235, 40, 3.97, "USA"),
				new Container(1236, 40, 4.22, "China"), new Container(1237, 20, 2.16, "Ghana"),
				new Container(1238, 20, 2.1, "USA"), new Container(1239, 40, 4.08, "Italy"),
				new Container(1240, 40, 3.81, "China"), new Container(1241, 40, 4.2, "USA"),
				new Container(1242, 20, 1.82, "Italy") };
		// The below code prints out the array
		// (containers).
		StdOut.println("Containers: ");
		StdOut.println("=========== ");
		for (Container c : containers) {
			StdOut.println(c);
		}
		System.out.println();

		StdOut.println("= = = =    Part 1   = = = =\n");
		// The below code prints out the default/natural order
		// of the containers(same as above).
		StdOut.println("Containers by natural order:");
		for (Container c : containers) {
			StdOut.println(c);

		}
		StdOut.println("============================");

		// The below code prints out the reverse order
		// of the containers using a for loop.
		StdOut.println("Containers in reverse order:");
		for (int i = containers.length - 1; i >= 0; i--) {
			StdOut.println(containers[i].getId() + " " + containers[i].getLength() + " " + containers[i].getWeight()
					+ " " + containers[i].getCountry());
		}
		StdOut.println("============================");

		StdOut.println("= = = =    Part 2    = = = =\n");

		StdOut.println("Foreign Containers:");
		// The below loop/conditional statement increments each line
		// if the array is not equal to "USA", therefore
		// prints out every container other than USA(foreign containers).
		for (int i = 0; i < containers.length; i++) {

			if (containers[i].getCountry() != "USA")
				StdOut.println(containers[i].getId() + " " + containers[i].getLength() + " " + containers[i].getWeight()
						+ " " + containers[i].getCountry());
		}

		StdOut.println("===================");
	}
}