package edu.wit.cs.comp3370;

// provides an interface for any graph
public interface GraphInterface {
	
	void addVertex(double x, double y);
	
	Vertex[] getVertices();
	Edge[] getEdges();
	
	double getTotalEdgeWeight();
}
