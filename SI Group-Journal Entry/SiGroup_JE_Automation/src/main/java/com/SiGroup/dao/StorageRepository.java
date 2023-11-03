package com.SiGroup.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SiGroup.entites.ImageData;

public interface StorageRepository  extends JpaRepository<ImageData,Long>{

	Optional<ImageData> findByName(String fileName);
	
	void deleteByName(String fileName);
	
	
	
	
}
