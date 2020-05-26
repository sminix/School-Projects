package dijkstra;

import java.util.ArrayList;

/**
 * PriorityQueue builds a min heap to create the priority queue to pick the next edge, based on edge weight
 * I decided to use a max heap because how I sorted it goes from high to low, which is not how we 
 * want the priority queue. I am including it because I got it to work
 * @author Sam
 */
public class PriorityQueue {
	
	/**
	 * Sort sorts the min heap. My code sorts into high to low, which is the opposite of 
	 * what I want to happen
	 * @param A Array list of edges
	 */
	public static void sort(ArrayList<Edge> A){
		int n = A.size();
		
		for (int i = (n/2)-1; i>=0; i--){ //in an unsorted list, all leaves are valid
			minHeapify(A, n, i); //so heapify non leaf nodes
		}
		
		for(int i = n-1; i >= 1; i--){
			Edge temp = A.get(0); //swap root with the last element
			A.set(0, A.get(i));
			A.set(i,  temp);
			
			minHeapify(A, i, 0); //minHeapify at the root
		}
	}
	
	/**
	 * poll removes and returns the smallest weight edge
	 * @param A Array list of edges
	 * @return Edge smallest, which is the smallest weight edge
	 */
	public static Edge poll(ArrayList<Edge> A){	//removes and returns root I think this is where the problem is
		int n = A.size();
		Edge smallest = A.get(n-1);				//Smallest value is the root
		A.remove(n-1);
		n = A.size();
		minHeapify(A, 0, n - 1);				//heapify whole heap
		return smallest;						//return smallest value
	}
	
	/**
	 * Decrease key changes the value of an edge in the heap and restores the heap property
	 * @param A Array list of edges
	 * @param index integer of where want to change value
	 * @param value Edge of the new value
	 */
	public static void decreaseKey(ArrayList<Edge> A, int index, Edge value){
		A.set(index, value); 
		while(index != 0 && A.get((index - 1)/2).weight > A.get(index).weight){//while index is not the root and the parent is larger than the index
			A.set(index, A.get((index - 1)/2)); 			//set value at index equal to value at parent
			index = (index - 1)/2;						//change index to parent
			A.set(index, value);							//change value at index(now the parent) to value given
		}
	}
	
	/**
	 * Min heapify ensures that each parent is smaller than its two children
	 * Based on the edge weight
	 * @param list array list of edges
	 * @param i index of where want to consider heapifying
	 * @param r index of parent
	 */
	private static void minHeapify(ArrayList<Edge> list, int i, int r){
		
		if (list.isEmpty() == false){
			int small = r;
			int left = (2*r) + 1;//find left and right child
			int right = (2*r) + 2;
			
			if (left < i && list.get(left).weight < list.get(small).weight){ //if left child is in list we are sorting and 
				small = left;						//smaller than the parent, swap small value
			}
		
			if (right < i && list.get(right).weight < list.get(small).weight){
				small = right;
			}
			
			if (small != r){ 	//if smallest value is not the parent
				Edge temp = list.get(r); //swap them
				list.set(r, list.get(small));
				list.set(small, temp); 
			
				minHeapify(list, i, small); //call minHeapify on new location of target value
			}
		}
	}
}
