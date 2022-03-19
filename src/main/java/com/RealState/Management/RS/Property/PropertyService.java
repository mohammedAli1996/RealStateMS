package com.RealState.Management.RS.Property;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RealState.Management.RS.ServiceException;
import com.RealState.Management.RS.Contract.CommContract;
import com.RealState.Management.RS.Contract.CommContractRepository;
import com.RealState.Management.RS.Contract.ResContract;
import com.RealState.Management.RS.Contract.ResContractRepository;

@Service
public class PropertyService {

	
	@Autowired
	private PropertyRepository propertyRepository ; 
	
	@Autowired
	private ResContractRepository resRepo ; 
	
	@Autowired
	private CommContractRepository commRepo ; 
	
	
	public boolean rentProperty(int id , Date start , Date end, boolean edit ) {
		Property property = getPropertyById(id);
		if(!edit &&  property.getStatus().equalsIgnoreCase("rented")) {
			return false ; 
		}
		property.setStatus("rented") ; 
		property.setRentStartDate(start);
		property.setRentEndDate(end);
		propertyRepository.save(property);
		return true ; 
	}

	public int addProperty(Property request ) {
		if(request.getId() != -1 ) {
			Property db = getPropertyById(request.getId()) ; 
			db.setArea(request.getArea())
			.setCertNumber(request.getCertNumber())
			.setCity(request.getCity())
			.setCompanyName(request.getCompanyName())
			.setDistrict(request.getDistrict())
			.setLeaseUnit(request.getLeaseUnit())
			.setLicensesNumber(request.getLicensesNumber())
			.setMobileNumber(request.getMobileNumber())
			.setNameDesc(request.getNameDesc())
			.setParking(request.isParking())
			.setPremisesNum(request.isPremisesNum())
			.setRooms(request.getRooms())
			.setSewerageNum(request.getSewerageNum())
			.setType(request.getType())
			.setWaterNum(request.getWaterNum());
			return propertyRepository.save(db).getId();
		}
		request.setStatus("Available");
		return propertyRepository.save(request).getId();	
	}
	
	public Property getPropertyById(int id ) {
		Optional<Property> optional = propertyRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new ServiceException("Property not found");
	}
	
	public int updateProperty(Property request ) {
		Property property = getPropertyById(request.getId());
		property
		.setCompanyName(request.getCompanyName())
		.setCity(request.getCity())
		.setCertNumber(request.getCertNumber())
		.setDistrict(request.getDistrict())
		.setLicensesNumber(request.getLicensesNumber())
		.setMobileNumber(request.getMobileNumber());
		return propertyRepository.save(property).getId();
	}
	
	public List<Property> getAllProperties(){
		return propertyRepository.findByArchivedFalse();
	}

	
	public int deletePorpertyById(int id ) {
		Property property = getPropertyById(id);
		property.setDeleted(true);
		return propertyRepository.save(property).getId();
	}
	

	public List<Property> filter(Filter filter) {
		List<Property> result = new ArrayList<Property>();
		for(Property unit :propertyRepository.findByArchivedFalse()) {  
			if(!check(filter.getTtt())) {
				if(!unit.getType().toLowerCase().contains(filter.getTtt().toLowerCase())) {
					continue;
				}
			}
			if(!check(filter.getOwnerName())) {
				if(!unit.getNameDesc().toLowerCase().contains(filter.getOwnerName().toLowerCase())) {
					continue;
				}
			}
			
			if(!check(filter.getSewegNum())) {
				if(!unit.getSewerageNum().toLowerCase().contains(filter.getSewegNum().toLowerCase())) {
					continue;
				}
			}
			if(!check(filter.getWaterElec())) {
				if(!unit.getWaterNum().toLowerCase().contains(filter.getWaterElec().toLowerCase())) {
					continue;
				}
			}
			if(!check(filter.getLeaseUnit())) {
				if(!unit.getLeaseUnit().toLowerCase().contains(filter.getLeaseUnit().toLowerCase())) {
					continue;
				}
			}
			result.add(unit);
		}
		
		
		
		return result;
	}
	
	private boolean check(String s ) {
		return s == null || s.equals("");
	}

	public void deletePropertyById(int id) {
		Property property = getPropertyById(id);
		property.setArchived(true);
		property.setStatus("Available");
		List<CommContract> allComm =commRepo.findByPropertyId(id);
		for(CommContract contract : allComm ) {
			contract .setArchived(true);
			contract.setStatus("Cancelled");
		}
		List<ResContract> allRes =resRepo.findByPropertyUnitId(id);
		for(ResContract con : allRes) {
			con.setArchived(true);
			con.setStatus("Cancelled");
		}
		propertyRepository.save(property); 
		resRepo.saveAll(allRes);
		commRepo.saveAll(allComm);
	}
	
	
}
