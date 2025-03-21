package com.yash.Trading.platform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.WithdrawalRepo;
import com.yash.Trading.platform.domain.WithdrawalStatus;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.Withdrawal;

@Service
public class WithdrawalServiceimpl implements WithDrawalService{

	@Autowired
	private WithdrawalRepo repo;
	
	@Override
	public Withdrawal requestWithDrawal(Long amout, User user) {
		// TODO Auto-generated method stub
		Withdrawal with= new Withdrawal();
		with.setAmout(amout);
		with.setUser(user);
		with.setStatus(WithdrawalStatus.PENDING);
		return repo.save(with);
	}

	@Override
	public Withdrawal proceedWithDrawal(Long withDrawalId, boolean accept) throws Exception {
		// TODO Auto-generated method stub
		Optional<Withdrawal> with = repo.findById(withDrawalId);
		if(with.isEmpty())
		{
			throw new Exception("withdrawal not found");
		}
		Withdrawal with1 = new Withdrawal();
		with1.setDate(LocalDateTime.now());
		
		if(accept)
		{
			with1.setStatus(WithdrawalStatus.SUCCESS);
		}
		else {
			with1.setStatus(WithdrawalStatus.PENDING);
		}
		return repo.save(with1);
	}

	@Override
	public List<Withdrawal> getUsersWithDrawalHistory(User user) {
		// TODO Auto-generated method stub
		return repo.findByUserId(user.getId());
	}

	@Override
	public List<Withdrawal> getAllWithdrawalRequest() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

}
