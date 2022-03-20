package com.RealState.Management.RS.Contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.RealState.Management.RS.AjaxResponseBody;
import com.RealState.Management.RS.Property.Attachmenets.Attachment;
import com.RealState.Management.RS.Property.Attachmenets.AttachmentService;

@RestController
public class ContractController { 

	@Autowired
	private ContractService contractService ; 
	
	
	@Autowired
	private AttachmentService attachmentService ; 
	
	@GetMapping("/AddContract") 
	public ModelAndView getAddResContract() { 
		ModelAndView mav = new ModelAndView("Contract/contract");
		return mav ;  
	} 
	
	@GetMapping("/contract/editres/{contractId}") 
	public ModelAndView getEditResContract(@PathVariable int contractId) { 
		ModelAndView mav = new ModelAndView("Contract/editResContract");
		mav.addObject("id", contractId);
		return mav ;  
	} 
	  
	@GetMapping("/contract/getById/{id}")  
	public ResContract getAllResContractView(@PathVariable int id) {   
		return contractService.getResContractById(id);
	} 
	   
	@GetMapping("/contract/view/res")  
	public ModelAndView getAllResContractView() {   
		ModelAndView mav = new ModelAndView("Contract/rsdncnt");  
		return mav ;  
	} 

	@GetMapping("/contract/all/res")   
	public List<ResContract> getAllResContracts() { 
		return contractService.getAllResContracts();
	}
	
	
	
	  
	@PostMapping("/contract/add/res")
	public ResponseEntity<?> addResContract(@RequestBody ResContract request) {
		 AjaxResponseBody result = new AjaxResponseBody();
		 try {
			 int id = contractService.saveResContract(request);
			 result.setMsg("success");
			 result.setHolderID(id);
			 return ResponseEntity.ok(result);
		 }catch(Exception ex) {
			 result.setMsg(ex.getMessage());
			 return ResponseEntity.badRequest().body(result);
		 }  
		   
	  }     
	 	 
	 
	@GetMapping("/AddCommercialContract") 
	public ModelAndView getAddCommContract() { 
		ModelAndView mav = new ModelAndView("Contract/CommercialContract"); 
		return mav ; 
	}
	  
	
	@PostMapping("/contract/add/comm")
	public ResponseEntity<?> addCommContract(@RequestBody CommContract request) {
		 AjaxResponseBody result = new AjaxResponseBody();
		 try {
			 int id = contractService.saveCommContract(request);
			 result.setMsg("success"); 
			 result.setHolderID(id);
			 return ResponseEntity.ok(result);
		 }catch(Exception ex) {
			 result.setMsg(ex.getMessage());
			 return ResponseEntity.badRequest().body(result);
		 }  
		  
	  }    
	
	
	@GetMapping("/contract/view/comm")  
	public ModelAndView getAllCommContracts() {   
		ModelAndView mav = new ModelAndView("Contract/commContractView");  
		return mav ;  
	} 
	  
	@GetMapping("/contract/all/comm")   
	public List<CommContract> getAllCommCotracts() { 
		return contractService.getAllCommContracts();
	}
	
	@GetMapping("/contract/attachments/{contractId}/{type}/{section}")
	public List<Attachment> getContractAttachments(@PathVariable int contractId , @PathVariable String type , @PathVariable String section ){
		return attachmentService.findByContractIdAndTypeAndSection(contractId, type , section) ; 
	}
	
	
	@GetMapping("/contract/editcomm/{contractId}") 
	public ModelAndView getEditCommContract(@PathVariable int contractId) { 
		ModelAndView mav = new ModelAndView("Contract/editCommContract");
		mav.addObject("id", contractId);
		return mav ;    
	} 
	  
	@GetMapping("/contract/getCommById/{id}")  
	public CommContract getCommContractById(@PathVariable int id) {   
		return contractService.getCommContractById(id);
	} 
	
	
	
	@GetMapping("/cnclContract/res/{cId}")  
	public ModelAndView cancelResContract(@PathVariable int cId) {   
		  contractService.cancelResCotract(cId);
		return getIndex() ; 
	} 
	
	@GetMapping("/cnclContract/comm/{cId}")  
	public ModelAndView cancelCommContract(@PathVariable int cId) {   
		contractService.cancelCommContract(cId);
		return getIndex() ;  
	} 
	  
	
	@GetMapping("/index")
    public ModelAndView getIndex() {
    	ModelAndView mav = new ModelAndView("dashbaord");
    	return mav; 
    }
	
	
	
	//DashBoard
	@GetMapping("/ContractsDash/res") 
	public DashModel getResDash() {
		return contractService.getResDash();
	}
	@GetMapping("/ContractsDash/comm")
	public DashModel getCommDash() {
		return contractService.getCommDash();
	}
	
}
