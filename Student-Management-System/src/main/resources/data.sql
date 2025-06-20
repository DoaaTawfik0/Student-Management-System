-- Insert Students
INSERT INTO STUDENT(ID, NAME, EMAIL, DATE_OF_BIRTH) VALUES(100, 'Tarek', 'tarek@gmail.com', '2021-06-01');
INSERT INTO STUDENT(ID, NAME, EMAIL, DATE_OF_BIRTH) VALUES(101, 'Doaa', 'doaa@gmail.com', '2021-02-15');

-- Insert Courses
INSERT INTO COURSE(ID, DESCRIPTION, NAME) VALUES(400, 'Explaining Design Patterns(Factory and Singleton)', 'Design Patterns');
INSERT INTO COURSE(ID, DESCRIPTION, NAME) VALUES(401, 'Programmable Logic Controller', 'Plc');
INSERT INTO COURSE(ID, DESCRIPTION, NAME) VALUES(402, 'Computer Architecture||Ahmed Saleh', 'Computer Architecture');

-- Insert Student-Course relationships (enrollments)
-- Tarek takes Design Patterns and Computer Architecture
INSERT INTO STUDENT_COURSE(STUDENT_ID, COURSE_ID) VALUES(100, 400);
INSERT INTO STUDENT_COURSE(STUDENT_ID, COURSE_ID) VALUES(100, 402);

-- Doaa takes PLC and Computer Architecture
INSERT INTO STUDENT_COURSE(STUDENT_ID, COURSE_ID) VALUES(101, 401);
INSERT INTO STUDENT_COURSE(STUDENT_ID, COURSE_ID) VALUES(101, 402);


-- Insert Books with student relationships
INSERT INTO BOOK(ID, TITLE, AUTHOR, STUDENT_ID) VALUES(500, 'Algorithms Unlocked', 'Thomas H. Cormen', 100);
INSERT INTO BOOK(ID, TITLE, AUTHOR, STUDENT_ID) VALUES(501, 'Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', 101);
INSERT INTO BOOK(ID, TITLE, AUTHOR, STUDENT_ID) VALUES(502, 'Design Patterns: Elements of Reusable Object-Oriented Software', 'Gang of Four', 100);
INSERT INTO BOOK(ID, TITLE, AUTHOR, STUDENT_ID) VALUES(503, 'The Pragmatic Programmer', 'Andrew Hunt', 100);
INSERT INTO BOOK(ID, TITLE, AUTHOR, STUDENT_ID) VALUES(504, 'Refactoring', 'Martin Fowler', 101);



