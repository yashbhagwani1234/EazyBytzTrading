package com.yash.Trading.platform.service;

import java.util.List;

import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.Withdrawal;

public interface WithDrawalService {

	Withdrawal requestWithDrawal(Long amout, User user);
	
	Withdrawal proceedWithDrawal(Long withDrawalId,boolean accept) throws Exception;
	
	List<Withdrawal> getUsersWithDrawalHistory(User user);
	
	List<Withdrawal> getAllWithdrawalRequest();
}
