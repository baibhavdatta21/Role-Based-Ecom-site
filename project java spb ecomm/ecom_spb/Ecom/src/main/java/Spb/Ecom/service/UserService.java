package Spb.Ecom.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Spb.Ecom.models.Cart;
import Spb.Ecom.models.Category;
import Spb.Ecom.models.Product;
import Spb.Ecom.models.Role;
import Spb.Ecom.models.User;
import Spb.Ecom.models.UserPrincipal;
import Spb.Ecom.repository.ProductRepository;
import Spb.Ecom.repository.RoleRepository;
import Spb.Ecom.repository.UserRepository;
import Spb.Ecom.security.JwtUtil;
import Spb.Ecom.utils.KMPAlgorithm;

@Service
public class UserService {

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	RoleRepository roleRepository;
	
	@Autowired 
	PasswordEncoder passwordEncoder;
	
	@Autowired 
	ProductRepository productRepository;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired 
	UserDetailsService2 userDetailsService; 
	
	public ResponseEntity<?> signup(User u) {
		
		Optional<User> u1=userRepository.findByUserName(u.getUserName());
		
		if(u1.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists for"+u.getUserName()+" Username" );
		}
		
		//double role
		List<Role> lst=u.getRole();
		if(lst.size()==2) {
			Optional<Role> r11=roleRepository.findById(1);
			Optional<Role> r22=roleRepository.findById(2);
			List<Role> lst12=new ArrayList<Role>();
			lst12.add(r11.get());
			lst12.add(r22.get());
			u.setRole(lst12);
			u.setUserPassword(passwordEncoder.encode(u.getUserPassword()));
			Cart cart=new Cart();
			u.setCart(cart);
			cart.setUser(u);
			userRepository.save(u);
			return ResponseEntity.status(HttpStatus.CREATED).body("Successfully signed up As a Consumer and a Seller");
			
		}
		//role with consumer
		if(lst.get(0).getRole().equals("Consumer")) {

			Optional<Role> r1=roleRepository.findById(1);
			u.setRole(List.of(r1.get()));
			u.setUserPassword(passwordEncoder.encode(u.getUserPassword()));
			Cart cart=new Cart();
			u.setCart(cart);
			cart.setUser(u);
			userRepository.save(u);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Successfully signed up consumer");
		}
		//role with seller
		Optional<Role> r2=roleRepository.findById(2);
		u.setRole(List.of(r2.get()));
		u.setUserPassword(passwordEncoder.encode(u.getUserPassword()));
		userRepository.save(u);
		return ResponseEntity.status(HttpStatus.CREATED).body("Successfully signed up Seller");
	}
	
	public ResponseEntity<?> login(User u1) throws Exception{
		try {
			User u=userRepository.findByUserName(u1.getUserName()).orElseThrow(()->new BadCredentialsException("ok"));
			UserPrincipal up= new UserPrincipal(u);
	        Authentication man = authManager.authenticate(new UsernamePasswordAuthenticationToken(up.getUsername(),u1.getUserPassword(),up.getAuthorities()));

	        if (man.isAuthenticated()) {
	            return ResponseEntity.status(HttpStatus.OK).body(jwtUtil.generateToken(up));
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please enter the correct details");
	        }
	    }
		catch (BadCredentialsException e) {
	        // Handle failed authentication explicitly
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	    } 
		catch (AuthenticationCredentialsNotFoundException e) {
	        // Handle other authentication-related errors
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
	    }
	}

	public ResponseEntity<?> search(String name) {
		
		List<Product>lst= productRepository.findAll();
		Set<Product> ans = new HashSet<>();
		for(int i=0;i<lst.size();i++) {
			Product dum=lst.get(i);
			String prodName=dum.getProductName();
			if (KMPAlgorithm.kmpSearch(prodName.toLowerCase(), name.toLowerCase()) != -1) {
	            ans.add(dum);
	        }
			Category cat=lst.get(i).getCategory();
			String catName=cat.getCategoryName();
			if (KMPAlgorithm.kmpSearch(catName.toLowerCase(), name.toLowerCase()) != -1) {
	            ans.add(dum);
	        }	
		}
		if(ans.size()==0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sorry, no such products found!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(ans);
	}
}
