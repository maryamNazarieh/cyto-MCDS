package org.cytoscape.MCDS.MCDS.internal;

import java.util.*;
/**
 * MCDS core class
 * @author Maryam Nazarieh
 */
public class Quicksort {
  private ArrayList<Gene> GeneSet = new ArrayList<Gene>();
  private int number;
  
//sort_degree**********************************
 public void sort_degree(ArrayList<Gene> g) {
    if (g ==null || g.size()==0){
      return;
    }
    this.GeneSet = g;
    number = g.size();
    quicksort_degree(0, number - 1);
}
//quicksort_degree**********************************
 private void quicksort_degree(int low, int high) {
    int i = low, j = high;
    int pivot = GeneSet.get(low + (high-low)/2).get_degree();
    while (i <= j) {
      while (GeneSet.get(i).get_degree() < pivot) {
        i++;
      }
      while (GeneSet.get(j).get_degree() > pivot) {
        j--;
      }
      if (i <= j) {
        exchange(i, j);
        i++;
        j--;
      }
    }
    if (low < j)
      quicksort_degree(low, j);
    if (i < high)
      quicksort_degree(i, high);
 }
//sort_succ_degree**********************************
 public void sort_succ_degree(ArrayList<Gene> g) {
	    if (g ==null || g.size()==0){
	      return;
	    }
	    this.GeneSet = g;
	    number = g.size();
	    quicksort_succ_degree(0, number - 1);
}
//quicksort_succ_degree**********************************
private void quicksort_succ_degree(int low, int high) {
	int i = low, j = high;
	int pivot = GeneSet.get(low + (high-low)/2).get_degree_succ();
	while (i <= j) {
		while (GeneSet.get(i).get_degree_succ() < pivot) {
	        i++;
	     }
	    while (GeneSet.get(j).get_degree_succ() > pivot) {
	        j--;
	     }
	     if (i <= j) {
	        exchange(i, j);
	        i++;
	        j--;
	     }
	 }
	if (low < j)
		quicksort_succ_degree(low, j);
	if (i < high)
	    quicksort_succ_degree(i, high);
} 
//sort_black_degree**********************************  
 public void sort_black_degree(ArrayList<Gene> g) {
	    if (g ==null || g.size()==0){
	      return;
	    }
	    this.GeneSet = g;
	    number = g.size();
	    quicksort_black_degree(0, number - 1);
}
//quicksort_black_degree**********************************
 private void quicksort_black_degree(int low, int high) {
	    int i = low, j = high;
	    int pivot = GeneSet.get(low + (high-low)/2).get_black_degree();
	    while (i <= j) {
	      while (GeneSet.get(i).get_black_degree() < pivot) {
	        i++;
	      }
	      while (GeneSet.get(j).get_black_degree() > pivot) {
	        j--;
	      }
	      if (i <= j) {
	        exchange(i, j);
	        i++;
	        j--;
	      }
	    }
	    if (low < j)
	      quicksort_black_degree(low, j);
	    if (i < high)
	      quicksort_black_degree(i, high);
}

//exchange**********************************
private void exchange(int i, int j) {
    Gene temp = GeneSet.get(i);
    GeneSet.set(i,GeneSet.get(j));
    GeneSet.set(j,temp);
  }
} 

