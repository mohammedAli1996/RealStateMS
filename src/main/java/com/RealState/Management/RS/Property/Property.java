package com.RealState.Management.RS.Property;

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
public class Property {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id ; 
	
	private String licensesNumber ; 
	
	private String companyName ; 
	
	private String mobileNumber ; 
	
	private String certNumber ; 
	
	private String city ; 
	
	private String district ; 
	
	private String type ; 
	
	private String nameDesc ; 
	
	private String leaseUnit ; 
	
	private String area ; 
	
	private String rooms ; 
	
	private String waterNum ; 
	 
	private String sewerageNum ; 
	
	private String empNumber ; 
	  
	private boolean parking ; 
	
	private boolean premisesNum ; 
	
	private boolean deleted ; 
	private boolean archived ;
	
	private String status ; 
	
	private Date rentStartDate ; 
	
	private Date rentEndDate ; 
	
	
}
