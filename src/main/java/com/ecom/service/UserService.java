package com.ecom.service;

import com.ecom.exceptions.UserException;
import com.ecom.model.User;

public interface UserService {
	public User findUserById(Long id)throws UserException;
	public User findUserByJwt(String Jwt)throws UserException;

}
