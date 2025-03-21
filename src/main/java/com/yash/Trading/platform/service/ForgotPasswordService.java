package com.yash.Trading.platform.service;

import com.yash.Trading.platform.domain.VerficationType;
import com.yash.Trading.platform.model.ForgotPasswordToken;
import com.yash.Trading.platform.model.User;

public interface ForgotPasswordService {

	ForgotPasswordToken createToken(User user,String id, String otp,VerficationType verificationType,String sendTo);
	
	ForgotPasswordToken findByUser(Long userId);
	
	ForgotPasswordToken findById(String id);
	
	void deleteToken(ForgotPasswordToken token) ;
}
