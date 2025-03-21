package com.yash.Trading.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.Trading.platform.model.Asset;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.service.AssetService;
import com.yash.Trading.platform.service.UserService;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

	@Autowired
	private AssetService assetservice;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{assetId}")
	public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception{
		Asset asset = assetservice.getAssetById(assetId);
		return ResponseEntity.ok().body(asset);
		
	}
	
	@GetMapping("/coin/{coinId}/user")
	public ResponseEntity<Asset> getAssetByUserIdandCoinId(@PathVariable String coinId,@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserProfileByJwt(jwt);
		Asset asset = assetservice.findAssetByUserIdAndCoinId(user.getId(), coinId);
		return ResponseEntity.ok().body(asset);
	}
	
	@GetMapping()
	public ResponseEntity<List<Asset>> getAssetForUser(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		List<Asset> assets = assetservice.getUsersAssets(user.getId());
		return ResponseEntity.ok().body(assets);
		
	}
}
