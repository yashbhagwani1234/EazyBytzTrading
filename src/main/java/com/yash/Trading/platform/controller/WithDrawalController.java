package com.yash.Trading.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.Wallet;
import com.yash.Trading.platform.model.Withdrawal;
import com.yash.Trading.platform.service.UserService;
import com.yash.Trading.platform.service.WalletService;
import com.yash.Trading.platform.service.WithDrawalService;

@RestController
public class WithDrawalController {

	@Autowired
	private WithDrawalService withservice;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userservice;
	
   	@PostMapping("/api/withdrawal/{amount}")
   	public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount,@RequestHeader("Authorization") String jwt) throws Exception
   	{
   		User user = userservice.findUserProfileByJwt(jwt);
   		Wallet userWallet = walletService.gettUserWallet(user);
   		
   		Withdrawal with = withservice.requestWithDrawal(amount, user);
   		walletService.addBalance(userWallet,with.getAmout());
   		
   		return new ResponseEntity<>(with,HttpStatus.OK);
   		
   	}
   	@PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
   	public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id,@PathVariable boolean accept,@RequestHeader("Authorization") String jwt) throws Exception
   	{
   		User user = userservice.findUserProfileByJwt(jwt);
   		Withdrawal with = withservice.proceedWithDrawal(id, accept);
   		
   		Wallet userWallet = walletService.gettUserWallet(user);
   		if(!accept)
   		{
   			walletService.addBalance(userWallet, with.getAmout());
   		}
   		return new ResponseEntity<>(with,HttpStatus.OK);
   	}
   	
   	@GetMapping("/api/withdrawal")
   	public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(@RequestHeader("Authorization")String jwt) throws Exception{
   		User user = userservice.findUserProfileByJwt(jwt);
   		List<Withdrawal> with = withservice.getUsersWithDrawalHistory(user);
   		return new ResponseEntity<>(with,HttpStatus.OK);
   	}
   	
   	@GetMapping("/api/admin/withdrawal")
   	public ResponseEntity<List<Withdrawal>> getWithdrawalRequest(@RequestHeader("Authorization")String jwt) throws Exception {
   		User user = userservice.findUserProfileByJwt(jwt);
   		List<Withdrawal> withdrawal = withservice.getAllWithdrawalRequest();
   		return new ResponseEntity<>(withdrawal,HttpStatus.OK);
   	}
}
