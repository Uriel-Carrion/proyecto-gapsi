package com.gapsi.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gapsi.app.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>{
	
	Optional<Supplier> findByNameSupplierAndCompanyName(String nameSupplier, String companyName);

}
