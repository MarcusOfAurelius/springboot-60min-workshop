# 🎯 Workshop Exercises

These exercises are designed to be completed in approximately 1 hour total (~10-12 minutes per chapter). Each exercise reinforces the core concepts of its chapter through hands-on coding.

---

## 📕 Chapter 1: Getting Started - REST API Basics

### Exercise: Add a Book Count Endpoint

**What to implement:**
Add a new endpoint that returns the total count of books for a specific genre.

**Requirements:**
1. Add a new method in `BookService.java` called `countBooksByGenre(String genre)` that returns a `long`
2. Add a new GET endpoint in `BookController.java` at `/api/books/genre/{genre}/count`
3. The endpoint should return the count as a number

**Test your solution:**
```bash
curl http://localhost:8080/api/books/genre/Programming/count
# Expected output: 5 (or whatever the actual count is)
```

**Learning objective:** Practice creating service methods and REST endpoints with path variables.

**Hints:**
- Use `@GetMapping("/genre/{genre}/count")`
- Use `@PathVariable` to capture the genre from the URL
- Use `stream().filter().count()` in the service layer

---

## 💾 Chapter 2: Database & JPA - Query Methods

### Exercise: Find Books Published Before a Year

**What to implement:**
Add a repository query method to find all books with an ISBN that suggests they were published before a certain year.

**Requirements:**
1. Add a method in `BookRepository.java` called `findByPriceBetween(Double minPrice, Double maxPrice)` that returns `List<Book>`
2. Add a new method in `BookService.java` called `getBooksByPriceRange(Double minPrice, Double maxPrice)`
3. Add a GET endpoint in `BookController.java` at `/api/books/price-range?min=XX&max=XX`

**Test your solution:**
```bash
curl "http://localhost:8081/api/books/price-range?min=30.00&max=50.00"
# Expected: List of books priced between $30 and $50
```

**Learning objective:** Create custom Spring Data JPA query methods and understand query derivation.

**Hints:**
- Spring Data JPA automatically implements `findByPriceBetween` - you just declare it!
- Use `@RequestParam` for min and max parameters
- The method name pattern is: `findBy + Property + Between`

---

## 👥 Chapter 3: Advanced Mappings - DTOs and Relationships

### Exercise: Add Publication Year to Book

**What to implement:**
Add a `publicationYear` field to books and expose it through the DTO.

**Requirements:**
1. Add `private Integer publicationYear;` field to `Book.java` entity (with `@Column`)
2. Add getter/setter for the new field
3. Add `private Integer publicationYear;` to `BookDTO.java`
4. Update `BookMapper.java` to include the new field in the mapping
5. Update `CreateBookRequest.java` to accept publication year with validation (`@Min(1900)`, `@Max(2100)`)

**Test your solution:**
```bash
curl -X POST http://localhost:8082/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Modern Java in Action",
    "authorId": 1,
    "isbn": "9781617293566",
    "price": 44.99,
    "stock": 15,
    "publicationYear": 2018,
    "categoryIds": [1, 2]
  }'
```

**Learning objective:** Understand the flow from entity → DTO → response and how to keep them in sync.

**Hints:**
- Don't forget to add the field to all three places: Entity, DTO, and Request
- In the mapper, just add: `dto.setPublicationYear(book.getPublicationYear());`
- Remember to import validation annotations: `@Min`, `@Max`

---

## 🛒 Chapter 4: Transaction Management - Rollback Handling

### Exercise: Add Stock Reorder Threshold Check

**What to implement:**
Create a transactional method that updates book stock and automatically creates a reorder notification if stock falls below a threshold.

**Requirements:**
1. Add a new method in `BookService.java` (or create a new service):
   ```java
   @Transactional
   public void updateStockWithReorderCheck(Long bookId, Integer newStock, Integer threshold)
   ```
2. This method should:
   - Update the book's stock
   - If new stock < threshold, log a warning message: `"REORDER ALERT: Book [title] is low on stock!"`
   - If newStock is negative, throw an `IllegalArgumentException` (triggering rollback)

3. Add an endpoint in `BookController.java`:
   ```
   PUT /api/books/{id}/stock?quantity={quantity}&threshold={threshold}
   ```

**Test your solution:**
```bash
# Valid update
curl -X PUT "http://localhost:8083/api/books/1/stock?quantity=5&threshold=10"
# Should log reorder alert

# Invalid update (should rollback)
curl -X PUT "http://localhost:8083/api/books/1/stock?quantity=-5&threshold=10"
# Should return error and NOT update stock
```

**Learning objective:** Understand how `@Transactional` ensures data consistency and automatic rollback on exceptions.

**Hints:**
- Use `@Transactional(rollbackFor = Exception.class)` for clarity
- Use `System.out.println()` or a logger for the reorder alert
- The transaction will automatically rollback when you throw an exception

---

## ⭐ Chapter 5: Advanced Features - Custom Validation

### Exercise: Create a Valid ISBN Validator

**What to implement:**
Create a custom validation annotation to validate ISBN format.

**Requirements:**
1. Create a new annotation `@ValidISBN` in a new package `com.bookstore.chapter5.validation`
   ```java
   @Target({ElementType.FIELD})
   @Retention(RetentionPolicy.RUNTIME)
   @Constraint(validatedBy = ISBNValidator.class)
   public @interface ValidISBN {
       String message() default "ISBN must be in format 978-XXXXXXXXXX or 979-XXXXXXXXXX";
       Class<?>[] groups() default {};
       Class<? extends Payload>[] payload() default {};
   }
   ```

2. Create `ISBNValidator.java` that implements `ConstraintValidator<ValidISBN, String>`
   - Check if ISBN starts with "978-" or "979-" 
   - Check if total length is 14 characters (including dashes)

3. Apply `@ValidISBN` to the `isbn` field in your book creation request DTO

**Test your solution:**
```bash
# Valid ISBN
curl -X POST http://localhost:8084/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Book",
    "author": "Test Author",
    "isbn": "978-0134685991",
    "price": 39.99,
    "stock": 10
  }'

# Invalid ISBN
curl -X POST http://localhost:8084/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Book",
    "author": "Test Author",
    "isbn": "123-4567890123",
    "price": 39.99,
    "stock": 10
  }'
# Should return validation error
```

**Learning objective:** Create custom validation annotations for domain-specific rules.

**Hints:**
- The validator's `isValid()` method should return `true` or `false`
- Use `value.startsWith("978") || value.startsWith("979")` for prefix check
- Use `value.length() == 13` for length (without dashes, or adjust if keeping dashes)

---

## 🎓 Bonus Challenge (If Time Permits)

If you finish early, try this cross-cutting challenge:

### Add a "Featured Books" Endpoint

Create an endpoint that returns books meeting ALL these criteria:
- In stock (stock > 0)
- Reasonably priced (price < $60)
- High rated (if you added reviews, average rating >= 4.0)
- Paginated results (page size: 5)

This combines concepts from multiple chapters: queries, pagination, filtering, and DTOs.

---

## 📝 Solutions

Solutions for these exercises can be found in the `WORKSHOP_SOLUTIONS.md` file (to be provided separately, or participants can ask the instructor).

**Remember:** The goal is to learn by doing! Don't hesitate to ask questions or experiment with different approaches.
