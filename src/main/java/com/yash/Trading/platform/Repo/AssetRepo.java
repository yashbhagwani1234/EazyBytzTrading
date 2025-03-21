package com.yash.Trading.platform.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.Asset;

public interface AssetRepo extends JpaRepository<Asset,Long>{

	List<Asset> findByUserId(Long userid);
	
	Asset findByUserIdAndCoinId(Long userId,String coinId);
}
