package kz.edu.electronics_store.repository;

import kz.edu.electronics_store.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}


