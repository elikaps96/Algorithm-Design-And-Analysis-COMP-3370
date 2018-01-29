package edu.wit.cs.comp3370;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/* Calculates the reconstruction matrix of the Floyd-Warshall algorithm for
 * all-pairs shortest paths 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 10
 * 
 * Eli Kapetanopoulos
 */

public class LAB10 {

	// TODO document this method
	public static Vertex[][] FindAllPaths(Graph g) {
		// TODO implement this method

		int v = g.size();
		Vertex[][] pi = new Vertex[v][v];
		double d[][][] = new double[v + 1][v][v];

		// fill with infinity
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				d[0][i][j] = Integer.MAX_VALUE;
			}

		}

		// For each edge in the graph fill pi and
		// first distance matrix with dst and cost
		for (Edge e : g.getEdges()) {
			pi[e.src.ID][e.dst.ID] = e.dst;
			d[0][e.src.ID][e.dst.ID] = e.cost;
		}

		// Floyd-Warshall Algorithm
		for (int k = 1; k < v + 1; k++) {

			for (int i = 0; i < v; i++) {

				for (int j = 0; j < v; j++) {

					if (d[k - 1][i][j] < (d[k - 1][i][k - 1] + d[k - 1][k - 1][j])) {
						d[k][i][j] = d[k - 1][i][j];
					} else {
						d[k][i][j] = (d[k - 1][i][k - 1] + d[k - 1][k - 1][j]);
						pi[i][j] = pi[i][k - 1];
					}

				}
			}

		}

		// When going from same vertex set pi to null.
		for (int i = 0; i < v; i++) {
			pi[i][i] = null;
		}

		return pi;
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	// reads in an undirected graph from a specific file formatted with one
	// x/y node coordinate per line:
	private static Graph InputGraph(String vFile, String eFile) {

		Graph g = new Graph();
		// vFile is list of (x coord, y coord) tuples
		try (Scanner f = new Scanner(new File(vFile))) {
			while (f.hasNextDouble())
				g.addVertex(f.nextDouble(), f.nextDouble());
		} catch (IOException e) {
			System.err.println("Cannot open file " + vFile + ". Exiting.");
			System.exit(0);
		}

		// eFile is list of (src ID, dst ID, cost) tuples
		try (Scanner f = new Scanner(new File(eFile))) {
			while (f.hasNextInt())
				g.addEdge(f.nextInt(), f.nextInt(), f.nextDouble());
		} catch (IOException e) {
			System.err.println("Cannot open file " + eFile + ". Exiting.");
			System.exit(0);
		}

		return g;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String vFile, eFile;

		System.out.printf("Enter <vertices file> <edges file>\n");
		System.out.printf("(e.g: verts/small1 edges/small1)\n");
		vFile = s.next();
		eFile = s.next();

		// read in vertices
		Graph g = InputGraph(vFile, eFile);

		Vertex paths[][] = FindAllPaths(g);

		System.out.println("next array:");
		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths.length; j++) {
				if (paths[i][j] == null)
					System.out.printf("%3s", "x");
				else
					System.out.printf("%3d", paths[i][j].ID);
			}
			System.out.println();
		}
		s.close();

	}

}
