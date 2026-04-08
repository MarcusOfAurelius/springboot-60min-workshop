# 🛒 Chapter 4: Transaction Management

Learn how to manage transactions in Spring Boot with a shopping cart and order system!

## 🎯 Learning Objectives

- `@Transactional` annotation and its properties
- Transaction propagation (REQUIRED, REQUIRES_NEW, etc.)
- Transaction isolation levels
- Rollback strategies
- Optimistic locking with `@Version`
- Atomic operations across multiple entities

## 🚀 Running

```bash
cd chapter-4-transactions
mvn spring-boot:run
```

Access: http://localhost:8083

## 🧪 Testing Transaction Scenarios

### Create an Order
```bash
curl -X POST http://localhost:8083/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "bookQuantities": {
      "1": 2,
      "2": 1
    }
  }'
```

### Test Insufficient Stock (Rollback)
```bash
# Try to order more than available stock - transaction will rollback!
curl -X POST http://localhost:8083/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Jane Doe",
    "customerEmail": "jane@example.com",
    "bookQuantities": {
      "1": 100
    }
  }'
```

### Confirm Order
```bash
curl -X PUT http://localhost:8083/api/orders/1/confirm
```

### Cancel Order (Stock Restored)
```bash
curl -X PUT http://localhost:8083/api/orders/1/cancel
```

## 🔍 Key Concepts

### @Transactional Properties

```java
@Transactional(
    propagation = Propagation.REQUIRED,    // Transaction behavior
    isolation = Isolation.READ_COMMITTED,  // Concurrency control
    readOnly = false,                      // Optimization hint
    rollbackFor = Exception.class          // When to rollback
)
```

### Propagation Types
- **REQUIRED** (default): Join existing or create new
- **REQUIRES_NEW**: Always create new, suspend current
- **MANDATORY**: Must have existing transaction
- **SUPPORTS**: Join if exists, non-transactional otherwise
- **NOT_SUPPORTED**: Execute non-transactionally
- **NEVER**: Throw exception if transaction exists

### Isolation Levels
- **READ_UNCOMMITTED**: Dirty reads possible
- **READ_COMMITTED**: No dirty reads (default)
- **REPEATABLE_READ**: No dirty or non-repeatable reads
- **SERIALIZABLE**: Full isolation, slowest

### Optimistic Locking

```java
@Version
private Long version;
```

Hibernate automatically checks version before updating. If version changed, throws `OptimisticLockException`.

## 🎯 Workshop Exercise

**Time allocation: 10-12 minutes**

### Exercise: Add Stock Reorder Threshold Check

Create a transactional method that updates book stock and logs a reorder alert when stock is low.

**What you'll create:**
- Transactional method: `updateStockWithReorderCheck(Long bookId, Integer newStock, Integer threshold)`
- Automatic rollback if stock becomes negative
- Reorder alert logging when stock falls below threshold
- REST endpoint: `PUT /api/books/{id}/stock?quantity={quantity}&threshold={threshold}`

**Test commands:**
```bash
# Valid update with low stock warning
curl -X PUT "http://localhost:8083/api/books/1/stock?quantity=5&threshold=10"

# Invalid update (should rollback)
curl -X PUT "http://localhost:8083/api/books/1/stock?quantity=-5&threshold=10"
```

**See full details in:** [WORKSHOP_EXERCISES.md](../WORKSHOP_EXERCISES.md#-chapter-4-transaction-management---rollback-handling)

## 💡 What's Next?

Chapter 5: Advanced Features - Validation, Exception Handling, Reviews!

Happy coding! 🚀
