package com.RealState.Management.RS.Property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.RealState.Management.RS.AjaxResponseBody;
import com.RealState.Management.RS.Contract.ContractService;



@RestController
public class PropertyController {

	
	@Autowired
	private PropertyService propertyService ; 
	  
	@Autowired
	private ContractService contractService ; 
	
	@GetMapping("/AddProperty")    
	public ModelAndView getAddProperty() {
		ModelAndView mav = new ModelAndView("Property/register");
		return mav ;   
	}
	        
	
	@GetMapping("/property/find")
	public ModelAndView getFindProperty() { 
		contractService.validateContractsDates();
		ModelAndView mav = new ModelAndView("Contract/search");
		return mav ; 
	}
	
	@PostMapping("/property/find")
	public List<Property> findProperty(@RequestBody Filter filter) {
		contractService.validateContractsDates();
		return propertyService.filter(filter);
	}
	
	
	@GetMapping("/property/edit/{id}")    
	public ModelAndView getEditProperty(@PathVariable int id ) {
		ModelAndView mav = new ModelAndView("Property/editProperty");
		mav.addObject("pId", id);
		return mav ;    
	}
	
	@GetMapping("/property/delete/{id}")    
	public ModelAndView deletePropertyById(@PathVariable int id ) {
		 AjaxResponseBody result = new AjaxResponseBody();
		 try {
			 propertyService.deletePropertyById(id);
			 result.setMsg("success");
			 result.setHolderID(id);
			 ModelAndView mav = new ModelAndView("Contract/search");
		     return mav ; 
			
		 }catch(Exception ex) {
			 ModelAndView mav = new ModelAndView("Contract/search");
		     return mav ; 
		 }
		
	  }
	                        
	
	
	
	@GetMapping("/property/get/{id}")
	public Property getPropertyById(@PathVariable int id ) {  
		return propertyService.getPropertyById(id);
	}
	   
	     
	@PostMapping("/property/add")
	public ResponseEntity<?> addProperty(@RequestBody Property property) {
		 AjaxResponseBody result = new AjaxResponseBody();
		 try {
			 int id = propertyService.addProperty(property);
			 result.setMsg("success");
			 result.setHolderID(id);
			 return ResponseEntity.ok(result);
		 }catch(Exception ex) {
			 result.setMsg(ex.getMessage());
			 return ResponseEntity.badRequest().body(result);
		 }
		
	  }
	
	@PutMapping("/property/add")
	public ResponseEntity<?> updateProperty(@RequestBody Property property) {
		 AjaxResponseBody result = new AjaxResponseBody();
		 try {
			 propertyService.updateProperty(property);
			 result.setMsg("success");
			 return ResponseEntity.ok(result);
		 }catch(Exception ex) {
			 result.setMsg(ex.getMessage());
			 return ResponseEntity.badRequest().body(result);
		 }
		
	  } 
	


	
	
	
}
