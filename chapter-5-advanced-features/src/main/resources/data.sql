-- Insert sample books
INSERT INTO books (title, author, isbn, price, stock, description, average_rating) VALUES
('Clean Code', 'Robert C. Martin', '9780132350884', 44.99, 20, 'A Handbook of Agile Software Craftsmanship', 4.7),
('Effective Java', 'Joshua Bloch', '9780134685991', 52.99, 15, 'Best Practices for the Java Platform', 4.8),
('Spring in Action', 'Craig Walls', '9781617297571', 54.99, 10, 'Spring Framework Guide', 4.5),
('Domain-Driven Design', 'Eric Evans', '9780321125217', 59.99, 8, 'Tackling Complexity in Software', 4.6),
('Refactoring', 'Martin Fowler', '9780134757599', 49.99, 12, 'Improving the Design of Existing Code', 4.8);

-- Insert sample reviews
INSERT INTO reviews (book_id, reviewer_name, rating, comment, created_at) VALUES
(1, 'Alice Developer', 5, 'Absolutely essential for any developer! Changed how I write code.', CURRENT_TIMESTAMP),
(1, 'Bob Coder', 4, 'Great book but can be a bit dense. Worth the effort though!', CURRENT_TIMESTAMP),
(2, 'Charlie Java', 5, 'The definitive guide to Java best practices. Must-read!', CURRENT_TIMESTAMP),
(3, 'Diana Spring', 5, 'Best Spring Boot book I''ve read. Clear examples and great explanations.', CURRENT_TIMESTAMP),
(3, 'Eve Backend', 4, 'Very comprehensive, though some parts could be more concise.', CURRENT_TIMESTAMP);
