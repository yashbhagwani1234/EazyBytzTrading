package com.yash.Trading.platform.service;

import com.yash.Trading.platform.domain.VerficationType;
import com.yash.Trading.platform.model.User;

public interface UserService {

	public User findUserProfileByJwt(String jwt) throws Exception;
	public User findUserByEmail(String email) throws Exception;
	public User findUserById(Long userId) throws Exception;
	
	public User enableTwoFactorAuthentication(VerficationType vft,String sendTo,User user);
	User updatePassword(User user,String newPass);
	
}
