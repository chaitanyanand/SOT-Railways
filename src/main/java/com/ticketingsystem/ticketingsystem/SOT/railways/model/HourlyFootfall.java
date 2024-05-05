package com.ticketingsystem.ticketingsystem.SOT.railways.model;

import java.util.List;

public class HourlyFootfall {
	
	private String station;
	private String date;
	private List<String> stationList;
	private List<Double> hourlyFootfallList;
	
    
	public HourlyFootfall() {
		super();
	}


	public HourlyFootfall(String station, String date, List<String> stationList, List<Double> hourlyFootfallList) {
		super();
		this.station = station;
		this.date = date;
		this.stationList = stationList;
		this.hourlyFootfallList = hourlyFootfallList;
	}


	public String getStation() {
		return station;
	}


	public void setStation(String station) {
		this.station = station;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public List<String> getStationList() {
		return stationList;
	}


	public void setStationList(List<String> stationList) {
		this.stationList = stationList;
	}


	public List<Double> getHourlyFootfallList() {
		return hourlyFootfallList;
	}


	public void setHourlyFootfallList(List<Double> hourlyFootfallList) {
		this.hourlyFootfallList = hourlyFootfallList;
	}

	
	
	
	

}