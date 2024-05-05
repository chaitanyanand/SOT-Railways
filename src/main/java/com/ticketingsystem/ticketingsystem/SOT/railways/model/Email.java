package com.ticketingsystem.ticketingsystem.SOT.railways.model;

public class Email {
	private String email;
    private Long ticketId;
	
	public Email() {
		super();
	}

	public Email(String email, Long ticketId) {
		super();
		this.email = email;
		this.ticketId = ticketId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	
}
