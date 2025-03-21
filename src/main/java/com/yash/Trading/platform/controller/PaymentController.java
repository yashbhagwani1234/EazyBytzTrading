package com.yash.Trading.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.Trading.platform.Response.PaymentResponse;
import com.yash.Trading.platform.domain.PaymentMethod;
import com.yash.Trading.platform.model.PaymentOrder;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.service.PaymentService;
import com.yash.Trading.platform.service.UserService;

@RestController
public class PaymentController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private PaymentService paymentservice;
	
	@PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
	public ResponseEntity<PaymentResponse> paymentHandler(
			@PathVariable PaymentMethod paymentMethod,
			@PathVariable Long amount,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userservice.findUserProfileByJwt(jwt);
		
		PaymentResponse paymentresp;
		
		PaymentOrder order = paymentservice.createOrder(user, amount, paymentMethod);
		
		if(paymentMethod.equals(PaymentMethod.RAZORPAY))
		{
			paymentresp=paymentservice.createRazorpayPaymenting(user, amount,order.getId());
		}
		else {
			paymentresp = paymentservice.createStrpePaymenting(user, amount, order);
			
		}
		return new ResponseEntity<>(paymentresp,HttpStatus.CREATED);
	}
	
}
