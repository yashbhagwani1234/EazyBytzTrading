package com.yash.Trading.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yash.Trading.platform.Repo.UserRepository;
import com.yash.Trading.platform.Response.AuthRespone;
import com.yash.Trading.platform.config.JwtProvider;
import com.yash.Trading.platform.model.TwoFactorOtp;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.service.CustomerUserDetailsServices;
import com.yash.Trading.platform.service.EmailService;
import com.yash.Trading.platform.service.TwoFactorOtpService;
import com.yash.Trading.platform.service.WatchListService;
import com.yash.Trading.platform.util.OtpUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private CustomerUserDetailsServices customeUserDetailService;
	
	@Autowired
	private WatchListService service;
	
	@Autowired
	private TwoFactorOtpService twoFactorOtpService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthRespone> register(@RequestBody User user) throws Exception{
	
		User isEmailExit = repo.findByEmail(user.getEmail());
		
		if(isEmailExit!=null) {
			throw new Exception("Email is already exits used with another account");
		}
		
		User newUser = new User();
		newUser.setFullname(user.getFullname());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		
		User savedUser = repo.save(newUser);
		
		service.createWatchList(savedUser);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt = JwtProvider.generateToken(auth);
		
		AuthRespone res = new AuthRespone();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("register success");
		
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	@PostMapping("/signin")
	public ResponseEntity<AuthRespone> login(@RequestBody User user) throws Exception{
			
		String username = user.getEmail();
		String password = user.getPassword();
		
		Authentication auth = authenticate(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt = JwtProvider.generateToken(auth);
		
		User authUser = repo.findByEmail(username);
		
		
		if(user.getTwofactorauth().isEnabled())
		{
			AuthRespone res = new AuthRespone();
			res.setMessage("Two Factor auth is enabled");
			res.setTwoFactorAuthEnabled(true);
			
			String otp = OtpUtils.generateOtp();
			
			TwoFactorOtp oldTwoFactorOtp= twoFactorOtpService.findByUser(authUser.getId());
			if(oldTwoFactorOtp!=null)
			{
				twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
			}
			TwoFactorOtp newTwoFactorOtp = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);
			
			emailService.sendVerificationOtpEmail(username,otp);
			
			res.setSession(newTwoFactorOtp.getId());
			return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
		}
		
		AuthRespone res = new AuthRespone();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("Login success");
		
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	private Authentication authenticate(String username, String password) {
		// TODO Auto-generated method stub
	    UserDetails userDetails = customeUserDetailService.loadUserByUsername(username);
	    if(userDetails==null)
	    {
	    	throw new BadCredentialsException("Invalid username");
	    }
	    if(!password.equals(userDetails.getPassword()))
	    {
	    	throw new BadCredentialsException("invalid password");
	    }
		return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
	}
	
	@PostMapping("/two-factor/otp/{otp}")
	public ResponseEntity<AuthRespone> verfiySigninOtp(@PathVariable String otp,@RequestParam String id) throws Exception
	{
		TwoFactorOtp tfotp = twoFactorOtpService.findById(id);
		
		if(twoFactorOtpService.verifyTwoFactorOtp(tfotp, otp)) {
			AuthRespone res = new AuthRespone();
			res.setMessage("Two factor authentication verified");
			res.setTwoFactorAuthEnabled(true);
			res.setJwt(tfotp.getJwt());
			return new ResponseEntity<>(res,HttpStatus.OK);
		}
	    throw new Exception("invalid otp");
	 
	}
}
