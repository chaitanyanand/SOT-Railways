package com.ticketingsystem.ticketingsystem.SOT.railways.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class TicketFare {
	
	//These are initial values. values may have changed by the financingAdmin
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	private  int fareDuringNonPeakHour=50;
	private  int fareDuringPeakHour=65;
	private  String peakHourStart="8:00";
	private  String peakHourEnd="9:00";
	

	public TicketFare() {
		super();
	}
	public TicketFare(Long id, int fareDuringNonPeakHour, int fareDuringPeakHour, String peakHourStart,
			String peakHourEnd) {
		super();
		Id = id;
		this.fareDuringNonPeakHour = fareDuringNonPeakHour;
		this.fareDuringPeakHour = fareDuringPeakHour;
		this.peakHourStart = peakHourStart;
		this.peakHourEnd = peakHourEnd;
	}
	public TicketFare(int fareDuringNonPeakHour, int fareDuringPeakHour, String peakHourStart, String peakHourEnd) {
		super();
		this.fareDuringNonPeakHour = fareDuringNonPeakHour;
		this.fareDuringPeakHour = fareDuringPeakHour;
		this.peakHourStart = peakHourStart;
		this.peakHourEnd = peakHourEnd;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public int getFareDuringNonPeakHour() {
		return fareDuringNonPeakHour;
	}
	public void setFareDuringNonPeakHour(int fareDuringNonPeakHour) {
		this.fareDuringNonPeakHour = fareDuringNonPeakHour;
	}
	public int getFareDuringPeakHour() {
		return fareDuringPeakHour;
	}
	public void setFareDuringPeakHour(int fareDuringPeakHour) {
		this.fareDuringPeakHour = fareDuringPeakHour;
	}
	public String getPeakHourStart() {
		return peakHourStart;
	}
	public void setPeakHourStart(String peakHourStart) {
		this.peakHourStart = peakHourStart;
	}
	public String getPeakHourEnd() {
		return peakHourEnd;
	}
	public void setPeakHourEnd(String peakHourEnd) {
		this.peakHourEnd = peakHourEnd;
	}
		
	

}
