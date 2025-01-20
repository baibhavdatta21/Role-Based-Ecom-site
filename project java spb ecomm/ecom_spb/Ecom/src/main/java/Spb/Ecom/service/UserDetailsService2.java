package Spb.Ecom.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Spb.Ecom.models.User;
import Spb.Ecom.models.UserPrincipal;
import Spb.Ecom.repository.UserRepository;


@Service
public class UserDetailsService2 implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u=userRepository.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("not found"));
		return new UserPrincipal(u);
	}
	
}
