package org.cytoscape.MCDS.MCDS.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewManager;
/**
 * MCDS App
 * @author Thorsten Will
 */
public class MCDSApplication {
	private MCDSActivator activator;
	private CySwingApplication application;
	private MCDSPanel panel;
	private String app_name = "MCDS";
	private String version = "1.0";
	
	public MCDSApplication(MCDSActivator activator) {
		this.activator = activator;
		this.application = activator.getService(CySwingApplication.class);
		this.panel = new MCDSPanel(this);
		
		this.application.addAction(new ShowMCDSPanelAction(panel));
		this.application.addAction(new MCDSAboutAction(this));
	}
	
	
	/*
	 * Cytoscape-specific helper functions
	 */
	
	public CyApplicationManager getApplicationManager() {
		return activator.getService(CyApplicationManager.class);
	}
	
	public CyNetwork getCurrentNetwork() {
		return getApplicationManager().getCurrentNetwork();
	}
	
	public CyNetworkView getCurrentNetworkView() {
		return getApplicationManager().getCurrentNetworkView();
	}
	
	public CyNetworkViewManager getNetworkViewManager() {
		return activator.getService(CyNetworkViewManager.class);
	}
	
	/*
	 * Std getters
	 */
	
	public MCDSActivator getActivator() {
		return activator;
	}

	public CySwingApplication getCySwingApplication() {
		return application;
	}

	public MCDSPanel getPanel() {
		return panel;
	}

	public String getAppName() {
		return app_name;
	}
	
	public String getVersion() {
		return version;
	}
}
