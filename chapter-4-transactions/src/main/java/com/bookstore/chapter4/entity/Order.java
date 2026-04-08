package com.bookstore.chapter4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order Entity - represents a customer order
 * 
 * Demonstrates:
 * - @OneToMany with cascade for order items
 * - Order total calculation
 * - Order status management
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String customerName;
    private String customerEmail;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    /**
     * @OneToMany with CascadeType.ALL
     * When we save an order, all order items are saved automatically
     * When we delete an order, all order items are deleted too
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    
    private Double totalAmount;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime completedAt;
    
    /**
     * Helper method to add an item to the order
     * Maintains both sides of the bidirectional relationship
     */
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
    
    /**
     * Calculate total from all items
     * This should be called within a transaction
     */
    public void calculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }
    
    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }
}
