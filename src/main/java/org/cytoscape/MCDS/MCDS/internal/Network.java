package org.cytoscape.MCDS.MCDS.internal;

import java.util.*;

/**
 * MCDS core class
 * @author Maryam Nazarieh
 */
public class Network {

//******This function finds Distinct Genes*****************************************
public static ArrayList<String> findDistinctGene(ArrayList<String> str){//incomplete
			ArrayList<String> arr = new ArrayList<String>();
			
			for(String strRead : str ){
				String[] input = strRead.split(",|\t|\\s+");
				int i = 0;
				while(i != arr.size()){
					if(!(input[0].equalsIgnoreCase(arr.get(i))))
						i++;
					else
						break;
				}
				if(i == arr.size())
					arr.add(input[0]);
				
				i = 0;
				while(i != arr.size()){
					if(!(input[1].equalsIgnoreCase(arr.get(i))))
						i++;
					else
						break;
				}
				if(i == arr.size())
					arr.add(input[1]);
			}
		//System.out.println("The number of genes is : " + arr.size());
		return arr;
}

//*******This function creates a network for directed and undirected graph*****************************************

public static ArrayList<Gene> construct_network_CC(String component,ArrayList<String> geneList, ArrayList<String> str){
			ArrayList<Gene> GRN = new ArrayList<Gene>();//create identity for each gene in the geneList
			for(int i = 0; i < geneList.size(); i++){
				Gene g1 = new Gene(geneList.get(i).toString());
				GRN.add(g1);
			}
			for(int i = 0; i < geneList.size(); i++){
				Gene g1 = GRN.get(i);
					for(String strRead : str ){
						String[] input = strRead.split(",|\t|\\s+");
						if(geneList.get(i).equalsIgnoreCase(input[0])){
							int j = 0;
							while(j != g1.get_degree()){
								if(!(input[1].equalsIgnoreCase(g1.get_interactor(j).get_name())))
									j++;
								else
									break;
							}
							if(j == g1.get_degree())
								for(int z = 0; z < geneList.size(); z++)
									if((input[1].equalsIgnoreCase(GRN.get(z).get_name()))){
										g1.insert(GRN.get(z));
									}
							j = 0;
							while(j != g1.get_degree_succ()){
								if(!(input[1].equalsIgnoreCase(g1.get_successors(j).get_name())))
									j++;
								else
									break;
							}
							if(j == g1.get_degree_succ())
								for(int z = 0; z < geneList.size(); z++)
									if((input[1].equalsIgnoreCase(GRN.get(z).get_name()))){
										g1.insert_succ(GRN.get(z));
									}
						}
						//if(!(input[0].toUpperCase().equalsIgnoreCase(input[1].toUpperCase())))
						if(geneList.get(i).equalsIgnoreCase(input[1])){
								int j = 0;
								while(j != g1.get_degree()){
									if(!(input[0].equalsIgnoreCase(g1.get_interactor(j).get_name())))
										j++;
									else
										break;
								}
							 if(component.equals("LCC")){
								if(j == g1.get_degree())
									for(int z = 0; z < geneList.size(); z++)
										if((input[0].equalsIgnoreCase(GRN.get(z).get_name()))){
											g1.insert(GRN.get(z));
										}
							 }
								j = 0;
								while(j != g1.get_degree_pred()){
									if(!(input[0].equalsIgnoreCase(g1.get_predecessors(j).get_name())))
										j++;
									else
										break;
								}
								if(j == g1.get_degree_pred())
									for(int z = 0; z < geneList.size(); z++)
										if((input[0].equalsIgnoreCase(GRN.get(z).get_name()))){
											g1.insert_pred(GRN.get(z));
										}
							}
					}//END WHILE
			}
			int counter = 0;
			for(Gene g: GRN)
				counter = counter + g.successors_size();
			//double network_density = (counter) /(0.5 * geneList.size() * (geneList.size()-1));
			//System.out.println(geneList.size() + "\t" + counter + "\t" +  network_density);
			return GRN;	
}
//******get_index*********************************		
public static int get_index(ArrayList<Gene> grn, String str){
		int i;
		for(i = 0; i < grn.size(); i++){
			if(grn.get(i).get_name().equalsIgnoreCase(str)){
				break;
			}
		}
		return i;
}
//******find_max******************************************
public static int find_max(ArrayList<ArrayList<Gene>> arr){
			int max = 0;
			int val = 0;
			for(int i = 0; i < arr.size();i++){
				if(arr.get(i).size() > val){
					val = arr.get(i).size();
					max = i;
				}
			}
			return max;
}
//******findMax*****************************************
public static int findMax(ArrayList<ArrayList<Gene>> set){
		   int max_degree = 0;
		   int max_index = 0;
		   for(int d = 0; d < set.size();d++)
		      if (set.get(d).size() > max_degree){ 
		    	  max_degree = set.get(d).size();
		    	  max_index = d; 
		   }
		   return max_index;
}
//******set_all_blue*********************************
public static void set_all_blue(ArrayList<Gene> grn){
		for(Gene g : grn)
			g.set_blue();
}
//******set_all_blue*********************************
public static void set_all_red(ArrayList<Gene> grn){
			for(Gene g : grn)
				g.set_red();
}
//******set_all_False*********************************
public static void set_all_False(ArrayList<Gene> grn){
		for( Gene g : grn)
			g.set_False();
}
//******set_all_white*********************************
public static void set_all_white(ArrayList<Gene> grn){
		for( Gene g : grn)
			g.set_white();
}
//*************Display****************************
public static void display_mcds_cc(ArrayList<Gene> arr){
			int counter = 0;
				for(Gene g : arr){
						counter++;
						System.out.println(g.get_name() + "\t"+ g.get_degree_succ()+ "\t"+ g.get_degree_pred()+ "\t"+g.get_color());
				}
			System.out.println("Number of elements in the MCDS : " + counter);
		}
			
}
