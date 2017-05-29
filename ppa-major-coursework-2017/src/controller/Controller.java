package controller;

import java.util.Observable;

import api.ripley.Ripley;

public abstract class Controller extends Observable {
	
	private Ripley ripley;
	
	public Controller(Ripley ripley) {
		
		this.ripley = ripley;
		
	}
	
	protected Ripley ripley() {
		
		return ripley;
		
	}

}
