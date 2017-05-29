package controller;

import java.util.ArrayList;

import api.ripley.Ripley;

public class Statistics {
	
	private model.Statistics model;
	
	public Statistics(Ripley ripley, model.Statistics model) {
		
		this.model = model;
		
	}
	
	public void dateRangeChanged(int startYear, int endYear) {
		
		model.updateStatistics(startYear, endYear);
		
	}
	
	public void newSelectedStatistics(ArrayList<String> activeStatistics) {
		
		model.newSelectedStatistics(activeStatistics);
		
	}
	
}