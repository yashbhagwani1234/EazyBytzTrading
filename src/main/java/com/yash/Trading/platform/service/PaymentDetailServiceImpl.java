package com.yash.Trading.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.PaymentDetailrepo;
import com.yash.Trading.platform.model.PaymentsDetail;
import com.yash.Trading.platform.model.User;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService{

	@Autowired
	private PaymentDetailrepo repo;
	
	@Override
	public PaymentsDetail addPaymentDetails(String accountNumber, String accoutHolderName, String ifsc, String bankName,
			User user) {
		// TODO Auto-generated method stub
		PaymentsDetail pd = new PaymentsDetail();
		pd.setAccountNumber(accountNumber);
		pd.setAccountHolderName(accoutHolderName);
		pd.setIfsc(ifsc);
		pd.setBankName(bankName);
		pd.setUser(user);
		return repo.save(pd);
	}

	@Override
	public PaymentsDetail getUserPaymentDetails(User user) {
		// TODO Auto-generated method stub
		return repo.findByUserId(user.getId());
	}

}
