package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.TwoFactorOtp;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOtp,String>{

	TwoFactorOtp findByUserId(Long userId);
}
