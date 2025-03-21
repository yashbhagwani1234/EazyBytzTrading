package com.yash.Trading.platform.service;

import com.yash.Trading.platform.model.TwoFactorOtp;
import com.yash.Trading.platform.model.User;

public interface TwoFactorOtpService {

	TwoFactorOtp createTwoFactorOtp(User user,String otp,String jwt);
	
	TwoFactorOtp findByUser(Long userId);
	
	TwoFactorOtp findById(String id);
	
	boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp,String otp);
	
	void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp);
}
