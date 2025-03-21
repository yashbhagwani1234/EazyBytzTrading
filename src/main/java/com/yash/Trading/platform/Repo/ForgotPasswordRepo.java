package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.ForgotPasswordToken;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordToken,String>{

	ForgotPasswordToken findByUserId(Long userid);
}
