package com.ticketingsystem.ticketingsystem.SOT.railways.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private String type;
	private String role;
	@Column(name = "verification_code", length = 64,updatable=false)
	private String verificationCode;
	private boolean enabled;
	 @Column(name = "reset_password_token")
	    private String resetPasswordToken;
	     
	     
	
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public User() {
		super();
		role="user";
	}

	public User(Long id, String email, String firstname, String lastname, String password, String type, String role,
			String verificationCode, boolean enabled) {
		super();
		Id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.type = type;
		this.role = role;
		this.verificationCode = verificationCode;
		this.enabled = enabled;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", password=" + password + ", type=" + type + ", role=" + role + ", verificationCode="
				+ verificationCode + ", enabled=" + enabled + "]";
	}

	
	
	
}
