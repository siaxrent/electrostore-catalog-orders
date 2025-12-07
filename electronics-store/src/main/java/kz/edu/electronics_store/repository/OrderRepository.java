package kz.edu.electronics_store.repository;

import kz.edu.electronics_store.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
