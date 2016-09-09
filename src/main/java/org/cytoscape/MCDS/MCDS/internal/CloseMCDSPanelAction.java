package org.cytoscape.MCDS.MCDS.internal;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
/**
 * MCDS App
 * @author Thorsten Will
 */
public class CloseMCDSPanelAction extends AbstractCyAction {

	private static final long serialVersionUID = -603427773732936948L;
	
	private MCDSPanel panel;
	
	public CloseMCDSPanelAction(MCDSPanel panel) {
		super("Close");
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.panel != null)
			this.panel.deactivate();
	}

}
