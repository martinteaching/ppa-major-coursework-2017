package view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Statistic extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private ArrayList<StatisticNameAndValue> availableStatistics;
	
	private ArrayList<StatisticNameAndValue> activeStatistics;
	
	private StatisticNameAndValue currentPanel;
	
	private controller.Statistics controller_Statistics;
	
	public Statistic(ArrayList<StatisticNameAndValue> availableStatistics, ArrayList<StatisticNameAndValue> activeStatistics, StatisticNameAndValue startPanel, controller.Statistics controller_Statistics) {
		
		setLayout(new BorderLayout());
		
		this.controller_Statistics = controller_Statistics;
		
		this.availableStatistics = availableStatistics;
		
		this.activeStatistics = activeStatistics;
		
		activeStatistics.add(startPanel);
		
		currentPanel = startPanel;
		
		setupWest();
		
		setupEast();
		
		add(currentPanel, BorderLayout.CENTER);
		
	}
	
	public void setupWest() {
		
		JButton moveLeft = new JButton(">");
		
		moveLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int panelPosition = availableStatistics.indexOf(currentPanel);
				
				while ( panelPosition < availableStatistics.size() && activeStatistics.contains(availableStatistics.get(panelPosition)) ) panelPosition++;
				
				if ( panelPosition < availableStatistics.size() ) changePanel(availableStatistics.get(panelPosition));
				
			}
			
		});
		
		add(moveLeft, BorderLayout.EAST);
		
	}
	
	public void setupEast() {
		
		JButton moveRight = new JButton("<");
		
		moveRight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int panelPosition = availableStatistics.indexOf(currentPanel);
				
				while ( panelPosition > -1 && activeStatistics.contains(availableStatistics.get(panelPosition)) ) panelPosition--;
				
				if ( panelPosition > -1 ) changePanel(availableStatistics.get(panelPosition));
				
			}
			
		});
		
		add(moveRight, BorderLayout.WEST);
		
	}
	
	private void changePanel(StatisticNameAndValue newPanel) {
		
		remove(currentPanel);
		
		activeStatistics.remove(currentPanel);
		
		// Repaint to remove (not strictly needed).
		repaint();
		
		add(newPanel, BorderLayout.CENTER);
		
		activeStatistics.add(newPanel);
		
		// Revalidate to add.
		revalidate();
		
		currentPanel = newPanel;
		
		ArrayList<String> activeStatistics = new ArrayList<String>();
		
		for ( StatisticNameAndValue statisticNameAndValue : this.activeStatistics ) {
			
			activeStatistics.add(statisticNameAndValue.getTitle());
			
		}
		controller_Statistics.newSelectedStatistics(activeStatistics);
		
	}
	
}
