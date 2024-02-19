package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.config.JwtProvider;
import com.ecom.exceptions.UserException;
import com.ecom.http.AuthResponse;
import com.ecom.http.LoginRequest;
import com.ecom.model.Cart;
import com.ecom.model.User;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;
import com.ecom.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private CartService cartService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws UserException{
		System.out.println("SignUp Handler ");
		User existingUser= userRepo.findByEmail(user.getEmail());
		if(existingUser!=null) throw new UserException("user already registered with this email");
	
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	user.setRole("USER");
	User savedUser=userRepo.save(user);
	Cart cart =cartService.createCart(savedUser);
	Authentication authentication =new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
	SecurityContextHolder.getContext().setAuthentication(authentication);
	String token = jwtProvider.generateToken(authentication);
	
	AuthResponse authRes = new AuthResponse(token, "Signup Sucessful");
	return new ResponseEntity<AuthResponse>(authRes,HttpStatus.CREATED);
}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> userLoginHandler(@RequestBody LoginRequest loginReq){
		
		System.out.println("SIGN IN HANDLER-:::::::::::::::::");
		Authentication authentication = authenticate(loginReq.getEmail(),loginReq.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authRes = new AuthResponse(token, "Signin Sucessful");
		return new ResponseEntity<AuthResponse>(authRes,HttpStatus.OK);
		}
	private Authentication authenticate(String email, String password) {
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
		if(userDetails==null)throw new BadCredentialsException("Incorrect Email");
		else if(!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("Incorrect Password");
		
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
}
 	
	