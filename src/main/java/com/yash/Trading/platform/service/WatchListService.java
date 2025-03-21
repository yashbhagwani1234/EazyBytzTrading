package com.yash.Trading.platform.service;

import com.yash.Trading.platform.model.Coin;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.WatchList;

public interface WatchListService {

	WatchList findUserWatchList(Long userId) throws Exception;
	WatchList createWatchList(User user);
	WatchList findById(Long id) throws Exception;
	Coin addItemToWatchList(Coin coin,User user) throws Exception;
}
