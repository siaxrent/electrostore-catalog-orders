package kz.edu.electronics_store.controller;

import jakarta.validation.Valid;
import kz.edu.electronics_store.dto.OrderCreateRequest;
import kz.edu.electronics_store.dto.OrderDto;
import kz.edu.electronics_store.dto.OrderStatusUpdateRequest;
import kz.edu.electronics_store.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления заказами.
 * <p>
 * Обрабатывает создание заказов, получение списка и смену статуса.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Конструктор с внедрением сервиса заказов.
     *
     * @param orderService сервис работы с заказами
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Создать новый заказ.
     *
     * @param req данные по заказу и позициям
     * @return созданный заказ
     */
    @PostMapping
    public OrderDto create(@Valid @RequestBody OrderCreateRequest req) {
        return orderService.create(req);
    }

    /**
     * Получить список всех заказов.
     *
     * @return список DTO заказов
     */
    @GetMapping
    public List<OrderDto> list() {
        return orderService.list();
    }

    /**
     * Обновить статус заказа.
     *
     * @param id идентификатор заказа
     * @param req DTO с новым статусом
     * @return заказ с обновлённым статусом
     */
    @PatchMapping("/{id}/status")
    public OrderDto updateStatus(@PathVariable long id, @Valid @RequestBody OrderStatusUpdateRequest req) {
        return orderService.updateStatus(id, req.status());
    }
}
