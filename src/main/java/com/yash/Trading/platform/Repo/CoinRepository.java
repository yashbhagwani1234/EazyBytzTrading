package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.Coin;

public interface CoinRepository extends JpaRepository<Coin,String>{

}
