package org.cytoscape.MCDS.MCDS.internal;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

/**
 * MCDS App
 * @author Thorsten Will
 */
public class StartMCDS extends AbstractCyAction {

	private static final long serialVersionUID = 315178259722406186L;
	private MCDSApplication app;
	
	public StartMCDS(MCDSApplication app) {
		super("Run");
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	    CyNetworkView currentNetworkView = app.getCurrentNetworkView();
	    
	    // check if a network is selected
	    if (currentNetworkView == null)
	       return;
	    
		DialogTaskManager taskmanager = app.getActivator().getService(DialogTaskManager.class);
		taskmanager.execute(new TaskIterator(new MCDSTask(app)));

	}

}
