package com.RealState.Management.RS.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class MasterService {

	@Autowired
	protected UserRepository userRepo;
	
	
	public Usersys get_current_User() {
		String username ; 
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Object principal =  auth.getPrincipal();
	        if(principal instanceof UserDetails) {
	        	 username = ((UserDetails) principal).getUsername() ; 
		         for(Usersys user : this.userRepo.findAll()) {
		 			if(user.getUsername().equalsIgnoreCase(username)) {
		 				return user ; 
		 			}
		 		}
	        }
	         return null  ; 
    }
	
	
}
