package edu.wit.cs.comp3370;

public class Edge implements Comparable<Edge> {

	@Override
	public int compareTo(Edge o) {
		// TODO Auto-generated method stub

		if (this.cost < o.cost)
			return -1;
		else if (o.cost < this.cost)
			return 1;
		return 0;

	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	public Vertex src;
	public Vertex dst;
	public double cost;

	// creates an edge between two vertices
	Edge(Vertex s, Vertex d, double c) {
		src = s;
		dst = d;
		cost = c;
	}

}
