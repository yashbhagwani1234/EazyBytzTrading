package com.yash.Trading.platform.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.WalletRepo;
import com.yash.Trading.platform.domain.OrderType;
import com.yash.Trading.platform.model.Order;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.Wallet;

@Service
public class WalletServiceImpl implements WalletService{

	@Autowired
	private WalletRepo walletRepo;
	
	@Override
	public Wallet gettUserWallet(User user) {
		// TODO Auto-generated method stub
		Wallet wallet = walletRepo.findWalletByUserId(user.getId());
		if(wallet==null)
		{
			wallet = new Wallet();
			wallet.setUser(user);
			walletRepo.save(wallet);
		}
		return wallet;
	}

	@Override
	public Wallet addBalance(Wallet wallet, Long money) {
		// TODO Auto-generated method stub
		BigDecimal balance = wallet.getBalance();
		BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
		
		wallet.setBalance(newBalance);
		return walletRepo.save(wallet);
	}

	@Override
	public Wallet findWalletById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Wallet> wallet = walletRepo.findById(id);
		if(wallet.isPresent())
		{
			return wallet.get();
		}
		throw new Exception("Wallet not found");
	}

	@Override
	public Wallet walletToWalletTracsfer(User sender, Wallet recevierWallet, Long amout) throws Exception {
		// TODO Auto-generated method stub
	    Wallet senderWallet = gettUserWallet(sender);
	    if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amout))<0)
	    {
	    	throw new Exception("Insufficient balance..");
	    }
	    BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amout));
	    senderWallet.setBalance(senderBalance);
	    walletRepo.save(senderWallet);
	    
	    BigDecimal reciverBalance = recevierWallet.getBalance().add(BigDecimal.valueOf(amout));
	    recevierWallet.setBalance(reciverBalance);
	    walletRepo.save(recevierWallet);
	    return senderWallet;
	}

	@Override
	public Wallet payOrderPayment(Order order, User user) throws Exception {
		// TODO Auto-generated method stub
		Wallet wallet = gettUserWallet(user);
		
		if(order.getClass().equals(OrderType.BUY))
		{
			BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
			if(newBalance.compareTo(order.getPrice())<0)
			{
				throw new Exception("insufficient funds for this transaction");
			}
			wallet.setBalance(newBalance);
		}
		else {
			BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
			wallet.setBalance(newBalance);
		}
		walletRepo.save(wallet);
		return wallet;
	}

}
