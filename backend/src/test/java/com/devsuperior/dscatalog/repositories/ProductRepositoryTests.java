package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}

	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIs() {

		Product product = Factory.createProduct();
		product.setId(null);

		product = repository.save(product);
		Optional<Product> result = repository.findById(product.getId());

		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
		Assertions.assertTrue(result.isPresent());
		Assertions.assertSame(result.get(), product);

	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		repository.deleteById(existingId);

		Optional<Product> result = repository.findById(existingId);

		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {

		Optional<Product> result = repository.findById(existingId);

		Assertions.assertTrue(result.isPresent());

	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {

		Optional<Product> result = repository.findById(nonExistingId);

		Assertions.assertTrue(result.isEmpty());

	}

	@Test
	public void findByIdShouldReturnNonEmptyOptionalProductWhenIdExists() {

		Optional<Product> result = repository.findById(existingId);

		Assertions.assertTrue(result.isPresent());

	}

	@Test
	public void findByIdShouldReturnEmptyOptionalProductWhenIdDoesNotExists() {

		Optional<Product> result = repository.findById(nonExistingId);

		Assertions.assertTrue(result.isEmpty());

	}

}
