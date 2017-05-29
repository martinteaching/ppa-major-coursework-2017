package controller;

import javax.swing.JList;

import api.ripley.Ripley;

public class APITester {

	public static void main(String[] args) {
		
		Ripley ripley = new Ripley("", "" );
		
		System.out.println(ripley.getLastUpdated());
		
	}

}