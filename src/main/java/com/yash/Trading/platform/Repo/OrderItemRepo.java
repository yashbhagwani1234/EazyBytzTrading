package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long>{

}
