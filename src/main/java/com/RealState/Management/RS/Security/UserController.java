package com.RealState.Management.RS.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired 
	private UserService userService ; 
	  
	
	@GetMapping("/addUser")
	public ModelAndView getAddUser() {
		ModelAndView mav = new ModelAndView("createUser");
		mav.addObject("user", new Usersys());  
		return mav ; 
	}      
	  
	@PostMapping("/addUser")
	public ModelAndView addUser(@RequestBody Usersys request ) {
		System.out.println(request.getUsername());
		System.out.println(request.getPassword());
		userService.addUser(request);
		
		return getAllActiveUsers();
	}
	
	@GetMapping("/active/{id}")
	public ModelAndView activateUser(@PathVariable int id ) {
		userService.activateUser(id); 
		return getAllNonActiveUsers();
	}
	
	@GetMapping("/deactive/{id}")
	public ModelAndView deActivateUser(@PathVariable int id ) {
		userService.deActivateUser(id); 
		return getAllActiveUsers();
	} 
	   
	
	@GetMapping("/all")
	public ModelAndView getAllActiveUsers(){
		ModelAndView mav = new ModelAndView("allActiveUsers");
		mav.addObject("users", userService.getAllUsers(true));
		return mav;
	}   
	
	@GetMapping("/allActive")
	public List<UserResponse> getActiveUSesList(){
		return userService.getAllUsers(true) ;
	}  
	  
	@GetMapping("/allNonActive")
	public List<UserResponse> getNonActiveUsersList(){
		return userService.getAllUsers(false) ;
	}      
	  
	@GetMapping("/NA/all")
	public ModelAndView getAllNonActiveUsers(){
		ModelAndView mav = new ModelAndView("allNonActiveUsers");
		mav.addObject("users", userService.getAllUsers(false));
		return mav;
	}
	
}
