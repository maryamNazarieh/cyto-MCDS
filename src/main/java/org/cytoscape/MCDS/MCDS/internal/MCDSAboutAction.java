package org.cytoscape.MCDS.MCDS.internal;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
/**
 * MCDS App
 * @author Thorsten Will
 */
public class MCDSAboutAction extends AbstractCyAction {

	private static final long serialVersionUID = -6599422667116060803L;
	private MCDSApplication app;
	
	public MCDSAboutAction(MCDSApplication app) {
		super("About");
		setPreferredMenu("Apps.MCDS");
		this.app = app;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MCDSAboutDialog d = new MCDSAboutDialog(app);
		d.setVisible(true);
	}

}
