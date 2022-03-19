package com.RealState.Management.RS.uploader.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FilesSequenceRepository extends JpaRepository<FilesSequence,Integer>{

}
