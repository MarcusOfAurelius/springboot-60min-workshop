package com.bookstore.chapter4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderItem Entity - represents an item in an order
 */
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    private Integer quantity;
    private Double priceAtPurchase; // Price when ordered (may differ from current price)
    private Double subtotal;
    
    public OrderItem(Book book, Integer quantity) {
        this.book = book;
        this.quantity = quantity;
        this.priceAtPurchase = book.getPrice();
        this.subtotal = this.priceAtPurchase * quantity;
    }
}
