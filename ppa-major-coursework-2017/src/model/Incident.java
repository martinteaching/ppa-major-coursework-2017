package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import utils.GUIUtils;

public class Incident implements Comparable<Incident> {

	private LocalDateTime dateAndTime;

	private LocalDate posted;

	private String incidentID, city, state, shape, summary;

	int duration;

	public String getIndicentID() {

		return incidentID;

	}

	public LocalDateTime getDateAndTime() {

		return dateAndTime;

	}

	public String getCity() {

		return city;

	}

	public String getState() {

		return state;

	}

	public String getShape() {

		return shape;

	}

	public int getDuration() {

		return duration;

	}

	public String getSummary() {

		return summary;

	}

	public LocalDate getPosted() {

		return posted;

	}

	public Incident(String incidentID, String dateAndTime, String city, String state, String shape, String duration, String summary, String posted) {

		this.incidentID = incidentID;

		this.dateAndTime = LocalDateTime.parse(dateAndTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		this.posted = LocalDate.parse(posted, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		this.city = city;

		this.state = state;

		this.shape = shape;
		
		try {

			this.duration = 0; //Integer.parseInt(GUIUtils.parseMinutes(duration));

		} catch ( NumberFormatException e ) {

			this.duration = -1;

		}

		this.summary = summary;

	}

	public String toString() {

		return "Time: " + dateAndTime.toString().replace("T", " ") + " City: " + city + " Shape: " + shape + " Duration: "
				+ duration + " Posted: " + posted;

	}

	@Override
	public int compareTo(Incident o) {

		return dateAndTime.compareTo(o.dateAndTime);

	}

}
