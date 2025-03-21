package com.yash.Trading.platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yash.Trading.platform.domain.USER_ROLE;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String fullname;
	private String email;
	
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@Embedded
	private TwoFactorAuth twofactorauth = new TwoFactorAuth();
	
	private USER_ROLE role = USER_ROLE.CUSTOMER_ROLE;
	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String fullname, String email, String password, TwoFactorAuth twofactorauth, USER_ROLE role) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.twofactorauth = twofactorauth;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TwoFactorAuth getTwofactorauth() {
		return twofactorauth;
	}

	public void setTwofactorauth(TwoFactorAuth twofactorauth) {
		this.twofactorauth = twofactorauth;
	}

	public USER_ROLE getRole() {
		return role;
	}

	public void setRole(USER_ROLE role) {
		this.role = role;
	}
	
	
}
