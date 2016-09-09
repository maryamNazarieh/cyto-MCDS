package org.cytoscape.MCDS.MCDS.internal;

import java.util.*;
/**
 * MCDS core class
 * @author Maryam Nazarieh
 */
class Gene implements Comparable<Gene> {
	private String name;
	private int degree = 0;
	private int degree_pred = 0;
	private int degree_succ = 0;
	private int black_degree = 0;
	private ArrayList<Gene> interactors = new ArrayList<Gene>();
	private ArrayList<Gene> predecessors = new ArrayList<Gene>();
	private ArrayList<Gene> successors = new ArrayList<Gene>();
	private String color = "white";
	private String color_component = "green";
	private Boolean visited = false;
	private int index = 0;
	private int lowlink = 0;
	private int level = 0;
	 
	
	public Gene(String str){
		name = str;
		degree = 0;
	}
	
	public Gene(){
		
	}
	
	public void set_level(int l){
        level = l;
    }
	public int get_level(){
        return level;
    }
	public void set_name(String str){
        name = str;
    }
	public int get_Index(){
		return index;
	}
	public void set_Index(int ind){
		index = ind;
	}
	public int get_Lowlink(){
		return lowlink;
	}
	public void set_Lowlink(int ll){
		lowlink = ll;
	}
	public ArrayList<Gene> interactors(Gene g){
		return interactors;
	}
	public ArrayList<Gene> predecessors(Gene g){
		return predecessors;
	}
	public ArrayList<Gene> successors(Gene g){
		return successors;
	}
	public int get_black_degree(){
		return this.black_degree;
	}
	
	public void set_black_degree(int d){
		black_degree = d;
	}
	
	public int successors_size(){
		return successors.size();
	}
	public int predecessors_size(){
		return predecessors.size();
	}
	public int interactor_size(){
		return interactors.size();
	}
	
	public void set_True(){
		visited = true;
	}
	
	public void set_False(){
		visited = false;
	}
	
	public Boolean status(){
		return visited;
	}
	
	public Gene get_interactor(int i){
		return this.interactors.get(i);
	}
	
	public Gene get_successors(int i){
		return this.successors.get(i);
	}
	
	public Gene get_predecessors(int i){
		return this.predecessors.get(i);
	}
	
	public void insert_pred(Gene g){
		predecessors.add(g);
		degree_pred++;
	}
	public void insert_succ(Gene g){
		successors.add(g);
		degree_succ++;
	}
	public void delete_pred(Gene g){
		predecessors.remove(g);
		degree_pred--;
	}
	public void delete_succ(Gene g){
		successors.remove(g);
		degree_succ--;
	}
	public void insert(Gene g){
		interactors.add(g);
		degree++;
	}
	public void delete(Gene g){
		interactors.remove(g);
		degree--;
	}
	
	public String get_color(){
		return color;
	}
	
	public void set_black(){
		color = "black";
	}
	
	public void set_gray(){
		color = "gray";
	}
	
	public void set_darkGray(){
		color = "darkGray";
	}
	
	public String get_color_component(){
		return color_component;
	}
	
	public void set_red(){
		color_component = "red";
	}
	
	public void set_blue(){
		color_component = "blue";
	}
	public void set_white(){
		color = "white";
	}
	public String get_name(){
		return name;
	}
	
	public int get_degree(){
		return degree;
	}
	public void set_degree(int i){
		degree = i;
	}
	public int get_degree_pred(){
		return degree_pred;
	}
	public int get_degree_succ(){
		return degree_succ;
	}
	
	public void display_interactors(Gene g){
		for(int i = 0; i < g.interactors.size(); i++)
			System.out.print(g.interactors.get(i).name + "\t");	
	}
	
	public String toString() {
		return name + " " + lowlink;
	}

	@Override
	public int compareTo(Gene o) {
		return name.compareTo(o.get_name());
	}
	
}
