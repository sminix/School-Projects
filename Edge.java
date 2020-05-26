package dijkstra;

/**
 * Edge the class stored in the adjacency lists or the graph
 * @author Sam
 */
public class Edge {
	int source;			//source vertex
	int dest;			//destination vertex
	int weight;			//weight of the edge
	
	/**
	 * constructor
	 * @param source integer of the source vertex
	 * @param dest intgert of the destination vertex
	 * @param weight integer of the weight of the edge
	 */
	public Edge(int source, int dest, int weight){
		this.source = source;
		this.dest = dest;
		this.weight = weight;
	}
}
