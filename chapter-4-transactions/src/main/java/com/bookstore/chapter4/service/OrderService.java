package com.bookstore.chapter4.service;

import com.bookstore.chapter4.entity.Book;
import com.bookstore.chapter4.entity.Order;
import com.bookstore.chapter4.entity.OrderItem;
import com.bookstore.chapter4.repository.BookRepository;
import com.bookstore.chapter4.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * OrderService - Demonstrates transaction management
 * 
 * Key concepts:
 * - @Transactional ensures atomicity (all or nothing)
 * - Transaction propagation controls how transactions interact
 * - Isolation levels control concurrent access
 * - Rollback on exceptions
 */
@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    
    public OrderService(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }
    
    /**
     * Create an order with stock reservation
     * 
     * @Transactional ensures:
     * 1. If any operation fails, everything rolls back
     * 2. All operations see consistent data
     * 3. Stock updates are atomic
     * 
     * Isolation.READ_COMMITTED prevents dirty reads
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Order createOrder(String customerName, String customerEmail, Map<Long, Integer> bookQuantities) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        
        // Process each book in the order
        for (Map.Entry<Long, Integer> entry : bookQuantities.entrySet()) {
            Long bookId = entry.getKey();
            Integer quantity = entry.getValue();
            
            // Find the book
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Book not found: " + bookId));
            
            // Check stock availability
            if (book.getStock() < quantity) {
                // This will cause a rollback - no changes will be saved!
                throw new RuntimeException(
                    "Insufficient stock for book: " + book.getTitle() + 
                    ". Available: " + book.getStock() + ", Requested: " + quantity
                );
            }
            
            // Reserve stock
            book.setStock(book.getStock() - quantity);
            bookRepository.save(book);
            
            // Create order item
            OrderItem orderItem = new OrderItem(book, quantity);
            order.addItem(orderItem);
        }
        
        // Calculate total
        order.calculateTotal();
        
        // Save order (and all order items via cascade)
        return orderRepository.save(order);
    }
    
    /**
     * Confirm an order
     * 
     * Propagation.REQUIRED (default):
     * - Joins existing transaction if one exists
     * - Creates new transaction if none exists
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Order confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new RuntimeException("Only PENDING orders can be confirmed");
        }
        
        order.setStatus(Order.OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }
    
    /**
     * Cancel an order and restore stock
     * 
     * This demonstrates transaction rollback on business logic failure
     */
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel delivered orders");
        }
        
        // Restore stock for each item
        for (OrderItem item : order.getItems()) {
            Book book = item.getBook();
            book.setStock(book.getStock() + item.getQuantity());
            bookRepository.save(book);
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
    
    /**
     * Complete an order
     * 
     * Propagation.REQUIRES_NEW:
     * - Always creates a new transaction
     * - Suspends current transaction if one exists
     * - Useful for audit logging that should commit even if main transaction fails
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(Order.OrderStatus.DELIVERED);
        order.setCompletedAt(LocalDateTime.now());
        
        return orderRepository.save(order);
    }
    
    /**
     * Get all orders (read-only transaction)
     * 
     * readOnly = true optimizes the transaction for read operations
     */
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
