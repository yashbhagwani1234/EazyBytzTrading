package com.yash.Trading.platform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.VerificationCodeRepo;
import com.yash.Trading.platform.domain.VerficationType;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.VerificationCode;
import com.yash.Trading.platform.util.OtpUtils;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService{

	@Autowired
	private VerificationCodeRepo repo;
	
	@Override
	public VerificationCode sendVerificationCode(User user,VerficationType verficationType) {
		// TODO Auto-generated method stub
		VerificationCode verificationCode1 = new VerificationCode();
		verificationCode1.setOtp(OtpUtils.generateOtp());
		verificationCode1.setVerifivationtype(verficationType);
		verificationCode1.setUser(user);
		return repo.save(verificationCode1);
	}

	@Override
	public VerificationCode getVerificationCodeById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<VerificationCode> verificationCode = repo.findById(id);
		if(verificationCode.isPresent())
		{
			return verificationCode.get();
		}
		throw new Exception("verfication code not found");
	}

	@Override
	public VerificationCode getVerificationCodeByUser(Long userId) {
		// TODO Auto-generated method stub
		return repo.findByUserId(userId);
	}

	@Override
	public void deleteVerificationCodeById(VerificationCode verificationCode) {
		// TODO Auto-generated method stub
      repo.delete(verificationCode);
	}

}
