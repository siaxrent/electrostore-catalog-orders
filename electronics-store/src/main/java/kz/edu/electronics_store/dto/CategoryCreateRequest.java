package kz.edu.electronics_store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Запрос на создание категории.
 */
public record CategoryCreateRequest(
        @NotBlank(message = "Название категории обязательно")
        @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
        String name
) {}
