package com.RealState.Management.RS.Notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationsController {

	
	
	@Autowired
	NotificationsService notificationsService ; 
	
	@GetMapping("/Notifications/all")     
	public List<Notifications> getAllCommCotracts() { 
		return notificationsService.getNotifications();
	}
	
	
}
    