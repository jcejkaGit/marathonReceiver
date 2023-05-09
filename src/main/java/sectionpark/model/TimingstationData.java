package sectionpark.model;

import sectionpark.timingstation.CompetitionData;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimingstationData {

	private String timingstationID;
	private String timestamp;

	private double distance;
	private String unitDistance;

	private double altitude;
	private String unitAltitude;
	private CompetitionData compData;

	/**
	 * Constructor
	 */
	public TimingstationData() {
		
		this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		this.unitDistance = "m";
		this.unitAltitude = "hm";
		this.compData = new CompetitionData();
	}
	
	/**
	 * Setter and Getter Methods
	 */
	public CompetitionData getCompetitionData() {

		return this.compData;
	}
	public void setCompetitionData(CompetitionData compData) {
		this.compData = compData;
	}
	
	public String getTimingstationID() {
		return timingstationID;
	}
	
	public void setTimingstationID(String timingstationID) {
		this.timingstationID = timingstationID;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * Methods
	 */
	@Override
	public String toString() {
		String info = String.format("Timing Station Info: ID = %s, timestamp = %s, distance = %f",
			timingstationID, timestamp, distance );
		return info;
	}
}
