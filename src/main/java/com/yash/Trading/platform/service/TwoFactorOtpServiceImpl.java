package com.yash.Trading.platform.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.TwoFactorOtpRepository;
import com.yash.Trading.platform.model.TwoFactorOtp;
import com.yash.Trading.platform.model.User;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService{
    @Autowired
	private TwoFactorOtpRepository repo;
    
	@Override
	public TwoFactorOtp createTwoFactorOtp(User user, String otp, String jwt) {
		UUID uuid = UUID.randomUUID();
		
		String id = uuid.toString();
		
		TwoFactorOtp twoFactorOtp = new TwoFactorOtp();
		twoFactorOtp.setOtp(otp);
		twoFactorOtp.setJwt(jwt);
		twoFactorOtp.setId(id);
		twoFactorOtp.setUser(user);
		
		return repo.save(twoFactorOtp);
	}

	@Override
	public TwoFactorOtp findByUser(Long userId) {
		// TODO Auto-generated method stub
		return repo.findByUserId(userId);
	}

	@Override
	public TwoFactorOtp findById(String id) {
		// TODO Auto-generated method stub
		Optional<TwoFactorOtp> otp = repo.findById(id);
		return otp.orElse(null);
	}

	@Override
	public boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp, String otp) {
		// TODO Auto-generated method stub
		return twoFactorOtp.getOtp().equals(otp);
	}

	@Override
	public void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp) {
		// TODO Auto-generated method stub
	    repo.delete(twoFactorOtp);
		
	}
}
