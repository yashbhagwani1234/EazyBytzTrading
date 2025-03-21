package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.PaymentsDetail;

public interface PaymentDetailrepo extends JpaRepository<PaymentsDetail,Long>{
	PaymentsDetail findByUserId(Long userId);
	

}
