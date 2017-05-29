package view.panels;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import view.events.DateRangeChange;
import view.events.InvalidRange;

public class Welcome extends Cycle implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel welcomeLabel;
	
	private String APIversion, dateRangeSelected, grabbingData, dataGrabbed,  interact;
	
	public Welcome(String APIversion) {
		
		super("Welcome");
		
		this.APIversion = APIversion;
		
		setLayout(new BorderLayout());
		
		welcomeLabel = new JLabel("", SwingConstants.CENTER);
		
		add(welcomeLabel, BorderLayout.CENTER);
		
		dateRangeSelected = ""; 
		grabbingData = "";
		dataGrabbed = ""; 
		interact = "";
		
		updateWelcomeText();
		
	}
	
	private void updateWelcomeText() {
		
		welcomeLabel.setText(
				"<html>" + 
					"<body style='text-align: center;'>" + 
						"<div style='width:200px;'>" + 
							"<p>Welcome to the Ripley API " + APIversion + "</p>" + 
						    "<p>Please select from the dates above, in order to begin analysing UFO sighting data.</p>" +
							dateRangeSelected + 
						    grabbingData + 
						    dataGrabbed + 
							interact + 
						"</div>" + 
				    "</body>" + 
				"</html>");
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if ( arg instanceof DateRangeChange ) {
			
			if ( ((DateRangeChange)arg).getEvent() == DateRangeChange.UPDATING ) {
				
				dataGrabbed = ""; 
				interact = "";
				dateRangeSelected = "<br /><p>Date range selected, " + ((DateRangeChange)arg).getDateRange() + ".</p>";
				grabbingData = "<br /><p>Grabbing data...</p>";
				
			} else if ( ((DateRangeChange)arg).getEvent() == DateRangeChange.UPDATED ) {
				
				dataGrabbed =  "<br /> <p>Data grabbed in " + ((DateRangeChange)arg).getGrabTime() + ".</p><br />";
				interact = "<p><b>Please now interact with this data using the buttons to the left and the right.</b></p>";
				
			}
			
			updateWelcomeText();
		
		} else if ( arg instanceof InvalidRange ) {
			
			dateRangeSelected = ""; 
			grabbingData = "";
			dataGrabbed = ""; 
			interact = "";
			
			updateWelcomeText();
			
		}
		
		
	}

}
