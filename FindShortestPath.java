package dijkstra;

import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * FindShortestPath implements implements dijkstras algorithm to find the shortest path from New York distance data set
 * I am getting the correct weight, but my file path is not correct. It is including vertices that are not
 * in the path to the target vertex. 
 * @author Sam
 */
public class FindShortestPath {

	/**
	 * Dijktra function finds the shortest path from root vertice to the target vertex
	 * It then writes the output to a file
	 * @param graph hashmap of the vertex and adjacency list
	 * @param root integer indentifies source node
	 * @param dest integer to identify the target node
	 * @param fileName String the name of the output file
	 * @throws IOException
	 */
	private static void dijkstra(Map<Integer, ArrayList<Edge>> graph, Integer root, Integer dest , String fileName) throws IOException{

		int mapSize = graph.size();
		ArrayList<Edge> q = new ArrayList<Edge>(); //priority queue of vertices to visit
		
		ArrayList<Integer> dist = new ArrayList<Integer>(); //Array list of distances from source to each vertex
		ArrayList<Boolean> visited = new ArrayList<Boolean>(); //Array list if edge is included in Shortest path tree
		
		ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();

		//populate all arraylists
		for (int i = 0; i < mapSize; i ++){
			dist.add(i, Integer.MAX_VALUE); //distances are all infinite for now
			visited.add(i, false);			//nodes are not in the shortest path tree yet
			paths.add(i, new ArrayList<Integer>());
		}
		int source = root;
	
		paths.get(root - 1).add(root); //source is added to trail list
		
		//paths.set(source - 1, trail); //Add trail to paths list
		dist.set(source - 1, 0); //source vertex has a distance of 0
		visited.set(source - 1, true); //and has been visited
		
		//for all the Edges in the vertex adjacency list
		for (int count = 0; count < graph.get(source).size(); count ++){
			Edge e = graph.get(source).get(count);
			q.add(e); //add all edges to the queue
		}
		
		while(q.isEmpty() != true){	//while the queue is not empty

			PriorityQueue1.sort(q);	//sort queue
			Edge curr = PriorityQueue1.poll(q);	//curr is top of priority queue

			source = curr.source;	//source vertex of edge
			int target = curr.dest;	//target vertex polled edge
			int weight = curr.weight; //weight is cost of this edge
			
			ArrayList<Integer> addition = new ArrayList<Integer>(paths.get(source - 1)); //addition is initialize using list from paths of current source
			int sourceWeight = dist.get(source - 1); //get source and destination weight from distance array
			int destWeight = dist.get(target - 1);
			
			addition.add(target); //add target to list of nodes from source
			
			
			if (sourceWeight + weight < destWeight){ //if the source weight plus the edge weight is less than the current weight to the destination
				destWeight = sourceWeight + weight;	//update destination weight, because smaller path is known
				dist.set(target - 1, destWeight);
	
			}
			if (visited.get(target - 1) == false){ //if the target vertex has not been visited yet
				for (int count = 0; count < graph.get(target).size(); count ++){
					Edge e = graph.get(target).get(count); //get each edge from the target vertex
					q.add(e); //add all edges to the queue
				}
				visited.set(target - 1, true); //the edges have been added to the queue, so the vertex is considered visited
				paths.set(target - 1, addition); //add addition to list of paths from root to target
			}

			if (visited.get(dest - 1) == true){ //if target node has been visited, clear the queue
				q.clear(); //clearing the queue stops the while loop
			}
			
		}
		//This section writes to the output file
		ArrayList<Integer> path = new ArrayList<Integer>(paths.get(dest - 1));
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writer.write(Integer.toString(dist.get(dest - 1)));
		writer.newLine();
		for (int i = 0; i < path.size(); i++){
			writer.write(Integer.toString(path.get(i)));
			writer.newLine();
		}
		writer.close();
					
	}
	
	
	
	
	/**
	 * Driver method of FindShortestPath. Takes user input for the input and output file names and the
	 * beginning and final vertex
	 * @param args
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		//reader reads console for input from user
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter input file name: ");
		String inFile = reader.readLine();
		
		System.out.println("Enter source vertex: ");
		String sourceIn = reader.readLine();
		int source = Integer.parseInt(sourceIn);
		
		System.out.println("Enter destination vertex: ");
		String destIn = reader.readLine();
		int dest = Integer.parseInt(destIn);
		
		System.out.println("Enter output file name: ");
		String outFile = reader.readLine();
		
		//reads input file
		File f = new File(inFile); // file "/Users/Sam/Downloads/USA-road-d.NY.gr"
		
		BufferedReader b = new BufferedReader(new FileReader(f)); //initialize file reader
		String line = null;
		
		Map<Integer, ArrayList<Edge>> Vertices = new HashMap<Integer, ArrayList<Edge>>(); //create map

		while ((line = b.readLine()) != null){ //while there are still lines in the input file
			
			String[] parts = line.split(" "); //split line into a list of strings on white space
			ArrayList<Edge> adjVertices = new ArrayList<Edge>(); //create adjacency list

			
			if (parts[0].equals("a")){					//0 is the code for what the line contains
				
				Integer x = Integer.parseInt(parts[1]); //1 is the source
				Integer y = Integer.parseInt(parts[2]);	//2 is the destination
				Integer z = Integer.parseInt(parts[3]);	//3 is the distance
				
				Edge E = new Edge(x,y,z);					//the edge is the destination and distance
				adjVertices.add(E);						//add edge to list
				
				if (Vertices.containsKey(x)){			//if vertex is already in map
					
					ArrayList<Edge> old;				//retrieve old list
					old = Vertices.get(x);
					adjVertices.addAll(old); 			//add adjVertices to list				
					Vertices.put(x, adjVertices);		//add vertex back in with updated adjacency list
				}
				
				else{					
					Vertices.put(x, adjVertices);		//else add vertex and adjacency list
				}

			}
		}
		b.close(); //close file reader
		
		dijkstra(Vertices, source, dest, outFile); //call dijkstra
		
		
		
		
	}
	
}
