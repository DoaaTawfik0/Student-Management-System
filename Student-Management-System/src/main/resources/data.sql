-- Insert Students
INSERT INTO STUDENTS (ID, STUDENT_NAME, STUDENT_EMAIL, STUDENT_AGE) VALUES
(100, 'Tarek Hassan', 'tarek.hassan@university.edu', 20),
(101, 'Doaa Mohamed', 'doaa.mohamed@university.edu', 21),
(102, 'Ahmed Khalid', 'ahmed.khalid@university.edu', 22),
(103, 'Lina Samir', 'lina.samir@university.edu', 19),
(104, 'Omar Farouk', 'omar.farouk@university.edu', 23);

-- Insert Courses
INSERT INTO COURSE (ID, NAME, DESCRIPTION) VALUES
(400, 'Design Patterns', 'Advanced software design patterns including Factory, Singleton, and Observer'),
(401, 'PLC Programming', 'Fundamentals of Programmable Logic Controllers and industrial automation'),
(402, 'Computer Architecture', 'Computer organization, CPU design, and memory hierarchy'),
(403, 'Database Systems', 'Relational databases, SQL, and transaction management'),
(404, 'Algorithms', 'Analysis and design of computer algorithms');

-- Insert Student-Course enrollments
INSERT INTO STUDENT_COURSE (STUDENT_ID, COURSE_ID) VALUES
-- Tarek takes 3 courses
(100, 400), (100, 402), (100, 403),
-- Doaa takes 2 courses
(101, 401), (101, 404),
-- Ahmed takes 4 courses
(102, 400), (102, 401), (102, 402), (102, 403),
-- Lina takes 2 courses
(103, 402), (103, 404),
-- Omar takes 3 courses
(104, 400), (104, 401), (104, 404);

-- Insert Books (with student relationships)
INSERT INTO BOOKS (ID, BOOK_TITLE, BOOK_AUTHOR, STUDENT_ID) VALUES
-- Tarek's books
(500, 'Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma', 100),
(501, 'Clean Architecture', 'Robert C. Martin', 100),
(502, 'Database System Concepts', 'Abraham Silberschatz', 100),
-- Doaa's books
(503, 'Automating Manufacturing Systems with PLCs', 'Hugh Jack', 101),
(504, 'Introduction to Algorithms', 'Thomas H. Cormen', 101),
-- Ahmed's books
(505, 'Computer Organization and Design', 'David Patterson', 102),
(506, 'Structured Computer Organization', 'Andrew Tanenbaum', 102),
-- Lina's books
(507, 'Computer Architecture: A Quantitative Approach', 'John Hennessy', 103),
(508, 'The Algorithm Design Manual', 'Steven Skiena', 103),
-- Omar's books
(509, 'Head First Design Patterns', 'Eric Freeman', 104),
(510, 'Industrial Automation: Hands-On', 'Frank Lamb', 104),
(511, 'Algorithms Unlocked', 'Thomas H. Cormen', 104);