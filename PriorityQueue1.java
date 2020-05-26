package dijkstra;

import java.util.ArrayList;

/**
 * PriorityQueue1 uses a max heap to create the priority queue, sorting the edge based on weight
 * I prefered this method because the sorting made more sense 
 * @author Sam
 */
public class PriorityQueue1 {
	
	/**
	 * sort is a recursive algorithm. it takes the root (max)
	 * and swaps with the bottom. then heapify is done to get the new max to 
	 * the root. It sorts the list from low to high
	 * @param A arrray list of edges
	 */
	public static void sort(ArrayList<Edge> A){
		int n = A.size();
		
		for (int i = (n/2)-1; i>=0; i--){ //in an unsorted list, all leaves are valid
			maxHeapify(A, n, i); //so heapify non leaf nodes
		}
		
		for(int i = n-1; i >= 1; i--){
			Edge temp = A.get(0); //swap root with the last element
			A.set(0, A.get(i));
			A.set(i,  temp);
			
			maxHeapify(A, i, 0); //minHeapify at the root
		}
	}
	
	/**
	 * Poll returns and removes the root of the array list. If it has been sorted by the sort function,
	 * it returns the smallest edge
	 * @param A array list of edges, preferably sorted
	 * @return the smallest edge
	 */
	public static Edge poll(ArrayList<Edge> A){	//removes and returns root I think this is where the problem is
		
		Edge smallest = A.get(0);				//Smallest value is the root
		A.remove(0);
		int n = A.size();
		maxHeapify(A, 0, n - 1);				//heapify whole heap
		return smallest;						//return smallest value
	}
	
	/**
	 * maxHeapify is a method that ensures the max heap property is satisfied.
	 * it checks that the parent is larger than its 2 children. the heap parameter is based on the 
	 * weight of the edges
	 * @param list the heap
	 * @param i the size of the heap we are sorting
	 * @param r parent node index
	 */
	private static void maxHeapify(ArrayList<Edge> list, int i, int r){
		if (list.isEmpty() == false){
			int large = r;
			int left = (2*r) + 1;//find left and right child
			int right = (2*r) + 2;
		
			if (left < i && list.get(left).weight > list.get(large).weight){ //if left child is in list we are sorting and 
				large = left;						//larger than the parent, swap large value
			}
		
			if (right < i && list.get(right).weight > list.get(large).weight){
				large = right;
			}
		
			if (large != r){ 	//if largest value is not the parent
				Edge temp = list.get(r); //swap them
				list.set(r, list.get(large));
				list.set(large, temp); 
		
				maxHeapify(list, i, large); //call minHeapify on new location of target value
			}
		}
	}

}
