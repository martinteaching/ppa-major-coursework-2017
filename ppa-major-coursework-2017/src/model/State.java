package model;

import java.util.ArrayList;
import model.Incident;

public class State implements Comparable<State> {
	
	private String stateCode;
	
	private ArrayList<Incident> incidentsInState;

	public State(String stateCode) {

		this.stateCode = stateCode;
		this.incidentsInState = new ArrayList<Incident>();
	
	}

	public String getStateCode() {
		
		return stateCode;
	
	}

	public void setStateCode(String stateCode) {
	
		this.stateCode = stateCode;
	
	}

	public int getSightings() {
	
		return incidentsInState.size();
	
	}
	
	public ArrayList<Incident> getIncidentsInState() {
	
		return incidentsInState;
	
	}

	public void addIncident(Incident incident) {
	
		incidentsInState.add(incident);
	
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		return true;
	}

	public String toString() {
		
		return stateCode + " " + incidentsInState.size();
		
	}

	@Override
	public int compareTo(State o) {
		
		return o.getSightings() - getSightings();
		
	}
	
}