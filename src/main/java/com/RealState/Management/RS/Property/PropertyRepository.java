package com.RealState.Management.RS.Property;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Integer> {

	List<Property> findByArchivedFalse();
	
}
