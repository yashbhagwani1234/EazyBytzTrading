package com.yash.Trading.platform.req;

public class ResetPasswordReq {

	private String otp;
	
	private String password;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
