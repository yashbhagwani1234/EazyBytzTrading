package com.yash.Trading.platform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.WatchListRepo;
import com.yash.Trading.platform.model.Coin;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.WatchList;

@Service
public class WatchListServiceImpl implements WatchListService{

	@Autowired
	private WatchListRepo repo;
	
	@Override
	public WatchList findUserWatchList(Long userId) throws Exception {
		// TODO Auto-generated method stub
		WatchList watchList = repo.findByUserId(userId);
		if(watchList==null)
		{
			throw new Exception("watchList not found");
		}
		return watchList;
	}

	@Override
	public WatchList createWatchList(User user) {
		// TODO Auto-generated method stub
		WatchList watchList = new WatchList();
		watchList.setUser(user);
		
		return repo.save(watchList);
	}

	@Override
	public WatchList findById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<WatchList> optional = repo.findById(id);
		if(optional.isEmpty())
		{
			throw new Exception("Watch list not found");
		}
		return optional.get();
	}

	@Override
	public Coin addItemToWatchList(Coin coin, User user) throws Exception {
		// TODO Auto-generated method stub
		WatchList watchList = findUserWatchList(user.getId());
		if(watchList.getCoins().contains(coin))
		{
			watchList.getCoins().remove(coin);
		}
		else watchList.getCoins().add(coin);
	    repo.save(watchList);
	    return coin;
	}

}
