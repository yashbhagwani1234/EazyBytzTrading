package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.PaymentOrder;

public interface PaymentOrderRepo extends JpaRepository<PaymentOrder,Long>{

     
}
