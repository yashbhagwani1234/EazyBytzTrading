package com.yash.Trading.platform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.UserRepository;
import com.yash.Trading.platform.config.JwtProvider;
import com.yash.Trading.platform.domain.VerficationType;
import com.yash.Trading.platform.model.TwoFactorAuth;
import com.yash.Trading.platform.model.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repo;
	
	
	@Override
	public User findUserProfileByJwt(String jwt) throws Exception {
		// TODO Auto-generated method stub
		String email = JwtProvider.getEmailFromToken(jwt);
		User user = repo.findByEmail(email);
		
		if(user==null)
		{
			throw new Exception("user not found");
		}
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
         User user = repo.findByEmail(email);
		
		if(user==null)
		{
			throw new Exception("user not found");
		}
		return user;
		
	}

	@Override
	public User findUserById(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Optional<User> user = repo.findById(userId);
		if(user.isEmpty())
		{
			throw new Exception("user not found");
			
		}
		return user.get();
	}

	@Override
	public User enableTwoFactorAuthentication(VerficationType vft,String sendTo,User user) {
		// TODO Auto-generated method stub
		TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
		twoFactorAuth.setEnabled(true);
		twoFactorAuth.setSendTo(vft);
		
		user.setTwofactorauth(twoFactorAuth);
		
		return repo.save(user);
	}

	@Override
	public User updatePassword(User user, String newPass) {
		// TODO Auto-generated method stub
		user.setPassword(newPass);
		return repo.save(user);
	}

}
