package com.ticketingsystem.ticketingsystem.SOT.railways.model;

import java.util.List;

public class Footfall {
	
	private String startDate;
	private String endDate;
	private List<String> stationList;
	private List<Double> footfallList;
	public Footfall() {
		super();
	}
	public Footfall(String startDate, String endDate, List<String> stationList, List<Double> footfallList) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.stationList = stationList;
		this.footfallList = footfallList;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<String> getStationList() {
		return stationList;
	}
	public void setStationList(List<String> stationList) {
		this.stationList = stationList;
	}
	public List<Double> getFootfallList() {
		return footfallList;
	}
	public void setFootfallList(List<Double> footfallList) {
		this.footfallList = footfallList;
	}
	
	
	
	
	

}

