package com.ticketingsystem.ticketingsystem.SOT.railways.model;

public class Card {
	private String fullName;
	private String cardNumber;
	private String month;
	private String year;
	private String cvv;
	private Long ticketId;
	private String fare;
	public Card() {
		super();
	}
	public Card(String fullName, String cardNumber, String month, String year, String cvv, Long ticketId, String fare) {
		super();
		this.fullName = fullName;
		this.cardNumber = cardNumber;
		this.month = month;
		this.year = year;
		this.cvv = cvv;
		this.ticketId = ticketId;
		this.fare = fare;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getFare() {
		return fare;
	}
	public void setFare(String fare) {
		this.fare = fare;
	}
	
	
	
	

}
