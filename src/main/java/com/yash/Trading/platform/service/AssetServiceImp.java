package com.yash.Trading.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.AssetRepo;
import com.yash.Trading.platform.model.Asset;
import com.yash.Trading.platform.model.Coin;
import com.yash.Trading.platform.model.User;

@Service
public class AssetServiceImp implements AssetService{

	@Autowired
	private AssetRepo repo;
	
	@Override
	public Asset createAsset(User user, Coin coin, double quantity) {
		// TODO Auto-generated method stub
		Asset asset = new Asset();
		asset.setUser(user);
		asset.setCoin(coin);
		asset.setQuantity(quantity);
		asset.setBuyprice(coin.getCurrentPrice());
		return repo.save(asset);
	}

	@Override
	public Asset getAssetById(Long assetId) throws Exception {
		// TODO Auto-generated method stub
		return repo.findById(assetId).orElseThrow(()->new Exception("asset not found"));
	}

	@Override
	public Asset getAssetByUserIdAndId(Long userid, Long assedId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Asset> getUsersAssets(Long userId) {
		// TODO Auto-generated method stub
		return repo.findByUserId(userId);
	}

	@Override
	public Asset updatAsset(Long assetId, double quantity) throws Exception {
		// TODO Auto-generated method stub
		Asset oldAsset = getAssetById(assetId);
		oldAsset.setQuantity(quantity+oldAsset.getQuantity());
		return repo.save(oldAsset);
	}

	@Override
	public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
		// TODO Auto-generated method stub
		return repo.findByUserIdAndCoinId(userId, coinId);
	}

	@Override
	public void deleteAsset(Long assetId) {
		// TODO Auto-generated method stub
		repo.deleteById(assetId);
	}

}
