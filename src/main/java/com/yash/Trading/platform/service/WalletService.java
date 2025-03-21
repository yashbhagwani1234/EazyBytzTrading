package com.yash.Trading.platform.service;


import com.yash.Trading.platform.model.Order;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.Wallet;

public interface WalletService {

	Wallet gettUserWallet(User user);
	
	Wallet addBalance(Wallet wallet,Long money);
	
	Wallet findWalletById(Long id) throws Exception;
	
	Wallet walletToWalletTracsfer(User sender,Wallet recevierWallet,Long amout) throws Exception;
	
	Wallet payOrderPayment(Order order,User user) throws Exception;
}
