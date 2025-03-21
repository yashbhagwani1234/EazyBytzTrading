package com.yash.Trading.platform.model;

import com.yash.Trading.platform.domain.VerficationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class VerificationCode {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String otp;
	
	@OneToOne
	private User user;
	
	private String email;
	
	private String mobile;
	
	private VerficationType verifivationtype;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public VerficationType getVerifivationtype() {
		return verifivationtype;
	}

	public void setVerifivationtype(VerficationType verifivationtype) {
		this.verifivationtype = verifivationtype;
	}
	
}
