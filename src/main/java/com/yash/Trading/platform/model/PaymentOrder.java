package com.yash.Trading.platform.model;

import com.yash.Trading.platform.domain.PaymentMethod;
import com.yash.Trading.platform.domain.PaymentOrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PaymentOrder {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	private Long amount;
	
	private PaymentOrderStatus status;
	
	private PaymentMethod pm;
	
	@ManyToOne
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public PaymentOrderStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentOrderStatus status) {
		this.status = status;
	}

	public PaymentMethod getPm() {
		return pm;
	}

	public void setPm(PaymentMethod pm) {
		this.pm = pm;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
