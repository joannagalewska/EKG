package pi.data.importer.signal;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import pi.shared.SharedController;

public class ImportView extends JDialog{

	GridBagConstraints constraints;
	private JButton okButton;
	private JButton cancelButton;
	private JButton chooseButton;
	private JPanel buttonPanel;	
	private JLabel label;
	private JTextArea pathArea;
	private JPanel importPanel;
	private JLabel fileLabel;
	private ImportController controller;
	private final JFileChooser fileChooser = new JFileChooser();
	
	
	public ImportView(){
		
		okButton = new JButton("OK");
		cancelButton = new JButton("CANCEL");
		chooseButton = new JButton("CHOOSE");
		buttonPanel = new JPanel();
		importPanel = new JPanel();
		setPathArea(new JTextArea());
		importPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	
		//importPanel configuration
		fileLabel = new JLabel("Specimen");
		fileLabel.setVisible(true);
		importPanel.add(fileLabel);
		getPathArea().setEditable(true);
		getPathArea().setPreferredSize(new Dimension(150,20));
		getPathArea().setEditable(true);
		importPanel.add(getPathArea());
		importPanel.add(chooseButton);
		importPanel.setVisible(true);
		importPanel.setSize(300,500);
		
		//buttonPanel configuration
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);
		
		//this panel configuration
		this.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.add(importPanel, constraints);
	
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.add(buttonPanel, constraints);
	
		this.controller = new ImportController(this);
		this.setTitle("Create Specimen: Single");
		this.setVisible(true);
		this.setLocation(100, 100);
		this.setSize(500,150);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//configuration of ActionListeners
		chooseButton.setActionCommand("CHOOSE");
		chooseButton.addActionListener(this.controller);
		
		okButton.setActionCommand("OK");
		okButton.addActionListener(this.controller);
		
		cancelButton.setActionCommand("CANCEL");
		cancelButton.addActionListener(this.controller);
		
		//FileChooser configuration
    	fileChooser.setCurrentDirectory(SharedController.getInstance().getLastDirectory());
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("XML (*.xml)","xml");
    	fileChooser.addChoosableFileFilter(filter);
    	fileChooser.setFileFilter(filter);
    	
		
	}


	public JFileChooser getFileChooser() {
		return fileChooser;
	}


	public JTextArea getPathArea() {
		return pathArea;
	}


	public void setPathArea(JTextArea text) {
		this.pathArea = text;
	}
	
	public ImportView getContext(){
		return this;
	}
	
	
	
	
}
