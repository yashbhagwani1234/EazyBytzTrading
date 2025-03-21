package com.yash.Trading.platform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.ForgotPasswordRepo;
import com.yash.Trading.platform.domain.VerficationType;
import com.yash.Trading.platform.model.ForgotPasswordToken;
import com.yash.Trading.platform.model.User;

@Service
public class ForgotPasswordImpl implements ForgotPasswordService{

	@Autowired
	private ForgotPasswordRepo repo;
	
	@Override
	public ForgotPasswordToken createToken(User user, String id, String otp, VerficationType verificationType,
			String sendTo) {
		// TODO Auto-generated method stub
		ForgotPasswordToken token = new ForgotPasswordToken();
		token.setUser(user);
		token.setSendTo(sendTo);
		token.setOtp(otp);
		token.setId(id);
		
		return repo.save(token);
	}

	@Override
	public ForgotPasswordToken findByUser(Long userId) {
		// TODO Auto-generated method stub
		return repo.findByUserId(userId);
	}

	@Override
	public void deleteToken(ForgotPasswordToken token) {
		// TODO Auto-generated method stub
		repo.delete(token);
	}

	@Override
	public ForgotPasswordToken findById(String id) {
		// TODO Auto-generated method stub
		Optional<ForgotPasswordToken> token = repo.findById(id);
		return token.orElse(null);
	}

}
