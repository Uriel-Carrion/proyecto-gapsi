package com.gapsi.app.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gapsi.app.entities.Supplier;
import com.gapsi.app.response.DTOErrorResponse;
import com.gapsi.app.services.SupplierService;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

	private static final Log logger = LogFactory.getLog(SupplierController.class);

	@Autowired
	private SupplierService service;

	/**
	 * Crea un nuevo proveedor.
	 *
	 * @param supplier Objeto Supplier que contiene los datos del nuevo proveedor.
	 * @return ResponseEntity con el código de estado HTTP y el proveedor creado o
	 *         un mensaje de error.
	 */
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Supplier supplier) {
		try {
			Optional<Supplier> existingSupplier = service.findByNameSupplierAndCompanyName(supplier.getNameSupplier(),
					supplier.getCompanyName());

			if (existingSupplier.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new DTOErrorResponse("Ya existe un proveedor con el mismo nombre", "Nombre Duplicado"));
			}

			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(supplier));
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					(new DTOErrorResponse("Ha ocurrido un error inesperado al recuperar a los proveedores", "Error")));
		}
	}

	/**
	 * Obtiene un proveedor aleatorio.
	 *
	 * @return ResponseEntity con el código de estado HTTP y el proveedor aleatorio
	 *         o un mensaje de error.
	 */
	@GetMapping("/supplier")
	public ResponseEntity<?> getSupplier() {
		try {
			List<Supplier> suppliers = service.findAll();

			if (suppliers.isEmpty()) {
				DTOErrorResponse errorResponse = new DTOErrorResponse("No se encontraron usuarios", "Not Found");
				return ResponseEntity.status(404).body(errorResponse);
			}
			Random random = new Random();
			int index = random.nextInt(suppliers.size());
			return ResponseEntity.status(HttpStatus.OK).body(suppliers.get(index));

		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body((new DTOErrorResponse("Ha ocurrido un error inesperado", "Error")));
		}
	}

	/**
	 * Obtiene un proveedor por su ID.
	 *
	 * @param id ID del proveedor a buscar.
	 * @return ResponseEntity con el código de estado HTTP y el proveedor encontrado
	 *         o un mensaje de error.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		try {
			Optional<Supplier> userOptional = service.findById(id);
			if (userOptional.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(userOptional.orElseThrow());
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new DTOErrorResponse("error", "el usuario no se encontro por el id:" + id));
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body((new DTOErrorResponse("Ha ocurrido un error inesperado", "Error")));
		}
	}

	/**
	 * Obtiene una lista paginada de proveedores.
	 *
	 * @param page Número de página (índice 0-based).
	 * @param size Número de elementos por página.
	 * @return ResponseEntity con el código de estado HTTP y una página de
	 *         proveedores.
	 */
	@GetMapping
	public ResponseEntity<?> getSuppliers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		try {
			Page<Supplier> suppliers = service.findAll(PageRequest.of(page, size));
			return ResponseEntity.ok(suppliers);
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					(new DTOErrorResponse("Ha ocurrido un error inesperado al recuperar a los proveedores", "Error")));
		}
	}

	/**
	 * Elimina un proveedor por su ID.
	 *
	 * @param id ID del proveedor a eliminar.
	 * @return ResponseEntity con el código de estado HTTP.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Supplier> userOptional = service.findById(id);
		if (userOptional.isPresent()) {
			service.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
