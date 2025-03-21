package com.yash.Trading.platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private double quantity;
	
	@ManyToOne
	private Coin coin;
	
	private double buyprice;
	
	private double sellprice;
	
	@JsonIgnore
	@OneToOne
	private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Coin getCoin() {
		return coin;
	}

	public void setCoin(Coin coin) {
		this.coin = coin;
	}

	public double getBuyprice() {
		return buyprice;
	}

	public void setBuyprice(double buyprice) {
		this.buyprice = buyprice;
	}

	public double getSellprice() {
		return sellprice;
	}

	public void setSellprice(double sellprice) {
		this.sellprice = sellprice;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
