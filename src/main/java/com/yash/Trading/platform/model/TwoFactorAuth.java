package com.yash.Trading.platform.model;

import com.yash.Trading.platform.domain.VerficationType;

public class TwoFactorAuth {
	private boolean isEnabled;
	private VerficationType sendTo;
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public VerficationType getSendTo() {
		return sendTo;
	}
	public void setSendTo(VerficationType sendTo) {
		this.sendTo = sendTo;
	}
}
