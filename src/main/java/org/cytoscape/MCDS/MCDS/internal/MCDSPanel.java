package org.cytoscape.MCDS.MCDS.internal;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
/**
 * MCDS App
 * @author Thorsten Will
 */
public class MCDSPanel extends JPanel implements CytoPanelComponent {
	
	private static final long serialVersionUID = 9214703280442690209L;
	
	private MCDSApplication app;
	private JComboBox<String> algo_option;
	private JTextArea result_output;
	private JScrollPane scrollPane_output;
	
	public MCDSPanel(MCDSApplication app) {
		super();
		this.app = app;
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel optionPanel = new JPanel();
		add(optionPanel);
		
		JLabel lblOptimizationCriterion = new JLabel("Optimization criterion:");
		optionPanel.add(lblOptimizationCriterion);
		
		algo_option = new JComboBox<String>(new String[]{"largest connected component (undirected)", "largest connected component (directed)", "strongly connected component"});
		optionPanel.add(algo_option);
		
		JPanel btnPanel = new JPanel();
		optionPanel.add(btnPanel);
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		
		JButton btnRunMcds = new JButton("Run MCDS");
		btnRunMcds.addActionListener(new StartMCDS(app));
		btnPanel.add(btnRunMcds);
		
		JButton btnClose = new JButton("Close panel");
		btnClose.addActionListener(new CloseMCDSPanelAction(this));
		btnPanel.add(btnClose);
		
		scrollPane_output = new JScrollPane();
		scrollPane_output.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane_output);
		
		result_output = new JTextArea();
		result_output.setRows(HEIGHT);
		scrollPane_output.setBorder(BorderFactory.createTitledBorder("MCDS result"));
		scrollPane_output.setViewportView(result_output);
		result_output.setEditable(false);
	}
	
	public void activate() {
		// register
		this.app.getActivator().registerService(this, CytoPanelComponent.class);
		
		// focus on this panel
		CytoPanel cyto_panel = app.getCySwingApplication().getCytoPanel(getCytoPanelName());
		if (cyto_panel.getState() == CytoPanelState.HIDE) {
			cyto_panel.setState(CytoPanelState.DOCK);
		}
		
		setVisible(true);
		cyto_panel.setSelectedIndex(cyto_panel.indexOfComponent(getComponent()));
	}
	
	public void deactivate() {
		this.app.getActivator().unregisterAllServices(this);
		result_output.setText("");
		scrollPane_output.setBorder(BorderFactory.createTitledBorder("MCDS result"));
	}
	
	public String getAlgoOption() {
		return String.valueOf(algo_option.getSelectedItem());
	}
	
	public JTextArea getResultOutputTextArea() {
		return this.result_output;
	}
	
	public JScrollPane getOutputScrollPane() {
		return this.scrollPane_output;
	}
	
	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.WEST;
	}

	@Override
	public Icon getIcon() {
		return null;
	}

	@Override
	public String getTitle() {
		return this.app.getAppName();
	}

}
