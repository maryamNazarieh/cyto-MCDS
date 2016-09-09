package org.cytoscape.MCDS.MCDS.internal;

import javax.swing.JDialog;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JEditorPane;
/**
 * MCDS App
 * @author Thorsten Will
 */
public class MCDSAboutDialog extends JDialog {
	
	private static final long serialVersionUID = -9165365131267775591L;
	
	public MCDSAboutDialog(MCDSApplication app) {
		super(app.getCySwingApplication().getJFrame(), "About MCDS", false);
		setResizable(false);

        setSize(420, 300);
        getContentPane().setLayout(null);
        
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setEditorKit(new HTMLEditorKit());
        editorPane.setBackground(getContentPane().getBackground());
        editorPane.setBounds(6, 75, 408, 140);
        getContentPane().add(editorPane);
        editorPane.setText("<html><center>MCDS Cytoscape-Plugin Version " + app.getVersion() + "</center><br>"
        		+ "<center>by Maryam Nazarieh, Thorsten Will</center><br><center>and Volkhard Helms</center>"
        		+ "<br>"
        		+ "<br>"
        		+ "<center><a href=\"mailto:maryam.nazarieh@bioinformatik.uni-saarland.de\">Contact: maryam.nazarieh@bioinformatik.uni-saarland.de</a> </center></html>");
	}
}
