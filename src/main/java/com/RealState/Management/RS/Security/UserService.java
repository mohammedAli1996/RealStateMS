package com.RealState.Management.RS.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.RealState.Management.RS.ServiceException;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository ; 
	
	@Autowired
	private MasterService masterService ;
	 
	
	private List<String> currSystemRoles = new ArrayList<String>();
	
	@PostConstruct
	private void initRolesList() {
		currSystemRoles.add("manager");
		currSystemRoles.add("sales");
		if(userRepository.countByUsername("SupportAccount") == 0 ) {
    		Usersys user = new Usersys();
    		user.setUsername("SupportAccount");
    		user.setPassword(new BCryptPasswordEncoder().encode("Support@Admin123"));
    		user.addRole("owner");
    		user.addPermission("owner");
    		user.setRepoId(10);
    		this.userRepository.save(user);
    	}  
	}
	
	
	
	 
	/*Getters*/
	
	public Usersys addUser(Usersys request ) {
		if(userRepository.countByUsername(request.getUsername()) > 0 ) {
			throw new ServiceException("User name should be unique ");
		}
		System.out.println(request.getUsername());
		System.out.println(request.getPassword());
		System.out.println(new BCryptPasswordEncoder().encode(request.getPassword()));
		request.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
		request.addRole("user");
		request.addPermission("user");
		request.setRepoId(10);
		return userRepository.save(request);
	}        
	
	public List<UserResponse> getAllUsers(boolean active){
		List<UserResponse> users = new ArrayList<UserResponse>();
		//Usersys curr = masterService.get_current_User() ; 
		for(Usersys user : userRepository.findAll()) {
//			if(user.getUserRoles().equals("owner") || user.getUserRoles().equals("manager")) {
//				continue ; 
//			}
			if(user.isActive() != active) {
				continue ;
			}
//			if("owner".equalsIgnoreCase(user.getUserRoles())) {
//				continue ; 
//			}
//			if(!curr.getUserRoles().equals("owner")) {
//				if(user.getRepoId() != curr.getRepoId()) {
//					continue ;
//				}
//			}
			UserResponse response = new UserResponse();
			response.setEmployeeName(user.getEmployeeName());
			response.setId(user.getId());
			response.setUserName(user.getUsername());
			response.setRole(user.getUserRoles());
			users.add(response);
		}
		return users ; 
	}
	
	private Usersys getUser(int id ) {
		Optional<Usersys> optional = this.userRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new ServiceException("لم يتم العثور على المستخدم");
	}

	
	
	/*Active state control*/
	
	public void activateUser(int userId ) {
		 Usersys user = getUser(userId);
		 user.setActive(true);
		 this.userRepository.save(user);
	}
	
	public void deActivateUser(int userId ) {
		 Usersys user = getUser(userId);
		 user.setActive(false);
		 this.userRepository.save(user);
	}
	
	
	public void deleteUsersWithRepoId(int repoId) {
		for(Usersys user : userRepository.findAll()) {
			if(user.getRepoId() == repoId ) {
				user.setActive(false);
				userRepository.save(user);
			}
		}
	}
	
	
	/*Sys roles*/
	
	public List<String> getCurrSystemRoles() {
		return currSystemRoles;
	}

	public void setCurrSystemRoles(List<String> currSystemRoles) {
		this.currSystemRoles = currSystemRoles;
	}
	
	/*Dash service*/
	
	public int getUsersCount(int repoId) {
		if(masterService.get_current_User().getUserRoles().equalsIgnoreCase("owner")
				|| masterService.get_current_User().getUserRoles().equalsIgnoreCase("admin")
				) {
			if(repoId == -1 ) {
				return (int)this.userRepository.count();
			}else {
				return this.userRepository.countByrepoId(masterService.get_current_User().getRepoId());
			}
		}
		return this.userRepository.countByrepoId(masterService.get_current_User().getRepoId());
	}
}
