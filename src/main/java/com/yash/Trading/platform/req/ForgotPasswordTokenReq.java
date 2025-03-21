package com.yash.Trading.platform.req;

import com.yash.Trading.platform.domain.VerficationType;

public class ForgotPasswordTokenReq {

	private String sendTo;
	private VerficationType vfType;
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public VerficationType getVfType() {
		return vfType;
	}
	public void setVfType(VerficationType vfType) {
		this.vfType = vfType;
	}
	
}
