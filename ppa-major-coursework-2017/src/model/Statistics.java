package model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import api.ripley.Ripley;
import utils.GUIUtils;
import view.events.DateRangeChange;

/**
 * @author Martin
 *
 */
public class Statistics extends Observable {
	
	/**
	 * 
	 */
	public static final String PATH_TO_SELECTED_STATISTICS = "library/selected-statistics.txt";
	
	/**
	 * 
	 */
	private ArrayList<api.ripley.Incident> incidents;
	
	/**
	 * 
	 */
	private int startYear;
	
	/**
	 * 
	 */
	private int endYear;
	
	/**
	 * 
	 */
	private Ripley ripley;
	
	/**
	 * @param ripley
	 */
	public Statistics(Ripley ripley) {
		
		this.ripley = ripley;
		
	}
	
	/**
	 * @param startYear
	 * @param endYear
	 */
	private void recallAPI(int startYear, int endYear) {

		if ( endYear - startYear > -1 && endYear - startYear < 10 && ( incidents == null || this.startYear != startYear || this.endYear != endYear ) ) {
			
			this.startYear = startYear;
			this.endYear = endYear;
			
			setChanged();
			notifyObservers(new DateRangeChange(DateRangeChange.UPDATING, startYear + " - " + endYear));
			
			long start = System.currentTimeMillis();
			
			incidents = ripley.getIncidentsInRange(startYear + "-01-01 00:00:00", endYear + "-01-01 00:00:00");
			
			long end = System.currentTimeMillis() - start;
			int seconds = (int) (end / 1000) % 60 ;
			int minutes = (int) ((end / (1000*60)) % 60);
			
			setChanged();
			notifyObservers(new DateRangeChange(DateRangeChange.UPDATED, minutes + " minutes and " + seconds + " seconds"));
			
		}
	
	}
	
	/**
	 * @param startYear
	 * @param endYear
	 * @return
	 */
	public ArrayList<State> incidentsPerState(int startYear, int endYear) {
		
		ArrayList<model.State> states = new ArrayList<model.State>();
		
		recallAPI(startYear, endYear);
	
		for ( api.ripley.Incident incident : incidents ) {
			
			if ( !states.contains(new model.State(incident.getState())) ) {
				
				states.add(new model.State(incident.getState()));
				
			} 
			
			states.get(states.indexOf(new model.State(incident.getState()))).addIncident(new model.Incident(incident.getIncidentID(), incident.getDateAndTime(), incident.getCity(), incident.getState(), incident.getShape(), incident.getDuration(), incident.getSummary(), incident.getPosted()));
			
		}
		
		if ( states.size() > 0 ) {
			
			Collections.sort(states);
			return states;
			
		} else {
			
			return null;
			
		}
		
	}

