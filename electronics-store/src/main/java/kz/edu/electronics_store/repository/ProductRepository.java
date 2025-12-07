package kz.edu.electronics_store.repository;

import kz.edu.electronics_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
