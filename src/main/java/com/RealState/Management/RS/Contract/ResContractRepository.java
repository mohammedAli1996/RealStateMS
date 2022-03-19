package com.RealState.Management.RS.Contract;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResContractRepository extends JpaRepository<ResContract,Integer>{
	List<ResContract> findByPropertyUnitId(int propertyUnitId) ; 
	List<ResContract> findByArchivedFalse();
	
}
