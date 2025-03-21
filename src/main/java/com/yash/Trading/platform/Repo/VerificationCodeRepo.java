package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.VerificationCode;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode,Long>{

	public VerificationCode findByUserId(Long userId);
}
