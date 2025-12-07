package kz.edu.electronics_store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateRequest(
        @NotBlank String customerName,
        @NotBlank String customerPhone,
        @NotEmpty List<Item> items
) {
    public record Item(
            @NotNull Long productId,
            @Min(1) int quantity
    ) {}
}
