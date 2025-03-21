package com.yash.Trading.platform.controller;

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

import com.yash.Trading.platform.model.Coin;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.WatchList;
import com.yash.Trading.platform.service.CoinService;
import com.yash.Trading.platform.service.UserService;
import com.yash.Trading.platform.service.WatchListService;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

	@Autowired
	private WatchListService watchListService;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private CoinService coinservice;
	
	@GetMapping("/user")
	public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception
	{
	    User user = userservice.findUserProfileByJwt(jwt);
		WatchList watchList = watchListService.findUserWatchList(user.getId());
		
		return ResponseEntity.ok(watchList);
	}
	
	@PostMapping("/create")
	public ResponseEntity<WatchList> createWathcList(@RequestHeader("Authorization") String jwt) throws Exception 
	{
		User user = userservice.findUserProfileByJwt(jwt);
		WatchList createWatchList = watchListService.createWatchList(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createWatchList);
	}
	
	@GetMapping("/{watchlistId}")
	public ResponseEntity<WatchList> getWatchListById(
			@PathVariable Long watchlistId) throws Exception
	{
		WatchList watchList = watchListService.findById(watchlistId);
		return ResponseEntity.ok(watchList);
	}
	
	@PatchMapping("/add//coin/{coinId}")
	public ResponseEntity<Coin> addItemToWatchList(@RequestHeader("Authorization")String jwt,
	@PathVariable String coinId) throws Exception{
		User user = userservice.findUserProfileByJwt(jwt);
		Coin coin = coinservice.findById(coinId);
		Coin addedCoin = watchListService.addItemToWatchList(coin, user);
		return ResponseEntity.ok(addedCoin);
	}
}
