package org.cytoscape.MCDS.MCDS.internal;

import java.util.ArrayList;

import java.util.Map;

import javax.swing.BorderFactory;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

/**
 * MCDS App
 * @author Thorsten Will
 */
public class MCDSTask implements Task {

	private MCDSApplication app;
	
	public MCDSTask(MCDSApplication app) {
		this.app = app;
	}
	
	@Override
	public void cancel() {
		
	}

	@Override
	public void run(TaskMonitor taskmonitor) throws Exception {
		
		taskmonitor.setTitle("Calculating " + app.getAppName() + " ... ");
		
		CyNetworkView currentNetworkView = app.getCurrentNetworkView();
		CyNetwork network = currentNetworkView.getModel();
	    CyTable nodetable = network.getDefaultNodeTable();
	    
	    // get parameter
	    String option = app.getPanel().getAlgoOption();
	    String cmd = "LCC";
	    if (option.equals("largest connected component (directed)"))
	    	cmd = "LCCD";
	    else if (option.equals("strongly connected component")) {
			cmd = "SCC";
		}
	    
	    // build MCDS input
	    ArrayList<String> mcds_input = new ArrayList<String>(network.getEdgeList().size());
	    for (CyEdge edge:network.getEdgeList()) {
	    	String node1 = network.getRow(edge.getSource()).get(CyNetwork.NAME, String.class);
	    	String node2 = network.getRow(edge.getTarget()).get(CyNetwork.NAME, String.class);
	    	mcds_input.add(node1 + "," + node2);
	    }
	    
	    // compute MCDS
	    MCDSResult mcds_result = MCDS.mcds(cmd, mcds_input);
	    
	    // prepare output
	    String result_column = "MCDS role";
	    if (nodetable.getColumn(result_column) == null)
	    	nodetable.createColumn(result_column, String.class, false);
	    
	    String succ_column = "successor nodes";
	    if (nodetable.getColumn(succ_column) == null)
	    	nodetable.createColumn(succ_column, Integer.class, false);
	    
	    String pred_column = "predecessor nodes";
	    if (nodetable.getColumn(pred_column) == null)
	    	nodetable.createColumn(pred_column, Integer.class, false);
	    
	    /*
	     * write outputs
	     */
	    
	    // write list of relevant nodes according to MCDS into panel 
	    app.getPanel().getResultOutputTextArea().setText("");
	    app.getPanel().getOutputScrollPane().setBorder(BorderFactory.createTitledBorder("MCDS result (" + mcds_result.getMastergeneColors().keySet().size() + " nodes)"));
	    
	    // for java 7 compatibility: no String.join there ...
	    StringBuilder sb = new StringBuilder();
	    boolean first = true;
	    for (String s:mcds_result.getMastergeneColors().keySet()) {
	    	if (!first)
	    		sb.append(System.lineSeparator());
	    	else
	    		first = false;
	    	
	    	sb.append(s);
	    }
	    app.getPanel().getResultOutputTextArea().setText(sb.toString());
	    
	    // build a map to finally relate CyNode-objects and MCDS Gene objects with each other
	    Map<String, Gene> name_obj_map = mcds_result.getNameToGene();
	    
	    /*
	     * write info for all nodes
	     */
	    
	    // unselect all selected nodes
	    for (CyNode previously_selected_node:CyTableUtil.getNodesInState(network, "selected", true))
	    	network.getRow(previously_selected_node).set(CyNetwork.SELECTED, false);
	    
	    Map<String, String> mastergene_roles = mcds_result.getMastergeneRoles();
	    
	    for (CyNode cy_node:network.getNodeList()) {
	        String node_name = network.getRow(cy_node).get(CyNetwork.NAME, String.class);
	        
	        // succ. info
	        Gene gene = name_obj_map.get(node_name);
	        int succ_property = gene.get_degree_succ();
	        int pred_property = gene.get_degree_pred();
	        network.getRow(cy_node).set(succ_column, succ_property);
	        network.getRow(cy_node).set(pred_column, pred_property);
	        
	        // MCDS algorithm results
	        String mcds_property = "none";
	        if (mastergene_roles.containsKey(node_name)) {
	        	mcds_property = mastergene_roles.get(node_name);
	        	
	        	// select all colored nodes
	        	network.getRow(cy_node).set(CyNetwork.SELECTED, true);
	        }
	        network.getRow(cy_node).set(result_column, mcds_property);
	    }
	    
	    currentNetworkView.updateView();
	}
}
