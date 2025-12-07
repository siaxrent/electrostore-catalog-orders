package kz.edu.electronics_store.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal priceAtPurchase;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Order order;

    protected OrderItem() {}

    public OrderItem(Product product, int quantity, BigDecimal priceAtPurchase) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
    public Order getOrder() { return order; }

    public void setOrder(Order order) { this.order = order; }
}
