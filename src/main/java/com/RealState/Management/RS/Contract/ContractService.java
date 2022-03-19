package com.RealState.Management.RS.Contract;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RealState.Management.RS.ServiceException;
import com.RealState.Management.RS.Property.Property;
import com.RealState.Management.RS.Property.PropertyRepository;
import com.RealState.Management.RS.Property.PropertyService;

@Service
public class ContractService {
	
	
	@Autowired
	private ResContractRepository resRepo ; 
	
	@Autowired
	private PropertyService propertyService ; 
	
	@Autowired
	private CommContractRepository commRepo ; 
	
	@Autowired
	private PropertyRepository propertyRepository ; 

	
	public void cancelCommContract(int id ) {
		CommContract contract = getCommContractById(id);
		Property property = propertyService.getPropertyById(contract.getPropertyId());
		contract.setStatus("Cancelled");
		property.setStatus("Available");
		commRepo.save(contract);
		propertyRepository.save(property);
	}
	 
	public void cancelResCotract(int id ) {
		ResContract contract = getResContractById(id);
		Property property = propertyService.getPropertyById(contract.getPropertyUnitId());
		contract.setStatus("Cancelled");
		property.setStatus("Available");
		resRepo.save(contract);
		propertyRepository.save(property);
	}
	
	public int saveResContract(ResContract request) {
		if(request.getId() != -1 ) {
			//update
			if(!propertyService.rentProperty(request.getPropertyUnitId(), request.getContractStartDate(), request.getContractEndDate(),true)) {
				throw new ServiceException("Property already rented ");
			}
			ResContract db = getResContractById(request.getId());
			db.setEmiratesId(request.getEmiratesId())
			.setPassportNum(request.getPassportNum())
			.setNameContract(request.getNameContract())
			.setNationality(request.getNationality())
			.setMobileNum(request.getMobileNum())
			.setEmailContract(request.getEmailContract())
			.setContractStartDate(request.getContractStartDate())
			.setContractEndDate(request.getContractEndDate())
			;
			if(db.getContractEndDate().after(new Date())) {
				db.setStatus("Rented");
			}else {
				db.setStatus("Expired");
			}
			return resRepo.save(db).getId();
		}
		if(!propertyService.rentProperty(request.getPropertyUnitId(), request.getContractStartDate(), request.getContractEndDate(),false)) {
			throw new ServiceException("Property already rented ");
		}
		if(request.getContractEndDate().after(new Date())) {
			request.setStatus("Rented");
		}else {
			request.setStatus("Expired");
		}
		return resRepo.save(request).getId() ; 
	}
	
	public ResContract getResContractById(int id ) {
		Optional<ResContract> optional = resRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null ; 
		}
	}
	
	public List<ResContract> getAllResContracts(){
		
		 List<ResContract> contracts = resRepo.findByArchivedFalse();
		 for(ResContract contract : contracts ) {
			 Property property = propertyService.getPropertyById(contract.getPropertyUnitId());
			 contract.setCity(property.getCity()); 
			 contract.setDistrict(property.getDistrict()); 
			 contract.setType(property.getType()) ;  
			 contract.setNameDesc(property.getNameDesc());
		 }
		 for(ResContract contract : contracts) {
			 if(contract.getStatus().equalsIgnoreCase("Rented")) {
				 if(contract.getContractEndDate().before(new Date())) {
					 contract.setStatus("Expired");
					 resRepo.save(contract);
				 }
			 }
		 }
		return contracts;
	}         
	   
	      
	public int saveCommContract(CommContract request) {
		if(request.getPropertyId() != -1 ) {
			if(!propertyService.rentProperty(request.getPropertyId(), request.getContractStartDate(), request.getContractEndDate(),true)) {
				throw new ServiceException("Property already rented ");
			}
		}else {
			if(!propertyService.rentProperty(request.getPropertyId(), request.getContractStartDate(), request.getContractEndDate(),false)) {
				throw new ServiceException("Property already rented ");
			}
		}
		if(request.getContractEndDate().after(new Date())) {
			request.setStatus("Rented");  
		}else {
			request.setStatus("Expired");
		}
		return commRepo.save(request).getId() ; 
	}
	
	public CommContract getCommContractById(int id ) {
		Optional<CommContract> optional = commRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null ; 
		}
	}
	
	public List<CommContract> getAllCommContracts(){
		 List<CommContract> contracts =  commRepo.findByArchivedFalse();
		 for(CommContract contract : contracts ) {
			 Property property = propertyService.getPropertyById(contract.getPropertyId());
			 contract.setCity(property.getCity()); 
			 contract.setDistrict(property.getDistrict()); 
			 contract.setType(property.getType()) ;  
			 contract.setNameDesc(property.getNameDesc());
		 }
		 for(CommContract contract : contracts) {
			 if(contract.getStatus().equalsIgnoreCase("Rented")) {
				 if(contract.getContractEndDate().before(new Date())) {
					 contract.setStatus("Expired");
					 commRepo.save(contract);
				 }
			 }
		 }
		 return contracts ; 
	}      
	
	
	
	public DashModel getCommDash(){
		DashModel response = new DashModel();
		response.setTotal(commRepo.findAll().size()); 
		int c = 0 , a = 0 , e = 0 ; 
		for(CommContract contract : commRepo.findAll() ) {
			if(contract.getStatus().equalsIgnoreCase("Cancelled")) {
				c ++ ; 
			}
			else if(contract.getContractEndDate().after(new Date())) {
				a++;
			}else {
				e++;
			}
		}
		response.setCountCanceled(c);
		response.setCount(a);
		response.setCountExpired(e);
		return response ; 
	}
	
	
	public DashModel getResDash(){ 
		DashModel response = new DashModel();
		response.setTotal(resRepo.findAll().size()); 
		int c = 0 , a = 0 , e = 0 ; 
		for(ResContract contract : resRepo.findAll() ) {
			if(contract.getStatus().equalsIgnoreCase("Cancelled")) {
				c ++ ; 
			}
			else if(contract.getContractEndDate().after(new Date())) {
				a++;
			}else {
				e++;
			}
		}
		response.setCountCanceled(c);
		response.setCount(a);
		response.setCountExpired(e);
		return response ; 
	}

	public void validateContractsDates() {
		for(ResContract contract : resRepo.findAll()) {
			if(contract.getStatus().equalsIgnoreCase("Rented")) {
				if(contract.getContractEndDate().before(new Date())) {
					contract.setStatus("Expired");
					Property property = propertyService.getPropertyById(contract.getPropertyUnitId());
					property.setStatus("Available");
					propertyRepository.save(property);
					resRepo.save(contract);
				}
			}
			else if(contract.getStatus().equalsIgnoreCase("Expired")) {
				Property property = propertyService.getPropertyById(contract.getPropertyUnitId());
				property.setStatus("Available");
				propertyRepository.save(property);
			}
		}
		for(CommContract contract : commRepo.findAll()) {
			if(contract.getStatus().equalsIgnoreCase("Rented")) {
				if(contract.getContractEndDate().before(new Date())) {
					contract.setStatus("Expired");
					Property property = propertyService.getPropertyById(contract.getPropertyId());
					property.setStatus("Available");
					propertyRepository.save(property);
					commRepo.save(contract);
				}
			}
			else if(contract.getStatus().equalsIgnoreCase("Expired")) {
				Property property = propertyService.getPropertyById(contract.getPropertyId());
				property.setStatus("Available");
				propertyRepository.save(property);
			}
		}
	}

}
