package com.yash.Trading.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.Trading.platform.model.PaymentsDetail;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.service.PaymentDetailService;
import com.yash.Trading.platform.service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentDetailController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private PaymentDetailService pds;
	
	@PostMapping("/payment-details")
	public ResponseEntity<PaymentsDetail> addPaymentDetails(
			@RequestBody PaymentsDetail paymentDetailReq,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userservice.findUserProfileByJwt(jwt);
		
		PaymentsDetail paymentDetails = pds.addPaymentDetails(
				paymentDetailReq.getAccountNumber(),
				paymentDetailReq.getAccountHolderName(),
				paymentDetailReq.getIfsc(), 
				paymentDetailReq.getBankName(), user);
		return new ResponseEntity<>(paymentDetails,HttpStatus.CREATED);
				
	}
	@GetMapping("/payment-details")
	public ResponseEntity<PaymentsDetail> getUsersPaymentDetails(
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userservice.findUserProfileByJwt(jwt);
		
		PaymentsDetail paymentDetails = pds.getUserPaymentDetails(user);
		return new ResponseEntity<>(paymentDetails,HttpStatus.CREATED);
	}
}
