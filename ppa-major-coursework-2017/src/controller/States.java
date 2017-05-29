package controller;

import java.util.Observable;

import api.ripley.Ripley;

/**
 * Controllers could be packaged with model.
 * 
 * @author Martin
 *
 */
public class States {
	
	private model.States model;
	
	public States(Ripley ripley, model.States model) {
		
		this.model = model;
		
	}
	
	public void dateRangeChanged(int startYear, int endYear) {
		
		model.updateHeads(startYear, endYear);
		
	}
	
}
