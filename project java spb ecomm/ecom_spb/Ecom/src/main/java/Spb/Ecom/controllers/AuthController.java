package Spb.Ecom.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Spb.Ecom.models.CartProducts;
import Spb.Ecom.models.Product;
import Spb.Ecom.security.JwtUtil;
import Spb.Ecom.service.AuthService;
import Spb.Ecom.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	JwtUtil jwtutil;
	
	@GetMapping("/consumer/{consumerId}/cart")
    public ResponseEntity<?> getCart(@PathVariable("consumerId") Integer consumerId){
		return authService.getCart(consumerId);
	}
	@PostMapping("/consumer/{consumerId}/cart")
    public ResponseEntity<?> postProduct(@PathVariable("consumerId") Integer consumerId, @RequestBody Product p ){
		return authService.postProduct(consumerId,p);
	}
	@PutMapping("/consumer/{consumerId}/cart")
    public ResponseEntity<?> postProduct(@PathVariable("consumerId") Integer consumerId, @RequestBody CartProducts p ){
		return authService.putProduct(consumerId,p);
	}
	@DeleteMapping("/consumer/{consumerId}/cart")
    public ResponseEntity<?> delProduct(@PathVariable("consumerId") Integer consumerId,  @RequestBody Product p  ){
		return authService.delProduct(consumerId,p);
	}
	
	@GetMapping("/seller/{sellerId}/product")
	public ResponseEntity<?> sellerProducts(@PathVariable("sellerId") Integer sellerId){
		
		System.out.println("inside get mapping");
		return authService.sellerProducts(sellerId);
	}
	@GetMapping("/seller/product/{productId}")
	public ResponseEntity<?> productProducts(@PathVariable("productId") Integer productId){
		return authService.productProducts(productId);
	}
	@PostMapping("/seller/product")
	public void postProduct(@RequestBody Product product, HttpServletRequest request,HttpServletResponse response) throws IOException{
		String auth=request.getHeader("Authorization");
		String username=null;
		String token=null;
		token=auth.substring(7);
		username=jwtutil.extractUserName(token);
		System.out.println("Ok inside controlller");
		authService.postProduct(product,username);
		Integer productId=product.getProductId();
		response.sendRedirect("/api/auth/seller/product/" + productId);
	}
	
	@PutMapping("/seller/product")
	public ResponseEntity<?> putProduct(@RequestBody Product product) {
		return authService.putProduct(product);
	}
	@DeleteMapping("/seller/product")
	public ResponseEntity<?> delProduct(@RequestBody Product product) {
		
		return authService.delProduct(product);
	}
}
