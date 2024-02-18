package com.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.config.JwtProvider;
import com.ecom.exceptions.UserException;
import com.ecom.model.User;
import com.ecom.repository.UserRepository;
@Service
public class UserServiceImplementaion implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtProvider jwtProvider;
	@Override
	public User findUserById(Long id) throws UserException {
		User user = userRepository.findById(id).orElseThrow(()->new UserException("User not found with id : "+id));
		return user;
	}

	@Override
	public User findUserByJwt(String Jwt) throws UserException {
		String email =jwtProvider.getEmailFromToken(Jwt);
		User user = userRepository.findByEmail(email);
		if(user==null) throw new UserException("User not found -FIND-BY-JWT");
		return user;
	}

}
