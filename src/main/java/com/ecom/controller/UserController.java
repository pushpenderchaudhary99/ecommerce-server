package com.ecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.exceptions.UserException;
import com.ecom.model.User;
import com.ecom.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	UserService userService;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		System.out.println("IN USER PROFILE HANDLER");
		logger.info("Fetching user from db using JWT token...");
		User user = userService.findUserByJwt(jwt);
		user.setPassword(null);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}

}
