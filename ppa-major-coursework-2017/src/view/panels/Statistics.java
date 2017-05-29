package view.panels;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import utils.GUIUtils;

public class Statistics extends Cycle implements Observer {

	private static final long serialVersionUID = 1L;
	
	private controller.Statistics controller_Statistics;
	
	public Statistics(controller.Statistics controller_Statistics) {
		
		super("Statistic");
		
		setLayout(new GridLayout(2, 2));
		
		this.controller_Statistics = controller_Statistics;
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if ( arg instanceof ArrayList ) {
			
			removeAll();
			
			ArrayList<StatisticNameAndValue> availableStatistics = new ArrayList<StatisticNameAndValue>(), activeStatistics = new ArrayList<StatisticNameAndValue>();
			
			for ( model.StatisticNameAndValue statistic : (ArrayList<model.StatisticNameAndValue>)arg ) {
				
				StatisticNameAndValue individualStatistic = new StatisticNameAndValue(statistic.getTitle(), statistic.getValue());
				
				availableStatistics.add(individualStatistic);
				
			}
			
			addStatistics(availableStatistics, activeStatistics);
		
		}
		
	}
	
	public void addStatistics(ArrayList<StatisticNameAndValue> availableStatistics, ArrayList<StatisticNameAndValue> activeStatistics) {
		
		for ( int i = 0; i < 4; i++ ) {
			
			Statistic statistic = new Statistic(availableStatistics, activeStatistics, availableStatistics.get(i), controller_Statistics);
			
			GUIUtils.setBorder(statistic);
			
			add(statistic);
			
			revalidate();
			
			repaint();
		        
		}
		
	}

}