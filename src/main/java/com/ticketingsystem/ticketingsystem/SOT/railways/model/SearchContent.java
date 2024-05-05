package com.ticketingsystem.ticketingsystem.SOT.railways.model;

import java.util.List;



public class SearchContent {
	
	private String departureStation;
	private String destinationStation;
	private String departureDate;
	private List<Station>allStations;
	
	public SearchContent() {
		super();
	}

	public SearchContent(String departureStation, String destinationStation, String departureDate,
			List<Station> allStations) {
		super();
		this.departureStation = departureStation;
		this.destinationStation = destinationStation;
		this.departureDate = departureDate;
		this.allStations = allStations;
	}

	public String getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public String getDestinationStation() {
		return destinationStation;
	}

	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public List<Station> getAllStations() {
		return allStations;
	}

	public void setAllStations(List<Station> allStations) {
		this.allStations = allStations;
	}
	
	
	
	

}
