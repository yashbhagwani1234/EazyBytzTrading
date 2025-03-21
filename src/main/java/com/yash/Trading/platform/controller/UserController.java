package com.yash.Trading.platform.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yash.Trading.platform.Response.ApiResponse;
import com.yash.Trading.platform.Response.AuthRespone;
import com.yash.Trading.platform.domain.VerficationType;
import com.yash.Trading.platform.model.ForgotPasswordToken;
import com.yash.Trading.platform.model.User;
import com.yash.Trading.platform.model.VerificationCode;
import com.yash.Trading.platform.req.ForgotPasswordTokenReq;
import com.yash.Trading.platform.req.ResetPasswordReq;
import com.yash.Trading.platform.service.EmailService;
import com.yash.Trading.platform.service.ForgotPasswordService;
import com.yash.Trading.platform.service.UserService;
import com.yash.Trading.platform.service.VerificationCodeService;
import com.yash.Trading.platform.util.OtpUtils;

@RestController
public class UserController {
	
	@Autowired
	private VerificationCodeService vfCodeService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@Autowired
	private EmailService emailService;
	private String jwt;
	
	@GetMapping("/api/users/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@PostMapping("/api/users/verification/{verificationType}/send-otp")
	public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt
			,@PathVariable VerficationType verificationType) throws Exception{
	
		User user = userService.findUserProfileByJwt(jwt);
		
		VerificationCode vfcode = vfCodeService.getVerificationCodeByUser(user.getId());
		
		if(vfcode==null)
		{
			vfcode=vfCodeService.sendVerificationCode(user, verificationType);
		}
		
		if(verificationType.equals(VerficationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), vfcode.getOtp());
		}
		
		return new ResponseEntity<>("verificationotp sent successfully",HttpStatus.OK);
	}
	
	@PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
	public ResponseEntity<User> enableTwoFactorAuthentication(
			@PathVariable String otp,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		VerificationCode verificationCode = vfCodeService.getVerificationCodeByUser(user.getId());
		
		String sendTo = verificationCode.getVerifivationtype().equals(VerficationType.EMAIL)?verificationCode.getEmail():verificationCode.getMobile();
		
		boolean isVerified = verificationCode.getOtp().equals(otp);
		
		if(isVerified)
		{
			User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerifivationtype(),sendTo,user);
			
			vfCodeService.deleteVerificationCodeById(verificationCode);
			
			return new ResponseEntity<>(updatedUser,HttpStatus.OK);
		}
        throw new Exception("wrong otp");
	}
	
	@PostMapping("/auth/users/reset-password/send-otp")
	public ResponseEntity<AuthRespone> sendforgotPasswordOtp(
		@RequestBody ForgotPasswordTokenReq req) throws Exception{
	
		User user = userService.findUserByEmail(req.getSendTo());;
		String otp = OtpUtils.generateOtp();
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		
		ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
		
		if(token==null)
		{
			token=forgotPasswordService.createToken(user, id, otp, req.getVfType(), req.getSendTo());
		}
		
		if(req.getVfType().equals(VerficationType.EMAIL))
		{
			emailService.sendVerificationOtpEmail(user.getEmail(),token.getOtp());
		}
		
		AuthRespone response = new AuthRespone();
		response.setSession(token.getId());
		response.setMessage("Password reset otp sent successfully");
		
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PatchMapping("/auth/users/reset-password/verify-otp")
	public ResponseEntity<ApiResponse> resetPassword(
			@RequestParam String id,
			@RequestBody ResetPasswordReq req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		
		ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
		
		boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());
		
		if(isVerified)
		{
			userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());
			ApiResponse res = new ApiResponse();
			res.setMessage("password update successfully");
			return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
		}
		throw new Exception("wrong otp");
	}
}
