package com.yash.Trading.platform.service;

import java.util.List;

import com.yash.Trading.platform.model.Asset;
import com.yash.Trading.platform.model.Coin;
import com.yash.Trading.platform.model.User;

public interface AssetService {

	Asset createAsset(User user,Coin coin,double quantity);
	
	Asset getAssetById(Long assetId) throws Exception;
	
	Asset getAssetByUserIdAndId(Long userid,Long assedId);
	
	List<Asset> getUsersAssets(Long userId);
	
	Asset updatAsset(Long assetId,double quantity) throws Exception;
	
	Asset findAssetByUserIdAndCoinId(Long userId,String coinId);
	
	void deleteAsset(Long assetId);
}
