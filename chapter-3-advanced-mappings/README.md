# 👥 Chapter 3: Advanced Mappings

Welcome to Chapter 3! This chapter explores entity relationships, DTOs, and request/response mapping patterns.

## 🎯 Learning Objectives

- Entity relationships (`@OneToMany`, `@ManyToOne`, `@ManyToMany`)
- Bidirectional relationships and cascade types
- Lazy vs Eager fetching strategies
- Avoiding N+1 query problems with JOIN FETCH
- DTO (Data Transfer Object) pattern
- Manual mapping vs mapping libraries
- Request/Response separation

## 🏗️ Project Structure

```
chapter-3-advanced-mappings/
├── entity/
│   ├── Author.java          # @OneToMany with Books
│   ├── Book.java            # @ManyToOne with Author, @ManyToMany with Categories
│   └── Category.java        # @ManyToMany with Books
├── dto/
│   ├── AuthorDTO.java       # Author response DTO
│   ├── BookDTO.java         # Book response DTO
│   ├── CreateAuthorRequest.java
│   └── CreateBookRequest.java
├── repository/
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   └── CategoryRepository.java
├── mapper/
│   └── BookMapper.java      # Entity ↔ DTO conversion
├── service/
│   ├── AuthorService.java
│   ├── BookService.java
│   └── CategoryService.java
└── controller/
    ├── AuthorController.java
    ├── BookController.java
    └── CategoryController.java
```

## 🚀 Running the Application

```bash
cd chapter-3-advanced-mappings
mvn spring-boot:run
```

Access:
- API: `http://localhost:8082`

## 🗄️ Accessing H2 Console

Open `http://localhost:8082/h2-console` in your browser and enter:

- **JDBC URL**: `jdbc:h2:file:./data/bookstore-ch3`
- **Username**: `sa`
- **Password**: (leave empty)

Try running SQL queries to explore entity relationships:
```sql
SELECT * FROM authors;
SELECT * FROM books;
SELECT * FROM categories;
SELECT * FROM book_categories;  -- Many-to-Many join table

-- Query with joins
SELECT b.title, a.name as author_name 
FROM books b 
JOIN authors a ON b.author_id = a.id;
```

## 🔍 Entity Relationships Explained

### One-to-Many (Author → Books)

```java
@Entity
public class Author {
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;
}
```

### Many-to-One (Book → Author)

```java
@Entity
public class Book {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
}
```

### Many-to-Many (Book ↔ Categories)

```java
@Entity
public class Book {
    @ManyToMany
    @JoinTable(
        name = "book_categories",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
}
```

## 🧪 Testing the API

### Authors

```bash
# Get all authors with their books
curl http://localhost:8082/api/authors

# Get specific author
curl http://localhost:8082/api/authors/1

# Search authors by name
curl "http://localhost:8082/api/authors/search?name=Martin"

# Get authors by country
curl http://localhost:8082/api/authors/country/USA

# Create a new author
curl -X POST http://localhost:8082/api/authors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Kathy Sierra",
    "biography": "Author of Head First series",
    "email": "kathy@headfirst.com",
    "country": "USA"
  }'
```

### Books

```bash
# Get all books
curl http://localhost:8082/api/books

# Get book by ID (with author and categories)
curl http://localhost:8082/api/books/1

# Get books by author
curl http://localhost:8082/api/books/author/1

# Get books by category
curl http://localhost:8082/api/books/category/Programming

# Create a book
curl -X POST http://localhost:8082/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Head First Design Patterns",
    "isbn": "978-0596007126",
    "price": 44.99,
    "stock": 15,
    "description": "A brain-friendly guide to design patterns",
    "authorId": 6,
    "categoryIds": [1, 5]
  }'
```

### Categories

```bash
# Get all categories
curl http://localhost:8082/api/categories

# Create a category
curl -X POST http://localhost:8082/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Design Patterns",
    "description": "Books about software design patterns"
  }'
```

## 🔍 Key Concepts

### Why DTOs?

**Problems with returning entities directly:**
1. Circular references cause JSON serialization errors
2. Exposes internal database structure
3. Can't control what data is sent
4. Lazy-loading issues

**Benefits of DTOs:**
1. Clean API contracts
2. Control over response structure
3. Can combine data from multiple entities
4. Prevents over-fetching

### N+1 Query Problem

**Bad approach:**
```java
// This triggers 1 + N queries!
List<Author> authors = authorRepository.findAll(); // 1 query
for (Author author : authors) {
    author.getBooks(); // N queries (one per author)
}
```

**Good approach:**
```java
// Single query with JOIN FETCH
@Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :id")
Optional<Author> findByIdWithBooks(Long id);
```

## 🎯 Workshop Exercise

**Time allocation: 10-12 minutes**

### Exercise: Add Publication Year to Book

Extend the Book entity and DTOs with a publication year field.

**What you'll modify:**
- Add `publicationYear` field to `Book.java` entity
- Add `publicationYear` to `BookDTO.java`
- Update `BookMapper.java` to map the new field
- Add validation to `CreateBookRequest.java`

**Test command:**
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

**See full details in:** [WORKSHOP_EXERCISES.md](../WORKSHOP_EXERCISES.md#-chapter-3-advanced-mappings---dtos-and-relationships)

### Fetch Types

- **LAZY** (default for `@OneToMany`, `@ManyToMany`): Load data only when accessed
- **EAGER** (default for `@ManyToOne`, `@OneToOne`): Load data immediately

Best practice: Use LAZY by default, use JOIN FETCH when you need the data.

### Cascade Types

- `CascadeType.ALL`: All operations cascade
- `CascadeType.PERSIST`: Only save operations cascade
- `CascadeType.MERGE`: Only update operations cascade
- `CascadeType.REMOVE`: Only delete operations cascade

## 💡 What's Next?

In **Chapter 4**, we'll learn about:
- Transaction management with `@Transactional`
- Transaction propagation and isolation levels
- Handling concurrent updates
- Building a shopping cart with transactional operations

## 🎓 Exercises

1. **Add a Publisher entity** with @OneToMany relationship to Book
2. **Create a custom query** to find all authors with more than X books
3. **Add validation** to prevent duplicate ISBNs
4. **Implement pagination** for the book list
5. **Create a "co-authors" feature** (ManyToMany between authors)

## 🐛 Common Issues

### LazyInitializationException?
Use JOIN FETCH or make sure you're inside a transaction when accessing lazy properties.

### Circular reference in JSON?
Use `@JsonManagedReference` and `@JsonBackReference` or switch to DTOs.

### Duplicate entries in Many-to-Many?
Use `Set` instead of `List` for Many-to-Many relationships.

## 📚 Database Schema

```sql
authors (id, name, biography, email, country)
books (id, title, isbn, price, stock, description, author_id)
categories (id, name, description)
book_categories (book_id, category_id) -- join table
```

## 📖 Additional Resources

- [Hibernate Relationships Guide](https://hibernate.org/orm/documentation/)
- [JPA Annotations Reference](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html)
- [MapStruct](https://mapstruct.org/) - for automatic DTO mapping

Happy coding! 🚀
