package com.yash.Trading.platform.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.Trading.platform.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String email);
}
