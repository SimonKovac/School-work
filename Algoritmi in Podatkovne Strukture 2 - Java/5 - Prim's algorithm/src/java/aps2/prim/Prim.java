package aps2.prim;
import java.util.*;

/**
 * Prim's algorithm to compute the minimum spanning tree on the given graph.
 */
public class Prim {
	int[][] data; // prices of edges in the graph of size n*n
	int n;
	
	int cost;
	boolean[] jePokrita;

	public Prim(int n) {
		data = new int[n][n];
		this.n = n;
	}

	public Prim(int[][] d) {
		data = d;
		n = data.length;
	}

	/**
	 * Adds a bi-directional edge from node i to node j of price d into the cost
	 * matrix.
	 * 
	 * @param i Source node
	 * @param j Target node
	 * @param d Price
	 */
	public void addEdge(int i, int j, int d) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		this.data[i][j] = d;
		this.data[j][i] = d;
	}
	
	/**
	 * Returns the total cost of the minimum spanning tree. ie. The sum of all
	 * edge weights included in the minimum spanning tree.
	 * 
	 * @return The total cost of the minimum spanning tree.
	 */
	public int MSTcost() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		return this.cost;
	}

	/**
	 * Computes the minimum spanning tree on the graph using Prim's algorithm.
	 * If two edges share the same price, follow the edge with the smaller target node index.
	 * 
	 * @param source Initial vertex
	 * @return An array of visited edges in the correct order of size n-1. The edge in the array from node u to node v is encoded as u*n+v.
	 */
	public int[] computeMST(int source) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		int nextNode = 0;
		int nextCost = 0 ;
		int currCost = 0;
		int currNode = 0;
		int[] path = new int[n-1];
		
		this.cost = 0;
		this.jePokrita = new boolean[n];
		
		this.jePokrita[source] = true;
		
		for(int step = 0; step < n - 1; step++) {
			currCost = Integer.MAX_VALUE;
			for(int i = 0; i < n; i++) {
				if(!this.jePokrita[i]) continue;
				for(int j = 0; j < n; j++) {
					if(this.jePokrita[j]) continue;
					nextCost = this.data[i][j];
					if(nextCost == 0) continue;
					if(nextCost < currCost) {
						currCost = nextCost;
						currNode = i;
						nextNode = j;
					}
				}
			}
			this.jePokrita[nextNode] = true;
			this.cost += currCost;
			path[step] = currNode * this.n + nextNode;
		}
		
		return path;
	}
}
