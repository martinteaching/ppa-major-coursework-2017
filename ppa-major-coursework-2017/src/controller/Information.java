package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import api.ripley.Ripley;
import model.Incident;
import model.StatePoint;

public class Information extends Controller {
	
	private view.frames.Information view_Information;
	
	private model.States model_State;
	
	private Map<String, String> stateCodeToName;
	
	public Information(Ripley ripley, model.States model_State) {
		
		super(ripley);
		
		this.model_State = model_State;
		
		this.stateCodeToName = stateCodeToName();
	
	}
	
	private HashMap<String, String> stateCodeToName() {
		
		HashMap<String, String> stateCodeToName = new HashMap<String, String>();
		
		try ( BufferedReader reader = Files.newBufferedReader( Paths.get("library/state-names.txt") ) ) {
			
			for ( String position : reader.lines().collect( Collectors.toList() )) {
				
				stateCodeToName.put(position.split(",")[2], position.split(",")[0]);
			}
		
		} catch (IOException e) {
			
			e.printStackTrace();
		
		}
		
		return stateCodeToName;
		
	}
	
	public void alienClicked(String stateCode) {
		
		view_Information = new view.frames.Information(this);
		
		view_Information.setTitle(stateCodeToName.get(stateCode) + " (" + stateCode + ")");
		
		for ( Incident incident : model_State.getIncidentsByCode(stateCode) ) {
			
			view_Information.addIncident(incident);
		
		}
		
		view_Information.setVisible(true);
		
	}
	
	public void showDetails(String incidentID) {
		
		JOptionPane.showMessageDialog(view_Information, "<html><body><p style='width: 200px;'>" + ripley().getIncidentDetails(incidentID) + "</p></body></html>");
			
	}
	
}
