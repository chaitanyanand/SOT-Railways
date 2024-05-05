package com.ticketingsystem.ticketingsystem.SOT.railways.model;

import java.util.List;

public class TrainSearchResult implements Comparable<TrainSearchResult> {
	private String trainName;
	// private String trainNumber;
	private String departureDate;
	private String destinationArrivalDate;
	private String departureTime;
	private String destinationArrivalTime;
	private String departureStation;
	private String destinationStation;
	private int noOfVacantSeats;
	private int fare;

	public TrainSearchResult() {
		super();
	}
	
	public TrainSearchResult(String trainName, String departureDate, String destinationArrivalDate,
			String departureTime, String destinationArrivalTime, String departureStation, String destinationStation,
			int noOfVacantSeats, int fare) {
		super();
		this.trainName = trainName;
		this.departureDate = departureDate;
		this.destinationArrivalDate = destinationArrivalDate;
		this.departureTime = departureTime;
		this.destinationArrivalTime = destinationArrivalTime;
		this.departureStation = departureStation;
		this.destinationStation = destinationStation;
		this.noOfVacantSeats = noOfVacantSeats;
		this.fare = fare;
	}
	

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDestinationArrivalDate() {
		return destinationArrivalDate;
	}

	public void setDestinationArrivalDate(String destinationArrivalDate) {
		this.destinationArrivalDate = destinationArrivalDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getDestinationArrivalTime() {
		return destinationArrivalTime;
	}

	public void setDestinationArrivalTime(String destinationArrivalTime) {
		this.destinationArrivalTime = destinationArrivalTime;
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

	public int getNoOfVacantSeats() {
		return noOfVacantSeats;
	}

	public void setNoOfVacantSeats(int noOfVacantSeats) {
		this.noOfVacantSeats = noOfVacantSeats;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public int compareTimes(String time1,String time2) {
		String[]arr1=time1.split(":");
		String []arr2=time2.split(":");
		int hours1=Integer.parseInt(arr1[0]);
		int min1=Integer.parseInt(arr1[1]);
		int hours2=Integer.parseInt(arr2[0]);
		int min2=Integer.parseInt(arr2[1]);
		if(hours1>hours2)
			return 1;
		else if(hours1<hours2) {
			return -1;
		}
		else {
			return min1-min2;
		}
		
	}
	@Override
	public int compareTo(TrainSearchResult trainSearchResult) {
		return compareTimes(this.departureTime,trainSearchResult.getDepartureTime());
	}

}
