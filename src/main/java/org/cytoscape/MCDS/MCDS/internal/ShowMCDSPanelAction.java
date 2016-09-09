package org.cytoscape.MCDS.MCDS.internal;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;

/**
 * MCDS App
 * @author Thorsten Will
 */
public class ShowMCDSPanelAction extends AbstractCyAction {

	private static final long serialVersionUID = -6829561719711298185L;
	
	private MCDSPanel panel;
	
	public ShowMCDSPanelAction(MCDSPanel panel) {
		super("Start");
		setPreferredMenu("Apps.MCDS");
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.panel != null)
			this.panel.activate();
	}

}
