package com.RealState.Management.RS.Property.Attachmenets;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Integer>{

	List<Attachment> findByContractIdAndType(int contractId , String type);
	
	
}
