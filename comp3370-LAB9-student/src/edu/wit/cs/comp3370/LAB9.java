package edu.wit.cs.comp3370;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/* Calculates the minimal spanning tree of a graph 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 9
 * Eli Kapetanopoulos
 */

public class LAB9 {

	// TODO document this method
	public static void FindMST(Graph g) {

		Vertex[] vertices = g.getVertices();

		for (int i = 0; i < vertices.length; i++) {
			for (int j = 0; j < vertices.length; j++) {
				if (vertices[i] != vertices[j]) {

					// TODO generate edges from graph vertices
					g.addEdge(vertices[i], vertices[j]);
				}

			}
		}

		// Add Edges to LinkedHashSet in order to remove Duplicate edges in the
		// Array Since the hash set does not allow for dups. Then add edges back
		// to array edges. Sort Array.
		Set<Edge> nonDuplicate = new LinkedHashSet<Edge>(Arrays.asList(g.getEdges()));
		Edge[] edges = nonDuplicate.toArray(new Edge[nonDuplicate.size()]);
		Arrays.sort(edges);

		// empty edges from graph arraylist
		g.EmptyEdges();

		// Kruskal's Algorithm
		for (int i = 0; i < vertices.length; i++) {
			MakeSet(vertices[i]);
		}

		// TODO populate graph with MST edges
		for (int i = 0; i < edges.length; i++) {

			if (findSet(edges[i].src) != findSet(edges[i].dst)) {
				g.addEdge(edges[i].src, edges[i].dst);
				Union(edges[i].src, edges[i].dst);
			}
		}

	}

	// Make set
	private static void MakeSet(Vertex x) {

		x.parent = x;
		x.rank = 0;
	}

	// Utility function to find set of an element i
	private static Vertex findSet(Vertex x) {
		if (x != x.parent) {
			x.parent = findSet(x.parent);

		}
		return x.parent;
	}

	// Track trees height, put shorter tree under taller
	private static void Union(Vertex u, Vertex v) {
		Vertex x = findSet(u);
		Vertex y = findSet(v);

		if (x.rank > y.rank) {
			y.parent = x;
		} else
			x.parent = y;
		if (x.rank == y.rank) {
			y.rank = y.rank + 1;
		}
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	// reads in an undirected graph from a specific file formatted with one
	// x/y node coordinate per line:
	private static Graph InputGraph(String file1) {

		Graph g = new Graph();
		try (Scanner f = new Scanner(new File(file1))) {
			while (f.hasNextDouble()) // each vertex listing
				g.addVertex(f.nextDouble(), f.nextDouble());
		} catch (IOException e) {
			System.err.println("Cannot open file " + file1 + ". Exiting.");
			System.exit(0);
		}

		return g;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String file1;

		System.out.printf("Enter <points file> <edge neighborhood>\n");
		System.out.printf("(e.g: points/small .5)\n");
		file1 = s.next();

		// read in vertices
		Graph g = InputGraph(file1);
		g.epsilon = s.nextDouble();

		FindMST(g);

		s.close();

		System.out.printf("Weight of tree: %f\n", g.getTotalEdgeWeight());
	}

}
