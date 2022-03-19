package com.RealState.Management.RS.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	private UserRepository userRepository ; 
	
	public UserPrincipalDetailsService(UserRepository userRepository ){
		this.userRepository = userRepository ; 
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usersys user = this.userRepository.findByUsername(username) ;
		if(user == null ) {
			user = new Usersys();
		}else {
		}
		UserPrincipal userPrincipal = new UserPrincipal(user) ;  
		
		return userPrincipal;
	}
}
