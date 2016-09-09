package org.cytoscape.MCDS.MCDS.internal;

import java.util.*;
/**
 * MCDS core class
 * @author Maryam Nazarieh
 */
public class ComputeMCDS {

//******dominating_set***************************************
public static ArrayList<Gene> dominating_set(ArrayList<Gene> arr){
			Quicksort sorter = new Quicksort();
			Network.set_all_white(arr);
			
			arr.sort(null);
			sorter.sort_degree(arr);
			sorter.sort_succ_degree(arr);
			
			ArrayList<Gene> ds = new ArrayList<Gene>();
			int network_coverage_CC = 0;
			for(int i=arr.size()-1; i > -1;i--){
				if( arr.get(i).get_color().equals("white") && arr.get(i).get_degree_succ() > 0){
					arr.get(i).set_black();
					ds.add(arr.get(i));
					for(int j = 0; j < arr.get(i).get_degree_succ();j++){
						if(arr.get(i).get_successors(j).get_color().equals("white")){
							arr.get(i).get_successors(j).set_gray();
							network_coverage_CC = network_coverage_CC + 1;
						}
					}
					network_coverage_CC = network_coverage_CC + 1;
				}
			}
			for(int i=arr.size()-1; i > -1;i--){
				if( arr.get(i).get_color().equals("white") && arr.get(i).get_degree_succ() > -1){
					arr.get(i).set_black();
					network_coverage_CC = network_coverage_CC + 1;
				}
			}
			//System.out.println("The number of genes versus number of coverage: " + arr.size()+"\t"+network_coverage_CC);
			return ds;
}
//******findConnectors*********************************
public static ArrayList<Gene> findConnectors(ArrayList<Gene> component,ArrayList<Gene> ds){
			  Quicksort sorter = new Quicksort();	
			  ArrayList<Gene> black_darkGray = new ArrayList<Gene>();
			  ArrayList<ArrayList<Gene>> set = findBlackNeighbor(component,ds);
			  ArrayList<Gene> set_gene = new ArrayList<Gene>();
			  for(int i = 0;i < set.size();i++)
				  set_gene.add(set.get(i).get(0));
			  sorter.sort_black_degree(set_gene);
			  ArrayList<Gene> black = new ArrayList<Gene>();
			  Network.set_all_False(set_gene);
			  Network.set_all_blue(set_gene);
			  for(int i = 0;i < set_gene.size();i++){
				 if(set_gene.get(i).get_color().equals("black")){
					 black_darkGray.add(set_gene.get(i));
					 black_darkGray.get(black_darkGray.size()-1).set_red();
					 black.add(set_gene.get(i));	
				 }
			 }
			 boolean flag = false;
		     if(black_darkGray.size() > 0){	
		    	for(int m=0; m < black_darkGray.size(); m++){
		    		Network.set_all_False(black_darkGray);
		    		flag = check_connectivity_constrained(black_darkGray,m);
		    		if(flag){
		    			//System.out.println("\nThe black nodes are connected. please don't wait for Gray nodes.");
		    			break;
		    		}
		    	}	
		    	for(int i = set_gene.size()-1;i > -1;i--){
		    		if(!flag){
		    			if((set_gene.get(i).get_color().equals("gray") || set_gene.get(i).get_color().equals("white")) && set_gene.get(i).get_degree() > 0){
		    				black_darkGray.add(set_gene.get(i));
		    				black_darkGray.get(black_darkGray.size()-1).set_red();//
		    				black_darkGray.get(black_darkGray.size()-1).set_darkGray();
		    				for(int j = 0; j < black_darkGray.size(); j++){
		    					Network.set_all_False(black_darkGray);
		    					flag = check_connectivity_constrained(black_darkGray,j);
		    					if(flag)
		    						break;
		    				}
		    			}
		    		}
		    		
		    	}
		    }
			return black_darkGray;
}
//******mimicDS_CC*********************************
public static ArrayList<Gene> mimicDS(ArrayList<Gene> component,ArrayList<Gene> connectedSet){
			Quicksort sorter = new Quicksort();
			sorter.sort_degree(connectedSet);
			sorter.sort_succ_degree(connectedSet);
			int i = 0;
			int counter = connectedSet.size();
			while(i < counter){
				//System.out.println("mimicDS_CC is:" + i);
				Gene g = connectedSet.get(0);
				g.set_blue();
				connectedSet.remove(g);
				boolean flag = false;
				for(int m=0; m < connectedSet.size(); m++){
					Network.set_all_False(connectedSet);
					Network.set_all_blue(component);
					Network.set_all_red(connectedSet);
				    flag = check_connectivity_constrained(connectedSet,m);
				    if(flag){
				    	Network.set_all_blue(connectedSet);
				    	Network.set_all_blue(component);
				    	int num = network_coverage(connectedSet,component);
				    	if(num != component.size()){
				    		connectedSet.add(g);
							break;
				    	}
				    	else{
				    		break;
				    	}
				    }	
				 }//end for
				if(!flag){
					connectedSet.add(g);
				}
				i++;
			}	
			Network.set_all_blue(component);
			return connectedSet;
}
//******findBlackNeighbor*********************************
public static ArrayList<ArrayList<Gene>> findBlackNeighbor(ArrayList<Gene> component,ArrayList<Gene> ds){
 		ArrayList<ArrayList<Gene>> set = new ArrayList<ArrayList<Gene>>();
		for(int i = 0; i < component.size();i++){
			if(component.get(i).get_color().equals("gray") || component.get(i).get_color().equals("black")){
				ArrayList<Gene> gray_nw = new ArrayList<Gene>();
				gray_nw.add(component.get(i));
				for(int j = 0; j < component.get(i).get_degree();j++)
					for(int z = 0;z < ds.size(); z++){
						if(component.get(i).get_interactor(j).get_name().equals(ds.get(z).get_name()))
							if(ds.get(z).get_color().equals("black") && !(component.get(i).get_name()).equals(ds.get(z).get_name()))
								gray_nw.add(ds.get(z));			
				    }
				
				set.add(gray_nw);
				//set.get(i).get(0).set_black_degree(gray_nw.size()-1);
			}
		}
		Network.findMax(set);
		return set;		
}

//****Breadth First Search to test if the graph is connected and find independent connected components***************************
public static ArrayList<Gene> BreadthFirstSearch_constrained(ArrayList<Gene> grn, int index){
			ArrayList<Gene> visited_nodes = new ArrayList<Gene>();
			Queue<Gene> queue = new LinkedList<Gene>();
			queue.add(grn.get(index));
			grn.get(index).set_True();
			while(!queue.isEmpty()) {
				Gene g = (Gene)queue.remove();
				visited_nodes.add(g);
				Gene child=null;
				int i = 0;
				while((i < g.interactor_size())){
					if(g.get_interactor(i).get_color_component().equals("red") && g.get_interactor(i).status() == false){
						child = g.get_interactor(i);
						child.set_True();
						queue.add(child);
					}
					i++;
				}
			}
			return visited_nodes;
}
//******check_connectivity_constrained*********************************
public static boolean check_connectivity_constrained(ArrayList<Gene> grn, int index){
			ArrayList<Gene> visited_nodes = new ArrayList<Gene>();
			int counter = 0;
			Boolean flag = false;
			visited_nodes = BreadthFirstSearch_constrained(grn,index);
			for(int i = 0; i < grn.size();i++){
			    int j = 0;
			    while(j < visited_nodes.size()){
			    	if(!(grn.get(i).get_name().equalsIgnoreCase(visited_nodes.get(j).get_name())))
						j++;
			    	else
			    		break;
			    }
			    if(j == visited_nodes.size())
			    	counter++; 
			}
			if(counter > 0)
			    flag = false;
			else
			    flag = true;
			return flag;
}	
//******network coverage************************
public static int network_coverage(ArrayList<Gene> ConnectedSet,ArrayList<Gene> component){
			int counter = 0;
			for(int i = 0; i < ConnectedSet.size(); i++){
					ConnectedSet.get(i).set_red();
					for(int j = 0; j < ConnectedSet.get(i).successors_size(); j++)
						ConnectedSet.get(i).get_successors(j).set_red();
			}
			for(int i = 0; i < component.size(); i++)
				if(component.get(i).get_color_component().equals("red"))
					counter++;
			return counter;
}

}//end class
