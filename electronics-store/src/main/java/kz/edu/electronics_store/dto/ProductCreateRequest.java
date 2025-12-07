package kz.edu.electronics_store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kz.edu.electronics_store.entity.ProductType;

import java.math.BigDecimal;

/**
 * Запрос на создание товара.
 */
public record ProductCreateRequest(
        @NotBlank(message = "SKU обязателен")
        @Size(min = 3, max = 64, message = "SKU должен быть 3-64 символа")
        String sku,

        @NotBlank(message = "Название обязательно")
        @Size(min = 2, max = 200, message = "Название должно быть 2-200 символов")
        String name,

        @NotNull(message = "Цена обязательна")
        @Min(value = 0, message = "Цена не может быть отрицательной")
        BigDecimal price,

        @Min(value = 0, message = "Остаток (stock) не может быть отрицательным")
        int stock,

        @NotNull(message = "Тип товара обязателен")
        ProductType type,

        @NotNull(message = "categoryId обязателен")
        Long categoryId
) {}
