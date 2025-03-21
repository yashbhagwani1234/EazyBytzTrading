package com.yash.Trading.platform.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yash.Trading.platform.Response.PaymentResponse;
import com.yash.Trading.platform.model.Order;
import com.yash.Trading.platform.model.PaymentOrder;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.Wallet;
import com.yash.Trading.platform.model.WalletTransaction;
import com.yash.Trading.platform.service.OrderService;
import com.yash.Trading.platform.service.PaymentService;
import com.yash.Trading.platform.service.UserService;
import com.yash.Trading.platform.service.WalletService;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private OrderService orderservice;
	
	@Autowired
	private PaymentService paymentservice;
	
	@GetMapping("/api/wallet")
	public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userservice.findUserProfileByJwt(jwt);
		
		Wallet wallet = walletService.gettUserWallet(user);
		
		return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
	}

@PutMapping("/api/wallet/{walletId}/transfer")	
 ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,@PathVariable Long walletId,@RequestBody WalletTransaction req) throws Exception
	{
		User senderUSer = userservice.findUserProfileByJwt(jwt);
		
		Wallet receiverWallet = walletService.findWalletById(walletId);
		Wallet wallet = walletService.walletToWalletTracsfer(senderUSer, receiverWallet,req.getAmount());
		return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
	}
@PutMapping("/api/wallet/order/{orderId}/pay")	
ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,@PathVariable Long orderId) throws Exception
	{
	
		User user = userservice.findUserProfileByJwt(jwt);
		
		Order order  = orderservice.getOrderById(orderId);
		Wallet wallet =  walletService.payOrderPayment(order, user);
		return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
	}

    @PutMapping("/api/wallet/deposit")	
    ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization") String jwt,
    		@RequestParam(name="order_id") Long orderId,
    		@RequestParam(name="payment_id") String paymentId) throws Exception
	{
	
		User user = userservice.findUserProfileByJwt(jwt);
		
		Wallet wallet = walletService.gettUserWallet(user);
		 
		PaymentOrder order = paymentservice.getPaymentOrderById(orderId);
		
		Boolean status= paymentservice.proccedPaymentOrder(order, paymentId);
		
		if(wallet.getBalance()==null)
		{
			wallet.setBalance(BigDecimal.valueOf(0));
		}
		if(status)
		{
			
			wallet = walletService.addBalance(wallet, order.getAmount());
			
		}
		
		return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
	}
}
