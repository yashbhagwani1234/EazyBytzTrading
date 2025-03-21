package com.yash.Trading.platform.model;

import com.yash.Trading.platform.domain.VerficationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class ForgotPasswordToken {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	
	@OneToOne
	private User user;
	
	private String otp;
	
	private VerficationType verificationType;
	
	private String sendTo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public VerficationType getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(VerficationType verificationType) {
		this.verificationType = verificationType;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	
	
}
