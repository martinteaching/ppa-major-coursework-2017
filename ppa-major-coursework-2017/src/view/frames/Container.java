package view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.GUIUtils;
import view.panels.Cycle;

public class Container extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private List<Cycle> panels;
	
	private Cycle currentPanel;
	
	private controller.States controller_States;
	
	private controller.Statistics controller_Statistics;
	
	private JButton left, right;
	
	public Container(ArrayList<Cycle> panels, int startYear, int endYear, String lastUpdated, controller.States controller_States, controller.Statistics controller_Statistics) {
		
		this.controller_States = controller_States;
		
		this.controller_Statistics = controller_Statistics;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setMinimumSize(new Dimension(1024, 676));
		
		//setMaximumSize(new Dimension(1024, 676));
		
		this.panels = panels;
		
		setupOverallLayout();
		
		northLayout(startYear, endYear);
		
		southLayout(lastUpdated);
		
		pack();
		
	}
	
	private void setupOverallLayout() {
		
		setLayout(new BorderLayout());
		
		add(panels.get(0), BorderLayout.CENTER);
		
		currentPanel = panels.get(0);
		
		GUIUtils.setBorder( currentPanel,  BorderFactory.createLineBorder( Color.BLACK ) );
		
	}
	
	private void northLayout(int startYear, int endYear) {
		
		JPanel yearsPanel = new JPanel();
		
		add(yearsPanel, BorderLayout.NORTH);
		
		yearsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JComboBox<String> lowerYear = new JComboBox<String>();
		DefaultComboBoxModel<String> model_lowerYear = new DefaultComboBoxModel<String>();
		lowerYear.setModel(model_lowerYear);
		
		
		JComboBox<String> upperYear = new JComboBox<String>();
		DefaultComboBoxModel<String> model_upperYear = new DefaultComboBoxModel<String>();
		upperYear.setModel(model_upperYear);
		
		model_lowerYear.addElement("-");
		model_upperYear.addElement("-");
		
		for ( int start = startYear; start <= endYear + 1; start++ ) {
			
			model_lowerYear.addElement(start + "");
			
			model_upperYear.addElement(start + "");
			
		}
		
		lowerYear.setSelectedItem("-");
		upperYear.setSelectedItem("-");
		
		ItemListener changeYear = new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if ( e.getStateChange() == ItemEvent.SELECTED && !lowerYear.getSelectedItem().equals("-") && !upperYear.getSelectedItem().equals("-") ) {
					
					Thread updateDateRange = new Thread() {
						
						public void run() {
							
							dateRangeChanged(Integer.parseInt((String)lowerYear.getSelectedItem()), Integer.parseInt((String)upperYear.getSelectedItem()));
							
						}
					
					
					};
					
					updateDateRange.start();
				
				}
				
			}
			
		};
		
		lowerYear.addItemListener(changeYear);
	
		upperYear.addItemListener(changeYear);
		
		yearsPanel.add(new JLabel("From:"));
		
		yearsPanel.add(lowerYear);
		
		yearsPanel.add(new JLabel("To:"));
		
		yearsPanel.add(upperYear);
		
	}
	
	private void dateRangeChanged(int lowerYear, int upperYear) {
		
		controller_States.dateRangeChanged(lowerYear, upperYear);
		
		controller_Statistics.dateRangeChanged(lowerYear, upperYear);
		
		left.setEnabled(true);
		
		right.setEnabled(true);
		
	}
	
	private void southLayout(String lastUpdated) {
		
		JPanel navigationPanel = new JPanel();
		
		add(navigationPanel, BorderLayout.SOUTH);
		
		navigationPanel.setLayout(new BorderLayout());
		
		leftNavigation(navigationPanel);
		
		rightNavigation(navigationPanel);
		
		navigationPanel.add(new JLabel(lastUpdated, SwingConstants.CENTER), BorderLayout.CENTER);
		
	}
	
	private void leftNavigation(JPanel navigationPanel) {
		
		left = new JButton("<");
		
		navigationPanel.add(left, BorderLayout.WEST);
		
		left.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int panelPosition = panels.indexOf(currentPanel);
				
				if ( panelPosition > 0 ) {
					
					changePanel(panels.get(panelPosition - 1));
				
				}
				
			}
			
		});
		
		left.setEnabled(false);
		
	}
	
	private void rightNavigation(JPanel navigationPanel) {
		
		right = new JButton(">");
		
		navigationPanel.add(right, BorderLayout.EAST);
		
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int panelPosition = panels.indexOf(currentPanel);
				
				if ( panelPosition < panels.size() - 1 ) {
					
					changePanel(panels.get(panelPosition + 1));
					
				}
				
			}
			
		});
		
		right.setEnabled(false);
		
	}
	
	private void changePanel(Cycle newPanel) {
		
		remove(currentPanel);
		
		// Repaint to remove (not strictly needed).
		repaint();
		
		add(newPanel, BorderLayout.CENTER);
		
		// Revalidate to add.
		revalidate();
		
		currentPanel = newPanel;
		
		GUIUtils.setBorder( currentPanel,  BorderFactory.createLineBorder( Color.BLACK ) );
		
	}
	
	public Cycle getCurrentPanel() {
		
		return currentPanel;
		
	}
	
}
