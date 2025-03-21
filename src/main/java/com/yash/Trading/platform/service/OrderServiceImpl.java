package com.yash.Trading.platform.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.Trading.platform.Repo.OrderItemRepo;
import com.yash.Trading.platform.Repo.OrderRepo;
import com.yash.Trading.platform.domain.OrderStatus;
import com.yash.Trading.platform.domain.OrderType;
import com.yash.Trading.platform.model.Asset;
import com.yash.Trading.platform.model.Coin;
import com.yash.Trading.platform.model.Order;
import com.yash.Trading.platform.model.OrderItem;
import com.yash.Trading.platform.model.User;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepo repo;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private OrderItemRepo itemrepo;
	
	@Autowired
	private AssetService service;
	
	@Override
	public Order createOrder(User user,OrderItem orderItem, OrderType orderType) {
		// TODO Auto-generated method stub
		double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
		Order order = new Order();
		order.setUser(user);
		order.setOrderItem(orderItem);
		order.setOrderType(orderType);
		order.setPrice(BigDecimal.valueOf(price));
		order.setTimestamp(LocalDateTime.now());
		order.setStatus(OrderStatus.PENDING);
		
		
		return repo.save(order);
	}

	@Override
	public Order getOrderById(Long orderId) throws Exception {
		// TODO Auto-generated method stub
		return repo.findById(orderId).orElseThrow(()->new Exception("order not found"));
		
	}

	@Override
	public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
		// TODO Auto-generated method stub
		return repo.findByUserId(userId);
		
	}

	private OrderItem createOrderItem(Coin coin,double quantity,double buyPrice,double sellPrice)
	{
		OrderItem orderItem = new OrderItem();
		orderItem.setCoin(coin);
		orderItem.setQuantity(quantity);
		orderItem.setBuyprice(buyPrice);
		orderItem.setSellprice(sellPrice);
		
		return  itemrepo.save(orderItem);
	}
	
	@Transactional
	public Order buyAsset(Coin coin,double quantity,User user) throws Exception
	{
		if(quantity<=0)
		{
			throw new Exception("quantity should be > 0");
		}
		double buyPrice = coin.getCurrentPrice();
		
		OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,0);
		Order order = createOrder(user,orderItem,OrderType.BUY);
		orderItem.setOrder(order);
		
		walletService.payOrderPayment(order, user);
		
		order.setStatus(OrderStatus.SUCCESS);
		order.setOrderType(OrderType.BUY);
		Order savedOrder = repo.save(order);
		// create Asset
		
		Asset oldAsset = service.findAssetByUserIdAndCoinId(order.getUser().getId(),order.getOrderItem().getCoin().getId());
		
		if(oldAsset==null)
		{
			service.createAsset(user, orderItem.getCoin(),orderItem.getQuantity());
		}
		else {
			service.updatAsset(oldAsset.getId(), quantity);
		}
		return savedOrder;
	}
	@Transactional
	public Order sellAsset(Coin coin,double quantity,User user) throws Exception
	{
		if(quantity<=0)
		{
			throw new Exception("quantity should be > 0");
		}
		double sellPrice = coin.getCurrentPrice();
		
		Asset assetToSell = service.findAssetByUserIdAndCoinId(user.getId(),coin.getId());
		double buyPrice = assetToSell.getBuyprice();
		if(assetToSell!=null)
		{
		  	
		    OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,sellPrice);
		    Order order = createOrder(user,orderItem,OrderType.SELL);
		    orderItem.setOrder(order);
		
		if(assetToSell.getQuantity()>=quantity)
		{
			order.setStatus(OrderStatus.SUCCESS);
			order.setOrderType(OrderType.SELL);
			Order savedOrder = repo.save(order);
			
			walletService.payOrderPayment(order, user);
			
			Asset updateAsset = service.updatAsset(assetToSell.getId(), -quantity);
			
			Asset updatedAsset = service.updatAsset(assetToSell.getId(),-quantity);
			if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1) {
				service.deleteAsset(updatedAsset.getId());
			}
			return savedOrder;
		  }
		throw new Exception("unsuffiecient quantity to sell");
		}
		throw new Exception("asset not found");
	}
	@Override
	@Transactional
	public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
		// TODO Auto-generated method stub
		
		if(orderType.equals(OrderType.BUY))
		{
			return buyAsset(coin,quantity,user);
		}
		else if(orderType.equals(OrderType.SELL))
		{
			return sellAsset(coin,quantity,user);
		}
		throw new Exception("Invalid order type");
	}

}
