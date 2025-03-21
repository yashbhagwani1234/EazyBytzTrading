package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.Wallet;

public interface WalletRepo extends JpaRepository<Wallet,Long>{

	Wallet findWalletByUserId(Long userId);
}
