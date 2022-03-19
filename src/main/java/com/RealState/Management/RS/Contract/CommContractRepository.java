package com.RealState.Management.RS.Contract;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommContractRepository extends JpaRepository<CommContract,Integer>{

	List<CommContract> findByPropertyId(int propertyId); 
	List<CommContract> findByArchivedFalse();
}
