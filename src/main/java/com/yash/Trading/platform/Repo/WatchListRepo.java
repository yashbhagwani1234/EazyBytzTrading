package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.WatchList;

public interface WatchListRepo extends JpaRepository<WatchList,Long>{

	WatchList findByUserId(Long userId);
}
