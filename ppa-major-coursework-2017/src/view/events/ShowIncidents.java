package view.events;

import java.util.ArrayList;

import model.Incident;

/**
 * Auto-gen.
 * 
 * @author Martin
 *
 */
public class ShowIncidents {
	
	private String stateName;
	private String stateCode;
	private ArrayList<Incident> incidents;
	
	public ShowIncidents(String stateName, String stateCode, ArrayList<Incident> incidents) {
		this.stateName = stateName;
		this.stateCode = stateCode;
		this.incidents = incidents;
	}
	
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public ArrayList<Incident> getIncidents() {
		return incidents;
	}
	public void setIncidents(ArrayList<Incident> incidents) {
		this.incidents = incidents;
	}


}
