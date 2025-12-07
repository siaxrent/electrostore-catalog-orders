package kz.edu.electronics_store.dto;

import kz.edu.electronics_store.entity.ProductType;
import java.math.BigDecimal;

/**
 * DTO для вывода товара в API.
 */
public record ProductDto(
        Long id,
        String sku,
        String name,
        BigDecimal price,
        int stock,
        ProductType type,
        Long categoryId,
        String categoryName
) {}
