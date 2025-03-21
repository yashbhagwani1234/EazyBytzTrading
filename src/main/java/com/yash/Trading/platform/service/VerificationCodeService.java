package com.yash.Trading.platform.service;

import com.yash.Trading.platform.domain.VerficationType;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.VerificationCode;

public interface VerificationCodeService {
	
	VerificationCode sendVerificationCode(User user,VerficationType verificationType);
	
	VerificationCode getVerificationCodeById(Long id) throws Exception;
	
	VerificationCode getVerificationCodeByUser(Long userId);
	
	void deleteVerificationCodeById(VerificationCode verificationCode);

}
