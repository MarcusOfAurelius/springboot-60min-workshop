-- Insert Categories
INSERT INTO categories (name, description) VALUES
('Programming', 'Books about programming and software development'),
('Java', 'Books focused on Java programming language'),
('Spring Framework', 'Books about Spring and Spring Boot'),
('Software Architecture', 'Books on software design and architecture patterns'),
('Best Practices', 'Books on coding best practices and clean code'),
('Technical Skills', 'Books to improve technical skills');

-- Insert Authors
INSERT INTO authors (name, biography, email, country) VALUES
('Robert C. Martin', 'Also known as Uncle Bob, Robert C. Martin is a software engineer and author who has been a professional programmer since 1970.', 'uncle.bob@cleancoder.com', 'USA'),
('Joshua Bloch', 'Joshua Bloch is a software engineer and author, formerly the Chief Java Architect at Google.', 'joshua@effective.java', 'USA'),
('Craig Walls', 'Craig Walls is an author and software developer with over 20 years of experience.', 'craig@spring.io', 'USA'),
('Eric Evans', 'Eric Evans is a software designer who has focused on domains and domain modeling since the early 1990s.', 'eric@domainlanguage.com', 'USA'),
('Martin Fowler', 'Martin Fowler is a British software developer, author and international public speaker on software development.', 'martin@fowler.com', 'UK');

-- Insert Books
INSERT INTO books (title, isbn, price, stock, description, author_id) VALUES
('Clean Code', '978-0132350884', 44.99, 20, 'Even bad code can function. But if code isn''t clean, it can bring a development organization to its knees.', 1),
('Clean Architecture', '978-0134494166', 39.99, 15, 'Building upon the success of best-sellers The Clean Coder and Clean Code, legendary software craftsman Robert C. Martin shows how to bring greater professionalism and discipline to application architecture.', 1),
('Effective Java', '978-0134685991', 52.99, 12, 'The Definitive Guide to Java Platform Best Practices Updated for Java 7, 8, and 9', 2),
('Spring in Action', '978-1617297571', 54.99, 10, 'Spring in Action, Sixth Edition is a hands-on guide to the Spring Framework, updated for version 5.3.', 3),
('Domain-Driven Design', '978-0321125217', 59.99, 8, 'Tackling Complexity in the Heart of Software', 4),
('Refactoring', '978-0134757599', 49.99, 11, 'Improving the Design of Existing Code', 5);

-- Link Books to Categories (Many-to-Many)
-- Clean Code: Programming, Best Practices, Technical Skills
INSERT INTO book_categories (book_id, category_id) VALUES (1, 1), (1, 5), (1, 6);

-- Clean Architecture: Programming, Software Architecture, Best Practices
INSERT INTO book_categories (book_id, category_id) VALUES (2, 1), (2, 4), (2, 5);

-- Effective Java: Java, Programming, Best Practices
INSERT INTO book_categories (book_id, category_id) VALUES (3, 2), (3, 1), (3, 5);

-- Spring in Action: Spring Framework, Programming, Java
INSERT INTO book_categories (book_id, category_id) VALUES (4, 3), (4, 1), (4, 2);

-- Domain-Driven Design: Software Architecture, Programming, Best Practices
INSERT INTO book_categories (book_id, category_id) VALUES (5, 4), (5, 1), (5, 5);

-- Refactoring: Programming, Best Practices, Technical Skills
INSERT INTO book_categories (book_id, category_id) VALUES (6, 1), (6, 5), (6, 6);
