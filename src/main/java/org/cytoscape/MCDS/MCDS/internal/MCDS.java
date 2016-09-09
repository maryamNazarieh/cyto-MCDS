package org.cytoscape.MCDS.MCDS.internal;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * MCDS core class
 * @author Maryam Nazarieh and Thorsten Will
 */
public class MCDS{
	
	public static void printHelp() {
		System.out.println("usage: java -jar MCDS.jar [component] [network] [output-file]");
		
		System.out.println("[component] :");
		System.out.println("	Any of the following components are possible:");
		System.out.println("		LCC : largest connected component (undirected)");
		System.out.println("		LCCD : largest connected component (directed)");
		System.out.println("		SCC : strongly connected component");
		
		System.out.println("[network] :");
		System.out.println("	Any directed network in the linewise format: source[separator]target ,");
		System.out.println("	where [separator] can be any whitespace or ','.");
		System.out.println("[output-file] :");
		System.out.println("	The outcome is written to this file in csv-format. If it does not exist, it is created.");
		
		System.exit(0);
	}
	
	/**
	 * Parse arguments
	 * @param args
	 */
	public static String[] parseInput(String[] args) {
		String[] parameters = new String[3];
		
		if (args.length != 3) {
			System.out.println("Wrong number of arguments.");
			printHelp();
		}
		
		// 0 : type of calc -> LCCD LCC SCC
		parameters[0] = args[0];
		if (!args[0].equals("LCCD") && !args[0].equals("LCC") && !args[0].equals("SCC")) {
			System.out.println("Please specify the exact component as LCC, LCCD or SCC.");
			System.out.println("LCC is assumed.");
			parameters[0] = "LCC";
		}
		
		// 1 : network
		File f = new File(args[1]);
		if (!f.exists()) {
			System.out.println("The specified network " + f + " does not exist.");
			printHelp();
		}
		parameters[1] = args[1];
		
		// 2 : output-file -> no check necessary
		parameters[2] = args[2];
		
		return parameters;
	}
	
	public static ArrayList<String> convertTo(String file_input){
		ArrayList<String> arr = new ArrayList<String>();
		try{
			BufferedReader br = null;
			if (file_input.endsWith(".gz") || file_input.endsWith(".gzip"))
				br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file_input))));
			else
				br = new BufferedReader(new FileReader(file_input));
			
			String strRead = null;
			while((strRead = br.readLine())!= null){
				arr.add(strRead);
			}
			br.close();
		}catch(IOException e){
			System.out.println("Error while loading " + file_input + ".");
			System.exit(1);
		}
		return arr;
	}
	
	public static void writeOutput(MCDSResult mcds_result, String path) {
		List<String> output = new LinkedList<String>();
		
		// build output data
		output.add("Gene,MCDS_role,out_degree,in_degree");
		Map<String, String> mastergene_roles = mcds_result.getMastergeneRoles();
		
		for (String gene:mastergene_roles.keySet())
			output.add(gene + "," + mastergene_roles.get(gene) + "," + mcds_result.getNameToGene().get(gene).get_degree_succ() + "," + mcds_result.getNameToGene().get(gene).get_degree_pred());
		
		// actual writing
		try {
			
			BufferedWriter bw = null;
			if (path.endsWith(".gz") || path.endsWith(".gzip"))
				bw = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(path))));
			else
				bw = new BufferedWriter(new FileWriter(path));
			
			for (String entry:output) {
				bw.write(entry);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.err.println("Error while writing " + path + ".");
		}
	}
	
	/**
	 * Approximates MCDS of [arr] based on [input].
	 * @param input : "LCC":largest connected component (undirected), "LCCD":largest connected component (directed), "SCC":strongly connected component
	 * @param arr : ArrayList of (source, target) node pairs
	 * @return
	 */
	public static MCDSResult mcds(String input, ArrayList<String> arr){
			ArrayList<Gene> mcds = null;
			ArrayList<Gene> nw = null;
			Component c = new Component();
			ArrayList<Gene> largest_ConnectedComponent = null;
			ArrayList<String> geneList = Network.findDistinctGene(arr);

			if(input.equals("LCCD")){
				nw = Network.construct_network_CC("LCCD",geneList,arr);
				largest_ConnectedComponent = c.FindConnectedComponents(nw,geneList,arr,"LCCD");
			}
			else if(input.equals("LCC") || input.equals("SCC")){
				nw = Network.construct_network_CC("LCC",geneList,arr);
				if(input.equals("LCC")){
					largest_ConnectedComponent = c.FindConnectedComponents(nw,geneList,arr,"LCC");
				}
				else{
					largest_ConnectedComponent = c.Find_SCC(nw,geneList,arr);
				}
			}
				ArrayList<Gene> ds = ComputeMCDS.dominating_set(largest_ConnectedComponent);
				ArrayList<Gene> black_darkGray = ComputeMCDS.findConnectors(largest_ConnectedComponent,ds);
				
				mcds = ComputeMCDS.mimicDS(largest_ConnectedComponent,black_darkGray);
				
				Map<String, String> mastergene_colors = new HashMap<String, String>();
				for (Gene gene:mcds) 
					mastergene_colors.put(gene.get_name(), gene.get_color());
				
				//Network.display_mcds_cc(mcds);//shows the degree in the network	
				
				return new MCDSResult(mastergene_colors, nw);
				
	}
	
	//****************************************
	public static void main(String[] args) {
		// read and check arguments
		String[] parameters = parseInput(args);
		// read input-file
		ArrayList<String> arr = convertTo(parameters[1]);
		// compute mcds
		MCDSResult mcds_result = mcds(parameters[0], arr);
		// write to output-file
		writeOutput(mcds_result, parameters[2]);
	}

}
