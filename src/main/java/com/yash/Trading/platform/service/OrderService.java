package com.yash.Trading.platform.service;

import java.util.List;

import com.yash.Trading.platform.domain.OrderType;
import com.yash.Trading.platform.model.Coin;
import com.yash.Trading.platform.model.Order;
import com.yash.Trading.platform.model.OrderItem;
import com.yash.Trading.platform.model.User;

public interface OrderService {

	Order createOrder(User user,OrderItem orderItem,OrderType orderType);
	
	Order getOrderById(Long orderId) throws Exception;
	
	List<Order> getAllOrderOfUser(Long userId,OrderType orderType,String assetSymbol);
	
	Order processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;
	
	
}
