package kz.edu.electronics_store.dto;

import kz.edu.electronics_store.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        String customerName,
        String customerPhone,
        OrderStatus status,
        LocalDateTime createdAt,
        BigDecimal total,
        List<Item> items
) {
    public record Item(
            Long productId,
            String productName,
            int quantity,
            BigDecimal priceAtPurchase,
            BigDecimal lineTotal
    ) {}
}
