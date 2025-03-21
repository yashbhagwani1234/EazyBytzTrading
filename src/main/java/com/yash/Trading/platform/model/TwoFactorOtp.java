package com.yash.Trading.platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class TwoFactorOtp {

	@Id
	private String Id;
	
	private String otp;
	
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	@OneToOne
	private User user;
	
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String jwt;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
