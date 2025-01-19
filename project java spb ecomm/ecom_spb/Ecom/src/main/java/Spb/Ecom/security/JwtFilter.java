package Spb.Ecom.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	JwtUtil jwtutil;

	@Autowired 
	UserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String auth=request.getHeader("Authorization");
		String username=null;
		String token=null;
		System.out.println("Inside filter");
		System.out.println(auth);
		if(auth!=null && auth.startsWith("Bearer ")) {
			token=auth.substring(7);
			username=jwtutil.extractUserName(token);
		}
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails u= userDetailsService.loadUserByUsername(username);
				
				if(jwtutil.validateToken(token,u)) {
					System.out.println("token validated");
					UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(u,u.getPassword(),u.getAuthorities());
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					System.out.println("Filter work done");
				}
			}
		filterChain.doFilter(request,response);
	}
}
