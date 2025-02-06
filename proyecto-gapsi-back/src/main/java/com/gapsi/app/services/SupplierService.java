package com.gapsi.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.gapsi.app.entities.Supplier;


public interface SupplierService {
	
	Page<Supplier> findAll(Pageable pageable);
	
	List<Supplier> findAll();
	
	Optional<Supplier> findById(@NonNull Long id);
	
	Optional<Supplier> findByNameSupplierAndCompanyName(String nameSupplier, String companyName);
	
	Supplier save (Supplier supplier);
	
	void deleteById (@NonNull Long id);
	

}
