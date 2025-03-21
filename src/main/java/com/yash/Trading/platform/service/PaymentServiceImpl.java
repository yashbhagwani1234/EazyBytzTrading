package com.yash.Trading.platform.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import com.yash.Trading.platform.Repo.PaymentOrderRepo;
import com.yash.Trading.platform.Response.PaymentResponse;
import com.yash.Trading.platform.domain.PaymentMethod;
import com.yash.Trading.platform.domain.PaymentOrderStatus;
import com.yash.Trading.platform.model.PaymentOrder;
import com.yash.Trading.platform.model.User;

import jakarta.transaction.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private PaymentOrderRepo repo;
	
	@Value("${stripe.api.key}")
	private String stripeSecretKey;
	
	@Value("${razorpay.api.key}")
	private String apikey;

	@Value("${razor.api.secret}")
	private String apiSecretKey;
	
	
	@Override
	public PaymentOrder createOrder(User user, Long amount, PaymentMethod pm) {
		// TODO Auto-generated method stub
		PaymentOrder po = new PaymentOrder();
		po.setUser(user);
		po.setAmount(amount);
		po.setPm(pm);
		po.setStatus(PaymentOrderStatus.PENDING);
		return repo.save(po);
	}

	@Override
	public PaymentOrder getPaymentOrderById(Long id) throws Exception{
		// TODO Auto-generated method stub
		return repo.findById(id).orElseThrow(()->new Exception("payment order not found"));
		
	}

	@Override
	@Transactional
	public Boolean proccedPaymentOrder(PaymentOrder po, String paymentId) throws RazorpayException {
		// TODO Auto-generated method stub
		if(po.getStatus()==null)
		{
			po.setStatus(PaymentOrderStatus.PENDING);
		}
		if(po.getStatus().equals(PaymentOrderStatus.PENDING))
		{
			if(po.getPm().equals(PaymentMethod.RAZORPAY))
			{
				RazorpayClient razorpay = new RazorpayClient(apikey,apiSecretKey);
				Payment pay  = razorpay.payments.fetch(paymentId);
				
				Integer amount = pay.get("amount");
				String status = pay.get("status");
				
				if(status.equals("captured")) {
					po.setStatus(PaymentOrderStatus.SUCCESS);
					return true;
				}
				repo.save(po);
				return false;
			}
			po.setStatus(PaymentOrderStatus.SUCCESS);
			repo.save(po);
			return true;
			
		}
		return false;
	}

	@Override
	public PaymentResponse createRazorpayPaymenting(User user, Long amount,Long orderId) throws RazorpayException {
		// TODO Auto-generated method stub
		Long Amount = amount*100;
		try {
			RazorpayClient razorpay = new RazorpayClient(apikey,apiSecretKey);
			
			JSONObject paymentLinkReq = new JSONObject();
			paymentLinkReq.put("amount", amount);
			paymentLinkReq.put("currency","INR");
			
			JSONObject customer = new JSONObject();
			customer.put("name", user.getFullname());
			
			customer.put("email",user.getEmail());
			paymentLinkReq.put("customer", customer);
			
			JSONObject notify = new JSONObject();
			notify.put("email", true);
			paymentLinkReq.put("notify", notify);
			
			paymentLinkReq.put("reminder_enable", true);
			
			paymentLinkReq.put("callback_url","http://localhost:8080/wallet?order_id="+orderId);
			paymentLinkReq.put("callback_method", "get");
			
			PaymentLink payment = razorpay.paymentLink.create(paymentLinkReq);
			
			String paymentLinkId = payment.get("id");
			String paymentLinkUrl = payment.get("short_url");
			
			PaymentResponse res = new PaymentResponse();
			res.setPayment_url(paymentLinkUrl);
			
			return res;
		}
		catch(RazorpayException e)
		{
			System.out.println("Error creating payment link: "+e.getMessage());
			throw new RazorpayException(e.getMessage());
		}
	}

	@Override
	public PaymentResponse createStrpePaymenting(User user, Long amount, PaymentOrder orderId) throws StripeException {
		// TODO Auto-generated method stub
		Stripe.apiKey = stripeSecretKey;
		
		SessionCreateParams params = SessionCreateParams.builder().addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl("http://localhost:8080/wallet?order_id="+orderId)
				.setCancelUrl("http://localhost:8080/payment/cancel")
				.addLineItem(SessionCreateParams.LineItem.builder()
					    .setQuantity(1L)
					    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
					        .setCurrency("usd")
					        .setUnitAmount(amount * 100)
					        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
					            .setName("Top up wallet")
					            .build()
					        ).build()
					    ).build()
					).build();

		
		Session session = Session.create(params);
		System.out.println("Session----- "+session);
		
		PaymentResponse res = new PaymentResponse();
		res.setPayment_url(session.getUrl());
		
		return res;
				
	}

	
}
