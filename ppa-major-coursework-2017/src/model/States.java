package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;

import api.ripley.Ripley;
import view.events.AddAlienHead;
import view.events.DateRangeChange;
import view.events.InvalidRange;

public class States extends Observable {

	private Map<String, StatePoint> statePositions;
	
	private ArrayList<State> stateIncidents;
	
	private model.Statistics model_Statistics;
	
	private Ripley ripley;
	
	public States(Ripley ripley, model.Statistics model_Statistics) {
		
		this.ripley = ripley;
		
		this.model_Statistics = model_Statistics;
		
		statePositions = States.positionMap();
		
	}
	
	public static HashMap<String, StatePoint> positionMap() {
		
		HashMap<String, StatePoint> statePositions = new HashMap<String, StatePoint>();
		
		try ( BufferedReader reader = Files.newBufferedReader( Paths.get("library/states.txt") ) ) {
			
			for ( String position : reader.lines().collect( Collectors.toList() )) {
				
				statePositions.put(position.split(" ")[0], new StatePoint(Integer.parseInt(position.split(" ")[1]), Integer.parseInt(position.split(" ")[2])));
				
			}
		
		} catch (IOException e) {
			
			e.printStackTrace();
		
		}
		
		return statePositions;
		
	}
	
	public boolean updateHeads(int startYear, int endYear) {
		
		stateIncidents = model_Statistics.incidentsPerState(startYear, endYear);
		
		setChanged();
		
		notifyObservers(new DateRangeChange());
		
		if ( stateIncidents != null ) {
			
			// Smallest size of head is 1 / N where N = maximum sightings in a state
			int smallestSize = view.panels.States.MAX_HEAD_SIZE / stateIncidents.get(0).getSightings();
			
			for ( State state : stateIncidents ) {
				
				if ( statePositions.containsKey(state.getStateCode()) ) {
	
					// Offset X and Y position by size of head
					int X_OFFSET = 0;
					int Y_OFFSET = 0;
					
					setChanged();
					
					int size = smallestSize * state.getSightings() >= view.panels.States.MIN_HEAD_SIZE ? smallestSize * state.getSightings() : view.panels.States.MIN_HEAD_SIZE;
					
					// Includes size for head.
					notifyObservers(new AddAlienHead(statePositions.get(state.getStateCode()).getX() + X_OFFSET, statePositions.get(state.getStateCode()).getY() + Y_OFFSET, size, state.getStateCode()));
					
				}
			
			}
			
			return true;
	
		} else {
			
			setChanged();
			
			notifyObservers(new InvalidRange());
			
			return false;
			
		}
		
	}
	
	public ArrayList<Incident> getIncidentsByCode(String stateCode) {
		
		for ( State state : stateIncidents ) {
			
			if ( state.getStateCode().equals(stateCode) ) {
				
				return state.getIncidentsInState();
				
			}
			
		}
		
		return null;
		
	}
}
