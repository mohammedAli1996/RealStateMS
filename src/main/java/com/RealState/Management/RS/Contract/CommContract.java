package com.RealState.Management.RS.Contract;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Setter
@Getter
@Accessors(chain = true)
@Entity
public class CommContract {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id ; 
	
	private int propertyId ; 
	
	private String licenseNumber; 
	
	private String applicationNumber; 
	
	private Date contractStartDate ; 
	
	private Date contractEndDate ; 
	
	private String city ; 
	private String district ; 
	private String type ; 
	private String nameDesc ; 
	
	private String status ; 
	
	private boolean archived ;
	
}