	/**
	 * @param startYear
	 * @param endYear
	 */
	public void updateStatistics(int startYear, int endYear) {
		
		recallAPI(startYear, endYear);
		
		ArrayList<model.StatisticNameAndValue> statisticsNameAndValue = new ArrayList<model.StatisticNameAndValue>();
		
		statisticsNameAndValue.add(new model.StatisticNameAndValue("Hoaxes:", getHoaxes() + "", false));
		statisticsNameAndValue.add(new model.StatisticNameAndValue("Likeliest State:", getLikeliestState(startYear, endYear), false));
		statisticsNameAndValue.add(new model.StatisticNameAndValue("Non-US Sightings:", getNonUSSightings() + "", false));
		statisticsNameAndValue.add(new model.StatisticNameAndValue("Sightings via other platforms", sightingsOnOtherPlatforms() + "", false));
		statisticsNameAndValue.add(new model.StatisticNameAndValue("Additional Statistic 1:", additionalStatistic1() + "", false));
		statisticsNameAndValue.add(new model.StatisticNameAndValue("Additional Statistic 2", additionalStatistic2() + "", false));
		statisticsNameAndValue.add(new model.StatisticNameAndValue("Additional Statistic 3:", additionalStatistic3() + "", false));
		statisticsNameAndValue.add(new model.StatisticNameAndValue("Additional Statistic 4:", additionalStatistic4() + "", false));
		
		if ( new File(PATH_TO_SELECTED_STATISTICS).exists() && !new File(PATH_TO_SELECTED_STATISTICS).isDirectory() ) { 
		  
			try {
			
				List<String> selectedStatistics = Files.newBufferedReader( Paths.get(PATH_TO_SELECTED_STATISTICS) ).lines().collect( Collectors.toList() );
				
				for ( model.StatisticNameAndValue statisticNameAndValue : statisticsNameAndValue ) {
					
					if ( selectedStatistics.contains(statisticNameAndValue.getTitle()) ) {
						
						statisticNameAndValue.setSelectedByUser();
						
					}
		
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			
			}
			
		}
		
		Collections.sort(statisticsNameAndValue);
		
		setChanged();
		notifyObservers(statisticsNameAndValue);
	
	}
	
	/**
	 * @param activeStatistics
	 */
	public void newSelectedStatistics(ArrayList<String> activeStatistics) {
		
		try {
			
			Files.write(Paths.get(PATH_TO_SELECTED_STATISTICS), activeStatistics);
		
		} catch (IOException e) {

			e.printStackTrace();
		
		}
		
	}

	/**
	 * @return
	 */
	public String getLikeliestState(int startYear, int endYear) {
		
		recallAPI(startYear, endYear);
		
		ArrayList<model.State> states = incidentsPerState(startYear, endYear);
		
		String likeliestState = "-";
		
		if ( states != null ) {
			
			int highestSightings = states.get(0).getSightings();
			likeliestState = states.get(0).getStateCode();
			
			for ( int i = 1; i < states.size(); i++ ) {
				
				if ( states.get(i).getSightings() > highestSightings ) {
					
					highestSightings = states.get(i).getSightings();
					likeliestState = states.get(i).getStateCode();
				
				}
				
			}
		
		}
		
		return likeliestState;
		
	}
	
	/**
	 * @return
	 */
	public int getHoaxes() {
		
		int hoaxes = 0;
		
		for ( api.ripley.Incident incident : incidents ) {
			
			String incidentDetails = ripley.getIncidentDetails(incident.getIncidentID());
			
			if ( incidentDetails.contains("Hoax") ) {
				
				System.out.println(incidentDetails);
				
				hoaxes++;
				
			}
			
		}
		
		return hoaxes;
		
	}
	
	/**
	 * @return
	 */
	public int getNonUSSightings() {
		
		int nonUS = 0;
		
		for ( api.ripley.Incident incident : incidents ) {
			
			if ( !States.positionMap().containsKey(incident.getState()) ) {
				
				nonUS++;
				
			}
			
		}
		
		return nonUS;
		
	}
	
	/**
	 * @return
	 */
	public int sightingsOnOtherPlatforms() {
		
		ArrayList<String> videoInformation;
		
		try {
		
			videoInformation = GUIUtils.urlResponse("https://www.googleapis.com/youtube/v3/search?part=snippet&q=aliensighting&key=AIzaSyAOcruq-vw_VCgt49THA4lXAjlpXxME8PA");
			
			for ( String line : videoInformation ) {
				
				if ( line.contains("totalResults") ) {
					
					return Integer.parseInt(line.replace("\"totalResults\": ", "").replace(",", "").trim()); 
					
				}
				
			}
		
		} catch (IOException e) {
	
			e.printStackTrace();
		
		}
		
		return 0;
		
	}
	
	public int additionalStatistic1() {
		
		// To be completed by student
		return 0;
		
	}
	
	public int additionalStatistic2() {
		
		// To be completed by student
		return 0;
		
	}
	
	public int additionalStatistic3() {
		
		// To be completed by student
		return 0;
		
	}
	
	public int additionalStatistic4() {
		
		// To be completed by student
		return 0;
		
	}

}
