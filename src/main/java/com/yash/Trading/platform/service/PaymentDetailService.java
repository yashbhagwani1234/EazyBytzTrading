package com.yash.Trading.platform.service;

import com.yash.Trading.platform.model.PaymentsDetail;
import com.yash.Trading.platform.model.User;

public interface PaymentDetailService {

	public PaymentsDetail addPaymentDetails(String accountNumber,String accoutHolderName,String ifsc,String bankName ,User user);
	
	public PaymentsDetail getUserPaymentDetails(User user);
}
