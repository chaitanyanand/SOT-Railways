package com.ticketingsystem.ticketingsystem.SOT.railways.model;

import java.util.List;



public class Revenue {

	private String startDate;
	private String endDate;
	private List<String> stationList;
	private List<Double> revenueList;
	
	
	public Revenue() {
		super();
	}


	public Revenue(String startDate, String endDate, List<String> stationList, List<Double> revenueList) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.stationList = stationList;
		this.revenueList = revenueList;
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


	public List<Double> getRevenueList() {
		return revenueList;
	}


	public void setRevenueList(List<Double> revenueList) {
		this.revenueList = revenueList;
	}


	

	

}