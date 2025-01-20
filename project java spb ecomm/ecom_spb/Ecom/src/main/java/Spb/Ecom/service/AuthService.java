package Spb.Ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Spb.Ecom.models.Cart;
import Spb.Ecom.models.CartProducts;
import Spb.Ecom.models.Category;
import Spb.Ecom.models.Product;
import Spb.Ecom.models.User;
import Spb.Ecom.repository.CartProductsRepository;
import Spb.Ecom.repository.CartRepository;
import Spb.Ecom.repository.CategoryRepository;
import Spb.Ecom.repository.ProductRepository;
import Spb.Ecom.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartProductsRepository cartProductsRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	public ResponseEntity<?> getCart(Integer consumerId) {
		Optional<User> user=userRepository.findById(consumerId);
		if(user.isPresent()) {
			List<CartProducts> lst=user.get().getCart().getCartProducts();
			Double amount=0.0;
			for(int i=0;i<lst.size();i++) {
				amount+=lst.get(i).getQuantity()*lst.get(i).getProduct().getPrice();
			}
			Cart cart=user.get().getCart();
			cart.setTotalAmount(amount);
			cartRepository.save(cart);
			return ResponseEntity.status(HttpStatus.OK).body(cartRepository.findById(user.get().getCart().getCartId()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("doesn't exist");
	}

	public ResponseEntity<?> postProduct(Integer consumerId, Product p) {
		Optional<User> user=userRepository.findById(consumerId);
		Optional<Product> prd=productRepository.findById(p.getProductId());

		if(user.isPresent()) {
			CartProducts c=new CartProducts();
			c.setProduct(prd.get());
			c.setQuantity(1);
			c.setCart(user.get().getCart());
			Double amount=c.getCart().getTotalAmount();
			amount+=prd.get().getPrice();
			c.getCart().setTotalAmount(amount);
			return ResponseEntity.status(HttpStatus.OK).body(cartProductsRepository.save(c));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error");
	}

	public ResponseEntity<?> putProduct(Integer consumerId, CartProducts p) {
		Optional<User> user=userRepository.findById(consumerId);
		if(user.isPresent()) {
			Optional<CartProducts> cp=cartProductsRepository.findById(p.getCpId());
			if(p.getQuantity()==0) {
				Cart c=cp.get().getCart();
				//Double amount=c.getTotalAmount();
				//amount-=(cp.get().getQuantity()*cp.get().getProduct().getPrice());
				//c.setTotalAmount(amount);
				//cartRepository.save(c);
				cartProductsRepository.delete(cp.get());
			}
			else {
				//Cart c=cp.get().getCart();
				//Double amount=c.getTotalAmount();
				//amount-=(cp.get().getQuantity()*cp.get().getProduct().getPrice());
				cp.get().setQuantity(p.getQuantity());
				//amount+=(cp.get().getQuantity())*(cp.get().getProduct().getPrice());
				//c.setTotalAmount(amount);
				//cartRepository.save(c);
				cartProductsRepository.save(cp.get());
			}
			return ResponseEntity.status(HttpStatus.OK).body(cartProductsRepository.save(cp.get()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error");
	}

	public ResponseEntity<?> delProduct(Integer consumerId, Product p) {
		Optional<User> user=userRepository.findById(consumerId);
		Cart cart=user.get().getCart();
		List<CartProducts> lst= cart.getCartProducts();
		for(int i=0;i<lst.size();i++) {
			if(lst.get(i).getProduct().getProductId()==p.getProductId()) {
				//Cart c=lst.get(i).getCart();
				//Double amount=c.getTotalAmount();
				//amount-=(lst.get(i).getQuantity()*lst.get(i).getProduct().getPrice());
				cartProductsRepository.deleteById(lst.get(i).getCpId());
				//cartRepository.save(c);
				return ResponseEntity.status(HttpStatus.OK).body("deleted");
			}
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error");
	}

	public ResponseEntity<?> sellerProducts(Integer sellerId) {
		Optional<User> user=userRepository.findById(sellerId);
		return ResponseEntity.status(HttpStatus.OK).body(user.get().getProduct());
	}

	public ResponseEntity<?> productProducts(Integer productId) {
		Optional<Product> p=productRepository.findById(productId);
		if(p.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(p.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error");
	}

	public void postProduct(Product product, String username) {
		Optional<User> u=userRepository.findByUserName(username);
		Optional<Category> c=categoryRepository.findByCategoryName(product.getCategory().getCategoryName());
		if(c.isPresent()) {
			product.setSeller(u.get());
			product.setCategory(c.get());
			productRepository.save(product);	
		}
		else {
			Category cat=new Category(product.getCategory().getCategoryName());
			categoryRepository.save(cat);
			product.setSeller(u.get());
			product.setCategory(cat);
			productRepository.save(product);	
		}
			
	}

	public ResponseEntity<?> putProduct(Product product) {
		Integer productId=product.getProductId();
		Optional<Product> p1=productRepository.findById(productId);
		p1.get().setProductName(product.getProductName());
		p1.get().setPrice(product.getPrice());
		p1.get().setCategory(product.getCategory());
		p1.get().setSeller(product.getSeller());
		 return ResponseEntity.status(HttpStatus.OK).body(p1.get());
	}

	public ResponseEntity<?> delProduct(Product product) {
		// TODO Auto-generated method stub
		Integer productId=product.getProductId();
		Optional<Product> p1=productRepository.findById(productId);
		productRepository.delete(p1.get());
		return ResponseEntity.status(HttpStatus.OK).body(p1.get());
	}
	
	

}
