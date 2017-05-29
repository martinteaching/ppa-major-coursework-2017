package controller;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import api.ripley.Incident;
import api.ripley.Ripley;
import utils.GUIUtils;
import view.panels.Cycle;

public class Container {
	
	private view.frames.Container container;
	
	private view.panels.States view_States;
	private controller.States controller_States;
	private model.States model_States;
	
	private view.frames.Information view_Information;
	private controller.Information controller_Information;
	
	private view.panels.Statistics view_Statistics;
	private controller.Statistics controller_Statistics;
	private model.Statistics model_Statistics;
	
	private view.panels.Welcome view_Welcome;
	
	public Container(Ripley ripley) {
		
		GUIUtils.setUIFont(new javax.swing.plaf.FontUIResource("Monospace",Font.PLAIN,11));
		
		System.out.println(ripley.getAcknowledgementString());
		
		model_Statistics = new model.Statistics(ripley);
		controller_Statistics = new controller.Statistics(ripley, model_Statistics);
		view_Statistics = new view.panels.Statistics(controller_Statistics);
		model_Statistics.addObserver(view_Statistics);
		
		view_Welcome = new view.panels.Welcome("v" + ripley.getVersion());
		model_Statistics.addObserver(view_Welcome);
		
		model_States = new model.States(ripley, model_Statistics);
		controller_States = new controller.States(ripley, model_States);
		
		controller_Information = new controller.Information(ripley, model_States);
		
		view_States = new view.panels.States(controller_States, controller_Information);
		model_States.addObserver(view_States);
		model_States.addObserver(view_Welcome);
		
		container = new view.frames.Container(new ArrayList<Cycle>(Arrays.asList(new Cycle[] {  view_Welcome, view_States, view_Statistics })), ripley.getStartYear(), ripley.getLatestYear(), ripley.getLastUpdated(), controller_States, controller_Statistics);
		container.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		
		Ripley ripley = new Ripley("", "" );
			
		new Container(ripley);
		
	}

}