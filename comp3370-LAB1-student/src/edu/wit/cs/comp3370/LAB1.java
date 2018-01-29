package edu.wit.cs.comp3370;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

/* Sorts integers from command line using various algorithms 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 1
 * 
 * Eli Kapetanopoulos
 * 5/17/2017
 */

public class LAB1 {

	/*
	 * TODO: document this method Counting sort takes an array of integers from
	 * 0 to K and determines for each element the number of elements less than
	 * X. Then places element X into the output array.
	 */
	public static int[] countingSort(int[] a) {
		// TODO: implement this method

		// Find max value to build count array
		if (a.length == 0) {
			return a;
		}
		int countMax = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] > countMax)
				countMax = a[i];
		}

		// Set all values in count equal to 0
		int[] count = new int[countMax + 1];
		for (int i = 0; i < count.length; i++) {
			count[i] = 0;
		}

		// Count contains number of elements equal to i
		for (int i = 0; i < a.length; i++) {
			count[a[i]]++;
		}

		// count contains number of elements less than or equal to i
		for (int i = 1; i < count.length; i++) {
			count[i] = count[i] + count[i - 1];
		}

		// Final sorted array is filled
		int[] sorted = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			sorted[count[a[i]] - 1] = a[i];
			count[a[i]]--;

		}

		return sorted;
	}

	/*
	 * TODO: document this method Radix sort takes an array of integers from 0
	 * to K and sorts based on digit starting from the 1's place until largest
	 * place value is reached. Each digit is kept track of in a count and
	 * position array. Then placed into the output array based on place digit
	 * sorted.
	 */
	public static int[] radixSort(int[] a) {
		// TODO: implement this method

		// Find max value and number of digits
		if (a.length == 0) {
			return a;
		}
		int Max = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] > Max)
				Max = a[i];
		}

		int count[] = new int[10];
		int pos[] = new int[10];
		int sorted[] = new int[a.length];

		// Loop through counting sort for number of digits in largest
		// value passed in a[].
		for (int i = 1; Max / i > 0; i *= 10) {
			// Set all values in count equal to 0
			for (int j = 0; j < count.length; j++) {
				count[j] = 0;
				pos[j] = 0;
			}

			// Count contains number of digits equal to j.
			for (int j = 0; j < a.length; j++) {
				count[((a[j] / i) % 10)]++;
			}

			// Position array contains number of digits less than j.
			for (int j = 1; j < count.length; j++) {
				pos[j] = pos[j - 1] + count[j - 1];
			}

			// fill sorted array.
			for (int j = 0; j < a.length; j++) {
				sorted[pos[((a[j] / i) % 10)]] = a[j];
				pos[((a[j] / i) % 10)]++;
			}

			// fill original array.
			for (int j = 0; j < a.length; j++) {
				a[j] = sorted[j];
			}
		}

		return a;
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	public final static int MAX_INPUT = 524287;
	public final static int MIN_INPUT = 0;

	// example sorting algorithm
	public static int[] insertionSort(int[] a) {

		for (int i = 1; i < a.length; i++) {
			int tmp = a[i];
			int j;
			for (j = i - 1; j >= 0 && tmp < a[j]; j--)
				a[j + 1] = a[j];
			a[j + 1] = tmp;
		}

		return a;
	}

	/*
	 * Implementation note: The sorting algorithm is a Dual-Pivot Quicksort by
	 * Vladimir Yaroslavskiy, Jon Bentley, and Joshua Bloch. This algorithm
	 * offers O(n log(n)) performance on many data sets that cause other
	 * quicksorts to degrade to quadratic performance, and is typically faster
	 * than traditional (one-pivot) Quicksort implementations.
	 */
	public static int[] systemSort(int[] a) {
		Arrays.sort(a);
		return a;
	}

	// read ints from a Scanner
	// returns an array of the ints read
	private static int[] getInts(Scanner s) {
		ArrayList<Integer> a = new ArrayList<Integer>();

		while (s.hasNextInt()) {
			int i = s.nextInt();
			if ((i <= MAX_INPUT) && (i >= MIN_INPUT))
				a.add(i);
		}

		return toIntArray(a);
	}

	// copies an ArrayList of Integer to an array of int
	private static int[] toIntArray(ArrayList<Integer> a) {
		int[] ret = new int[a.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = a.get(i);
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the sorting algorithm to use ([c]ounting, [r]adix, [i]nsertion, or [s]ystem): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter the integers that you would like sorted, followed by a non-integer character: ");
		int[] unsorted_values = getInts(s);
		int[] sorted_values = {};

		s.close();

		switch (algo) {
		case 'c':
			sorted_values = countingSort(unsorted_values);
			break;
		case 'r':
			sorted_values = radixSort(unsorted_values);
			break;
		case 'i':
			sorted_values = insertionSort(unsorted_values);
			break;
		case 's':
			sorted_values = systemSort(unsorted_values);
			break;
		default:
			System.out.println("Invalid sorting algorithm");
			System.exit(0);
			break;
		}

		System.out.println(Arrays.toString(sorted_values));
	}

}
