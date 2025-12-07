package kz.edu.electronics_store.dto;

import jakarta.validation.constraints.NotNull;
import kz.edu.electronics_store.entity.OrderStatus;

public record OrderStatusUpdateRequest(
        @NotNull OrderStatus status
) {}
