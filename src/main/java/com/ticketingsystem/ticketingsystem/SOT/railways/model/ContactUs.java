package com.ticketingsystem.ticketingsystem.SOT.railways.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ContactUs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String firstName;
	private String lastName;
	private String issue;
	private String content;
	
	public ContactUs() {
		super();
	}

	public ContactUs(Long id, String firstName, String lastName, String issue, String content) {
		super();
		Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.issue = issue;
		this.content = content;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}