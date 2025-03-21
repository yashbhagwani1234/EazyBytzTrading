package com.yash.Trading.platform.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.yash.Trading.platform.Response.PaymentResponse;
import com.yash.Trading.platform.domain.PaymentMethod;
import com.yash.Trading.platform.model.PaymentOrder;
import com.yash.Trading.platform.model.User;

public interface PaymentService {

	PaymentOrder createOrder(User user,Long amount,PaymentMethod pm);
	
	PaymentOrder getPaymentOrderById(Long id) throws Exception;
	
	Boolean proccedPaymentOrder(PaymentOrder po,String paymentId) throws RazorpayException;
	
	PaymentResponse createRazorpayPaymenting(User user,Long amount,Long orderId) throws RazorpayException;
	
	PaymentResponse createStrpePaymenting(User user,Long amount,PaymentOrder order) throws StripeException;
    
}
