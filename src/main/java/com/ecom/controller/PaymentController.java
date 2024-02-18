package com.ecom.controller;

//import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ecom.exceptions.OrderException;
import com.ecom.exceptions.UserException;
import com.ecom.model.Order;
import com.ecom.model.User;
import com.ecom.repository.OrderRepository;
import com.ecom.http.ApiResponse;
import com.ecom.http.PaymentLinkResponse;
import com.ecom.service.OrderService;
import com.ecom.service.UserService;
import com.ecom.user.domain.OrderStatus;
import com.ecom.user.domain.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class PaymentController {
	
}
