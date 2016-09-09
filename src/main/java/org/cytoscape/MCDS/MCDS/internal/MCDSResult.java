package org.cytoscape.MCDS.MCDS.internal;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * MCDS App
 * @author Thorsten Will
 */
public class MCDSResult {
	
	final private Map<String, String> mastergene_colors;
	final private Map<String, Gene> name_to_gene = new HashMap<String, Gene>();
	
	public MCDSResult(Map<String, String> mastergene_colors, List<Gene> all_genes) {
		this.mastergene_colors = mastergene_colors;
		
	    // build a map to finally relate CyNode-objects and MCDS Gene objects with each other
	    for (Gene gene:all_genes) {
	    	name_to_gene.put(gene.get_name(), gene);
	    }
	}

	public Map<String, String> getMastergeneColors() {
		return mastergene_colors;
	}

	/**
	 * Returns map that refers to dominators/connectors instead of the color
	 * @return
	 */
	public Map<String, String> getMastergeneRoles() {
		Map<String, String> roles = new HashMap<String, String>();
		
		for (String s:mastergene_colors.keySet()) {
			String color = mastergene_colors.get(s);
			if (color.equals("black"))
        		roles.put(s, "dominator");
        	else
        		roles.put(s, "connector");
		}
		
		return roles;
	}

	public Map<String, Gene> getNameToGene() {
		return name_to_gene;
	}
}
