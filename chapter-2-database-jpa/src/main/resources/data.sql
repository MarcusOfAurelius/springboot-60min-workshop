-- Initial data for the bookstore
-- This file is executed after Hibernate creates the tables
-- (thanks to spring.jpa.defer-datasource-initialization=true)

-- Insert sample books
INSERT INTO books (title, author, isbn, price, stock, genre) VALUES
('The Pragmatic Programmer', 'David Thomas, Andrew Hunt', '978-0135957059', 49.99, 15, 'Programming'),
('Clean Code', 'Robert C. Martin', '978-0132350884', 44.99, 20, 'Programming'),
('Spring in Action', 'Craig Walls', '978-1617297571', 54.99, 10, 'Spring Framework'),
('Effective Java', 'Joshua Bloch', '978-0134685991', 52.99, 12, 'Java'),
('Domain-Driven Design', 'Eric Evans', '978-0321125217', 59.99, 8, 'Software Architecture'),
('Design Patterns', 'Gang of Four', '978-0201633610', 47.99, 14, 'Software Architecture'),
('Refactoring', 'Martin Fowler', '978-0134757599', 49.99, 11, 'Programming'),
('Head First Java', 'Kathy Sierra, Bert Bates', '978-0596009205', 39.99, 25, 'Java'),
('Java Concurrency in Practice', 'Brian Goetz', '978-0321349606', 49.99, 9, 'Java'),
('Microservices Patterns', 'Chris Richardson', '978-1617294549', 54.99, 7, 'Software Architecture');
