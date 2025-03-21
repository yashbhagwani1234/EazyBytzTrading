package com.yash.Trading.platform.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.Withdrawal;

public interface WithdrawalRepo extends JpaRepository<Withdrawal,Long>{

	List<Withdrawal> findByUserId(Long userId);
}
