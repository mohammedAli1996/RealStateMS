package com.RealState.Management.RS.Notifications;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RealState.Management.RS.Contract.CommContract;
import com.RealState.Management.RS.Contract.CommContractRepository;
import com.RealState.Management.RS.Contract.ResContract;
import com.RealState.Management.RS.Contract.ResContractRepository;

@Service
public class NotificationsService {
	
	
	@Autowired
	private ResContractRepository resRepo ; 
	
	@Autowired
	private CommContractRepository commRepo ; 
	
	
	public List<Notifications> getNotifications(){
		List<ResContract> resContracts = resRepo.findAll() ; 
		List<CommContract> commContracts = commRepo.findAll() ; 
		List<Notifications> response = new ArrayList<Notifications>();
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		today = calendar.getTime() ; 
		System.out.println("today : "+today);
		for(ResContract contract : resContracts) {
			if(!contract.getStatus().equalsIgnoreCase("rented")) {
				continue ; 
			}
			try {
				long diffInMillies = Math.abs(today.getTime() - contract.getContractEndDate().getTime());
				 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				 if(diff == 45 ) {
					 Notifications notifications = new Notifications();
					 notifications.setContractId(contract.getId());
					 notifications.setPriority("L");
					 notifications.setPath("/contract/editres/"+contract.getId());
					 notifications.setMsg("Contract Expires in 45 days");
					 response.add(notifications);
				 }else if (diff == 30 ) {
					 Notifications notifications = new Notifications();
					 notifications.setContractId(contract.getId());
					 notifications.setPriority("M");
					 notifications.setPath("/contract/editres/"+contract.getId());
					 notifications.setMsg("Contract Expires in 30 days");
					 response.add(notifications);
				 }else if (diff == 1 ) {
					 Notifications notifications = new Notifications();
					 notifications.setContractId(contract.getId());
					 notifications.setPath("/contract/editres/"+contract.getId());
					 notifications.setPriority("H");
					 notifications.setMsg("Contract Expires in One day");
					 response.add(notifications);
				 }
			} catch(Exception ex ) {
				
			}
		}
		for(CommContract contract : commContracts) {
			if(!contract.getStatus().equalsIgnoreCase("rented")) {
				continue ; 
			}
			try {
				long diffInMillies = Math.abs(today.getTime() - contract.getContractEndDate().getTime());
				 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				 if(diff == 45 ) {
					 Notifications notifications = new Notifications();
					 notifications.setContractId(contract.getId());
					 notifications.setPriority("L");
					 notifications.setPath("/contract/editcomm/"+contract.getId());
					 notifications.setMsg("Contract Expires in 45 days");
					 response.add(notifications);
				 }else if (diff == 30 ) {
					 Notifications notifications = new Notifications();
					 notifications.setContractId(contract.getId());
					 notifications.setPriority("M");
					 notifications.setPath("/contract/editcomm/"+contract.getId());
					 notifications.setMsg("Contract Expires in 30 days");
					 response.add(notifications);
				 }else if (diff == 1 ) {
					 Notifications notifications = new Notifications();
					 notifications.setContractId(contract.getId());
					 notifications.setPriority("H");
					 notifications.setPath("/contract/editcomm/"+contract.getId());
					 notifications.setMsg("Contract Expires in One day");
					 response.add(notifications);
				 }
			} catch(Exception ex ) {
				
			}
		}
		return response ; 
	}

	
	public List<Notifications> getTestData(){
		List<Notifications> result = new ArrayList<Notifications>();
		Notifications n = new Notifications();
		n.setMsg("High");
		n.setPriority("H");
		n.setPath("/contract/editcomm/");
		result.add(n);
		Notifications n1 = new Notifications();
		n1.setMsg("Med");
		n1.setPriority("M");
		n1.setPath("/contract/editcomm/");
		result.add(n1);
		Notifications n2 = new Notifications();
		n2.setMsg("Low");
		n2.setPriority("L");
		n2.setPath("/contract/editcomm/");
		result.add(n2);
		return result ; 
	}
	
}
