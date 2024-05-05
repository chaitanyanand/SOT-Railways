package com.ticketingsystem.ticketingsystem.SOT.railways.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String ticketNumber;
	private Long userId;
	private String trainName;
	private int noOfSeatsBooked;
	private String noOfVacantSeats;
	private String departureDate;
	private String departureTime;
	private String destinationArrivalDate;
	private String destinationArrivalTime;
	private String departureStationName;
	private String destinationStationName;
	private String fare;
	private String paymentType;
	

	public Ticket() {
		super();
	}


	public Ticket(Long id, String ticketNumber, Long userId, String trainName, int noOfSeatsBooked,
			String noOfVacantSeats, String departureDate, String departureTime, String destinationArrivalDate,
			String destinationArrivalTime, String departureStationName, String destinationStationName, String fare,
			String paymentType) {
		super();
		Id = id;
		this.ticketNumber = ticketNumber;
		this.userId = userId;
		this.trainName = trainName;
		this.noOfSeatsBooked = noOfSeatsBooked;
		this.noOfVacantSeats = noOfVacantSeats;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.destinationArrivalDate = destinationArrivalDate;
		this.destinationArrivalTime = destinationArrivalTime;
		this.departureStationName = departureStationName;
		this.destinationStationName = destinationStationName;
		this.fare = fare;
		this.paymentType = paymentType;
	}


	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getTicketNumber() {
		return ticketNumber;
	}


	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getTrainName() {
		return trainName;
	}


	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}


	public int getNoOfSeatsBooked() {
		return noOfSeatsBooked;
	}


	public void setNoOfSeatsBooked(int noOfSeatsBooked) {
		this.noOfSeatsBooked = noOfSeatsBooked;
	}


	public String getNoOfVacantSeats() {
		return noOfVacantSeats;
	}


	public void setNoOfVacantSeats(String noOfVacantSeats) {
		this.noOfVacantSeats = noOfVacantSeats;
	}


	public String getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}


	public String getDepartureTime() {
		return departureTime;
	}


	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}


	public String getDestinationArrivalDate() {
		return destinationArrivalDate;
	}


	public void setDestinationArrivalDate(String destinationArrivalDate) {
		this.destinationArrivalDate = destinationArrivalDate;
	}


	public String getDestinationArrivalTime() {
		return destinationArrivalTime;
	}


	public void setDestinationArrivalTime(String destinationArrivalTime) {
		this.destinationArrivalTime = destinationArrivalTime;
	}


	public String getDepartureStationName() {
		return departureStationName;
	}


	public void setDepartureStationName(String departureStationName) {
		this.departureStationName = departureStationName;
	}


	public String getDestinationStationName() {
		return destinationStationName;
	}


	public void setDestinationStationName(String destinationStationName) {
		this.destinationStationName = destinationStationName;
	}


	public String getFare() {
		return fare;
	}


	public void setFare(String fare) {
		this.fare = fare;
	}


	public String getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

		
	
}
