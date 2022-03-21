package com.RealState.Management.RS.Property.Attachmenets;

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
public class Attachment {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id ; 
	
	private String name ; 
	
	private String path ; 
	
	private String date ; 
	
	private String type ; 
	
	private String section ; 
	
	private String amount ; 
	
	private int contractId ; 
	
}
