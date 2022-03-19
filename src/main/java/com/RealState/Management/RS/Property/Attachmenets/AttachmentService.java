package com.RealState.Management.RS.Property.Attachmenets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List; ; 

@Service
public class AttachmentService {

	
	@Autowired
	private AttachmentRepository attachmentRepository ; 
	
	
	public Attachment saveAttachment(String fileName , String path , int contractId , String type , String date, String section) {
		Attachment attachment = new Attachment()
				.setDate(date)
				.setName(fileName)
				.setPath(path)
				.setContractId(contractId)
				.setType(type)
				.setSection(section);
		return attachmentRepository.save(attachment);
	}
	  
	public List<Attachment> findByContractIdAndTypeAndSection(int contractId , String type ,String section){
		List<Attachment> result = new ArrayList<Attachment>();
		List<Attachment> att  = attachmentRepository.findAll();
		System.out.println(att.size());
		for(Attachment attachment : att) {
			if(attachment.getContractId() == contractId) {
				if(attachment.getType().equalsIgnoreCase(type)) {
					if(attachment.getSection().equalsIgnoreCase(section)) {
						result.add(attachment);
					}
				}
			}
		}
		return result;
	}
	
}
