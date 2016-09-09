package org.cytoscape.MCDS.MCDS.internal;

import java.util.*;
/**
 * MCDS core class
 * @author Maryam Nazarieh
 */
public class Component extends Gene {

	public static int index;
	public static Stack<Gene> stack;
	public static ArrayList<ArrayList<Gene>> set;
	public ArrayList<ArrayList<Gene>> components;
	
//Find_SCC**********************************	
public ArrayList<Gene> Find_SCC(ArrayList<Gene> graph, ArrayList<String> gl,ArrayList<String> input_nw){
		index = 0;
		stack = new Stack<Gene>();
		set = new ArrayList<ArrayList<Gene>>();
		ArrayList<String> geneList_lcc = new ArrayList<String>();
		ArrayList<Gene> nw_cc = new ArrayList<Gene>();
		for(int i = 0; i < graph.size();i++){
			if (graph.get(i).get_Index() == 0){
				scc(graph,graph.get(i));
			}
		}
		
		int max = Network.find_max(set);
	    for(int i= 0;i < set.get(max).size();i++)
	    	geneList_lcc.add(set.get(max).get(i).get_name());
	    nw_cc = Network.construct_network_CC("LCC",geneList_lcc,input_nw);
		return nw_cc;//set of connected components
}
//scc**********************************
void scc(ArrayList<Gene> graph,Gene node){
		node.set_Index(index);
		node.set_Lowlink(index);
		index++;
		stack.push(node);
		for(int i = 0; i < node.successors_size();i++){
			Gene neighbor = node.get_successors(i);
			if(neighbor.get_Index() == 0){
				scc(graph,neighbor);
				node.set_Lowlink(Math.min(node.get_Lowlink(),neighbor.get_Lowlink()));
			}
			else if(stack.contains(neighbor)){
				node.set_Lowlink(Math.min(node.get_Lowlink(),neighbor.get_Index()));
			}
		}
		
		if(node.get_Lowlink() == node.get_Index()){
			ArrayList<Gene> subset = new ArrayList<Gene>();
			Gene neighbor = null;
			while(node != neighbor){
				neighbor = stack.pop();
				boolean remove = true;
				while (remove) // remove ALL occurances
					remove = stack.remove(neighbor);
				subset.add(neighbor);
			}
			set.add(subset);
		}
	}
//****Breadth First Search to test if the graph is connected and find disjoint connected components***************************
public  ArrayList<Gene> BreadthFirstSearch(ArrayList<Gene> grn, int index){
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
				if(g.get_interactor(i).status() == false){
					child = g.get_interactor(i);
					child.set_True();
					queue.add(child);
				}
				i++;
			}
		}
		return visited_nodes;
}
//******findConnectedComponent*********************************
public ArrayList<Gene> FindConnectedComponents(ArrayList<Gene> grn, ArrayList<String> gl,ArrayList<String> input_nw,String str){
		ArrayList<Gene> arr = new ArrayList<Gene>();
		ArrayList<Gene> nw_cc = new ArrayList<Gene>();
		Network.set_all_False(grn);
	    for(int i = 0; i < gl.size(); i++)
	    	arr.add(new Gene(gl.get(i)));
		ArrayList<ArrayList<Gene>> set = new ArrayList<ArrayList<Gene>>();	
		for(int i = 0; i < arr.size();i++){
			int index = Network.get_index(grn,arr.get(i).get_name());
			if(grn.get(index).status() == false){
				ArrayList<Gene> s = new ArrayList<Gene>();
				if(str.equals("LCCD"))
					Network.set_all_False(grn);
				s = BreadthFirstSearch(grn, index);
				set.add(s);
			}		
		}
		//System.out.println("The number of connected component is: " + set.size());
		int max = Network.find_max(set);
	    ArrayList<String> geneList_lcc = new ArrayList<String>();
	    for(int i= 0;i < set.get(max).size();i++)
	    	geneList_lcc.add(set.get(max).get(i).get_name());
	    if(str.equals("LCCD"))
	    	nw_cc = Network.construct_network_CC("LCCD",geneList_lcc,input_nw);
	    else
	    	nw_cc = Network.construct_network_CC("LCC",geneList_lcc,input_nw);
		return nw_cc;//set of connected components
}

}