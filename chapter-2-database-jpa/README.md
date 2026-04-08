# 💾 Chapter 2: Database & JPA

Welcome to Chapter 2! Now we're adding real database persistence with H2 and Spring Data JPA. Your data will survive application restarts!

## 🎯 Learning Objectives

By the end of this chapter, you will understand:
- How to configure H2 database (in-memory and file-based)
- JPA entity mapping (`@Entity`, `@Id`, `@GeneratedValue`, `@Column`)
- Spring Data JPA repositories
- Query methods (derived from method names)
- Custom JPQL queries
- Using H2 Console to inspect your database

## 🏗️ Project Structure

```
chapter-2-database-jpa/
├── src/main/java/com/bookstore/chapter2/
│   ├── Chapter2Application.java
│   ├── controller/
│   │   └── BookController.java
│   ├── service/
│   │   └── BookService.java
│   ├── entity/
│   │   └── Book.java              # JPA Entity
│   └── repository/
│       └── BookRepository.java    # Spring Data Repository
└── src/main/resources/
    ├── application.properties     # Database configuration
    ├── data.sql                   # Initial data
    └── banner.txt
```

## 🚀 Running the Application

```bash
cd chapter-2-database-jpa
mvn spring-boot:run
```

The application will:
- Start on `http://localhost:8081` (note: different port than Chapter 1!)
- Create a database file at `./data/bookstore.mv.db`
- Populate it with initial data from `data.sql`
- Enable H2 Console at `http://localhost:8081/h2-console`

## 🗄️ Accessing H2 Console

Open `http://localhost:8081/h2-console` in your browser and enter:

- **JDBC URL**: `jdbc:h2:file:./data/bookstore`
- **Username**: `sa`
- **Password**: (leave empty)

Try running SQL queries:
```sql
SELECT * FROM books;
SELECT * FROM books WHERE genre = 'Java';
SELECT * FROM books WHERE price < 50;
```

## 🧪 Testing the API

### Get All Books
```bash
curl http://localhost:8081/api/books
```

### Get Book Statistics
```bash
curl http://localhost:8081/api/books/stats
```

### Search by Author
```bash
curl http://localhost:8081/api/books/author/Robert%20C.%20Martin
```

### Get Affordable Books by Author
```bash
curl "http://localhost:8081/api/books/author/Robert%20C.%20Martin/affordable?maxPrice=50.00"
```

### Get Books Under Price
```bash
curl "http://localhost:8081/api/books/price/under?price=50.00"
```

### Get Books in Price Range
```bash
curl "http://localhost:8081/api/books/price/range?min=40.00&max=55.00"
```

### Get Low Stock Books
```bash
curl "http://localhost:8081/api/books/low-stock?threshold=10"
```

### Create a New Book
```bash
curl -X POST http://localhost:8081/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Spring Boot in Action",
    "author": "Craig Walls",
    "isbn": "978-1617292545",
    "price": 44.99,
    "stock": 15,
    "genre": "Spring Framework"
  }'
```

## 🔍 Key Concepts Explained

### JPA Entity
The `@Entity` annotation transforms a POJO into a database table:

```java
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    // ... more fields
}
```

### Spring Data Repository
By extending `JpaRepository`, you get tons of methods for free:

```java
public interface BookRepository extends JpaRepository<Book, Long> {
    // Spring generates the implementation!
}
```

### Query Methods
Spring Data can generate queries from method names:

```java
// SELECT * FROM books WHERE title LIKE '%?%'
List<Book> findByTitleContainingIgnoreCase(String title);

// SELECT * FROM books WHERE price < ?
List<Book> findByPriceLessThan(Double price);

// SELECT * FROM books WHERE price BETWEEN ? AND ?
List<Book> findByPriceBetween(Double min, Double max);
```

### Custom JPQL Queries
For complex queries, use `@Query`:

```java
@Query("SELECT b FROM Book b WHERE b.author = :author AND b.price < :maxPrice")
List<Book> findAffordableBooksByAuthor(@Param("author") String author, 
                                       @Param("maxPrice") Double maxPrice);
```

### File-based vs In-memory H2

**File-based** (what we use):
```properties
spring.datasource.url=jdbc:h2:file:./data/bookstore
```
- Data persists between restarts
- Database file saved to disk

**In-memory** (alternative):
```properties
spring.datasource.url=jdbc:h2:mem:bookstore
```
- Data lost on restart
- Faster for testing

## 💡 What's Next?

In **Chapter 3**, we'll learn:
- Entity relationships (`@OneToMany`, `@ManyToOne`, `@ManyToMany`)
- Working with multiple related entities (Authors, Books, Categories)
- DTOs and request/response mapping
- Avoiding N+1 query problems

## � Workshop Exercise

**Time allocation: 10-12 minutes**

### Exercise: Find Books in Price Range

Implement a Spring Data JPA query method to find books within a price range.

**What you'll create:**
- Repository method: `findByPriceBetween(Double minPrice, Double maxPrice)`
- Service method: `getBooksByPriceRange(Double minPrice, Double maxPrice)`
- REST endpoint: `GET /api/books/price-range?min=XX&max=XX`

**Test command:**
```bash
curl "http://localhost:8081/api/books/price-range?min=30.00&max=50.00"
```

**See full details in:** [WORKSHOP_EXERCISES.md](../WORKSHOP_EXERCISES.md#-chapter-2-database--jpa---query-methods)

## 🎓 Additional Practice Exercises

If you finish early, try these:

1. **Add a description field** to the Book entity and update the database
2. **Create a query method** to find books by author containing a keyword
3. **Add pagination** to the getAllBooks endpoint
4. **Create a custom query** to find books published with stock above average
5. **Add a published date field** and query methods for it

## 🐛 Common Issues

### Database locked?
Stop all running instances of the application.

### Data not persisting?
Check that `spring.datasource.url` uses `file:` not `mem:`

### Can't access H2 Console?
Ensure `spring.h2.console.enabled=true` in application.properties

### SQL syntax error in data.sql?
Make sure you're using H2-compatible SQL syntax.

## 📚 Comparison with Chapter 1

| Aspect | Chapter 1 | Chapter 2 |
|--------|-----------|-----------|
| Storage | ArrayList | H2 Database |
| Persistence | No | Yes |
| Service Layer | Manual CRUD | Repository Pattern |
| Query Support | Manual | Query Methods & JPQL |
| Concurrency | None | Database-level |
| Scalability | Limited | Better |

## 📖 Additional Resources

- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [H2 Database Documentation](https://www.h2database.com/)
- [JPA Annotations Reference](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html)

Happy coding! 🚀
