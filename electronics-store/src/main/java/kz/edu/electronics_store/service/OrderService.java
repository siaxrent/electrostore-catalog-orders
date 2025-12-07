package kz.edu.electronics_store.service;

import kz.edu.electronics_store.dto.OrderCreateRequest;
import kz.edu.electronics_store.dto.OrderDto;
import kz.edu.electronics_store.entity.OrderStatus;

import java.util.List;

/**
 * Сервисный интерфейс для работы с заказами.
 */
public interface OrderService {

    /**
     * Создать новый заказ.
     *
     * @param req DTO с данными по заказу и позициям
     * @return созданный заказ
     */
    OrderDto create(OrderCreateRequest req);

    /**
     * Получить список всех заказов.
     *
     * @return список DTO заказов
     */
    List<OrderDto> list();

    /**
     * Обновить статус заказа.
     *
     * @param id     идентификатор заказа
     * @param status новый статус
     * @return заказ с обновлённым статусом
     */
    OrderDto updateStatus(long id, OrderStatus status);
}
