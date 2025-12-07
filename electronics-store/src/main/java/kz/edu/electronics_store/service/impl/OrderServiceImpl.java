package kz.edu.electronics_store.service.impl;

import kz.edu.electronics_store.dto.OrderCreateRequest;
import kz.edu.electronics_store.dto.OrderDto;
import kz.edu.electronics_store.entity.Order;
import kz.edu.electronics_store.entity.OrderItem;
import kz.edu.electronics_store.entity.OrderStatus;
import kz.edu.electronics_store.entity.Product;
import kz.edu.electronics_store.exception.NotFoundException;
import kz.edu.electronics_store.repository.OrderRepository;
import kz.edu.electronics_store.repository.ProductRepository;
import kz.edu.electronics_store.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация сервиса работы с заказами.
 * <p>
 * Отвечает за проверку остатков, создание позиций заказа и смену статусов,
 * а также пишет детали операций в лог.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    /**
     * Конструктор с внедрением репозиториев заказов и товаров.
     *
     * @param orderRepository   репозиторий заказов
     * @param productRepository репозиторий товаров
     */
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderDto create(OrderCreateRequest req) {
        log.info("Creating order: customerName={}, phone={}", req.customerName(), req.customerPhone());

        Order order = new Order(req.customerName(), req.customerPhone());

        for (OrderCreateRequest.Item i : req.items()) {
            Product p = productRepository.findById(i.productId())
                    .orElseThrow(() -> new NotFoundException("Товар не найден: id=" + i.productId()));

            if (p.getStock() < i.quantity()) {
                log.warn("Not enough stock: productId={}, name={}, stock={}, requested={}",
                        p.getId(), p.getName(), p.getStock(), i.quantity());
                throw new IllegalArgumentException("Недостаточно товара на складе: " + p.getName());
            }

            // уменьшаем остаток
            int oldStock = p.getStock();
            p.setStock(oldStock - i.quantity());
            productRepository.save(p);

            log.info("Stock updated: productId={}, name={}, {} -> {}",
                    p.getId(), p.getName(), oldStock, p.getStock());

            order.addItem(new OrderItem(p, i.quantity(), p.getPrice()));
        }

        Order saved = orderRepository.save(order);
        log.info("Order created: id={}, itemsCount={}", saved.getId(), saved.getItems().size());

        return toDto(saved);
    }

    @Override
    public List<OrderDto> list() {
        log.info("Listing orders");
        return orderRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public OrderDto updateStatus(long id, OrderStatus status) {
        log.info("Updating order status: id={}, status={}", id, status);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказ не найден: id=" + id));

        OrderStatus old = order.getStatus();
        order.setStatus(status);

        Order saved = orderRepository.save(order);
        log.info("Order status updated: id={}, {} -> {}", saved.getId(), old, saved.getStatus());

        return toDto(saved);
    }

    private OrderDto toDto(Order o) {
        List<OrderDto.Item> items = o.getItems().stream().map(oi -> {
            BigDecimal lineTotal = oi.getPriceAtPurchase().multiply(BigDecimal.valueOf(oi.getQuantity()));
            return new OrderDto.Item(
                    oi.getProduct().getId(),
                    oi.getProduct().getName(),
                    oi.getQuantity(),
                    oi.getPriceAtPurchase(),
                    lineTotal
            );
        }).toList();

        BigDecimal total = items.stream()
                .map(OrderDto.Item::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderDto(
                o.getId(),
                o.getCustomerName(),
                o.getCustomerPhone(),
                o.getStatus(),
                o.getCreatedAt(),
                total,
                items
        );
    }
}
