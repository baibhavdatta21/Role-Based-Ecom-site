package Spb.Ecom;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import Spb.Ecom.models.Cart;
import Spb.Ecom.models.CartProducts;
import Spb.Ecom.models.Category;
import Spb.Ecom.models.Product;
import Spb.Ecom.models.Role;
import Spb.Ecom.models.User;
import Spb.Ecom.repository.CartProductsRepository;
import Spb.Ecom.repository.CartRepository;
import Spb.Ecom.repository.CategoryRepository;
import Spb.Ecom.repository.ProductRepository;
import Spb.Ecom.repository.RoleRepository;
import Spb.Ecom.repository.UserRepository;


@Component
public class DataInitializer implements CommandLineRunner {
	
	@Autowired 
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	CartProductsRepository cartProductsRepository;
	
	@Autowired 
	PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {
		Category category1=new Category("Fashion");
		Category category2=new Category("Electronics");
		Category category3=new Category("Books");
		Category category4=new Category("Groceries");
		Category category5=new Category("Medicines");
		categoryRepository.save(category1);
		categoryRepository.save(category2);
		categoryRepository.save(category3);
		categoryRepository.save(category4);
		categoryRepository.save(category5);
		
		Role role1=new Role("Consumer");
		Role role2=new Role("Seller");
		roleRepository.save(role1);
		roleRepository.save(role2);
		
		List<Role> lst_consumer=new ArrayList<Role>();
		lst_consumer.add(role1);
		List<Role> lst_seller=new ArrayList<Role>();
		lst_seller.add(role2);
		List<Role> lst_all=new ArrayList<Role>();
		lst_all.add(role1);
		lst_all.add(role2);
		
		Cart cart1=new Cart();
		Cart cart2=new Cart();
		Cart cart3=new Cart();
		User user1= new User("A",passwordEncoder.encode("123"),lst_consumer);
		User user2= new User("B",passwordEncoder.encode("123"),lst_seller);
		User user3= new User("C",passwordEncoder.encode("123"),lst_all);
		cart1.setUser(user1);
		cart2.setUser(user2);
		cart3.setUser(user3);
		cart1.setTotalAmount(20.0);
		user1.setCart(cart1);
		user2.setCart(cart2);
		user3.setCart(cart3);
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		
		Product product1= new Product("Apple iPad 10.2 8th Gen WiFi iOS Tablet",29190.00,category2,user3);
		Product product2=new Product("Crocin pain relief tablet",10.0,category5,user3);
		productRepository.save(product1);
		productRepository.save(product2);
		
		
		CartProducts cartProducts1=new CartProducts(2,cart1,product2);
		cart1.setCartProducts(List.of(cartProducts1));
		cartProductsRepository.save(cartProducts1);
//		productRepository.delete(product2);
	}

}
