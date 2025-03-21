package com.yash.Trading.platform.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.Order;

public interface OrderRepo extends JpaRepository<Order,Long>{

	List<Order> findByUserId(Long userId);
}
