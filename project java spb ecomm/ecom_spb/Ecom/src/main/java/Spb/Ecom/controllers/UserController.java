package Spb.Ecom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Spb.Ecom.models.User;
import Spb.Ecom.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String func1() {
		return "Hello";
	}
	
	@PostMapping("/api/public/signup")
	public ResponseEntity<?> signup(@RequestBody User u) {
		return userService.signup(u);
	}
	
	@PostMapping("/api/public/login")
	public ResponseEntity<?> login(@RequestBody User u) throws Exception {
		System.out.println("up baby up we go!");
		return userService.login(u);
	}
	@GetMapping("/api/public/product/search")
	public ResponseEntity<?> search(@RequestParam("name") String name){
		return userService.search(name);
	}
	
	
}
