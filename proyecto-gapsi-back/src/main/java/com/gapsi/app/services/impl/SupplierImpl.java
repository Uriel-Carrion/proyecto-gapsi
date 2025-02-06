package com.gapsi.app.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gapsi.app.entities.Supplier;
import com.gapsi.app.repositories.SupplierRepository;
import com.gapsi.app.services.SupplierService;

@Service
public class SupplierImpl implements SupplierService {

	@Autowired
	private SupplierRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Page<Supplier> findAll(Pageable pageable) {
		return (Page<Supplier>) repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Supplier> findById(@NonNull Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Supplier save(Supplier supplier) {
		return repository.save(supplier);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<Supplier> findByNameSupplierAndCompanyName(String nameSupplier, String companyName) {
		return repository.findByNameSupplierAndCompanyName(nameSupplier, companyName);
	}

	@Override
	public List<Supplier> findAll() {
		return (List<Supplier>) repository.findAll();
	}

}
