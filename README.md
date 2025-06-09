# Student Management System

A Spring Boot application for managing students, their books, and courses with RESTful API endpoints.

## Features

- **Student Management**: Create, read, update, and delete student records
- **Book Management**: Manage books with student associations
- **Course Management**: Handle courses and student enrollments
- **Relationship Management**:
  - Assign books to students
  - Enroll students in courses
  - View all books/courses for a student
  - View all students in a course
- **Validation**: Comprehensive input validation
- **Error Handling**: Custom exceptions and global exception handler

## Technologies

- **Backend**: 
  - Java 17
  - Spring Boot 3.x
  - Spring Data JPA
  - Hibernate
  - H2 Database (embedded)
- **Validation**:
  - Jakarta Validation API
- **Build Tool**:
  - Maven

## Entity Relationships

- **Student → Book**: One-to-Many (One student can have multiple books)
- **Student ↔ Course**: Many-to-Many (Students can enroll in multiple courses)

## API Endpoints

### Student Endpoints

| Method | Endpoint                            | Description                          |
|--------|-------------------------------------|--------------------------------------|
| GET    | `/students`                         | Get all students                     |
| GET    | `/students/{id}`                    | Get student by ID                    |
| POST   | `/students`                         | Create new student                   |
| PUT    | `/students/{id}`                    | Update student                       |
| DELETE | `/students/{id}`                    | Delete student                       |
| POST   | `/students/{id}/books/{bookId}`     | Assign book to student               |
| GET    | `/students/{id}/books`              | Get all books for student            |
| DELETE | `/students/{id}/books/{bookId}`     | Remove book from student             |
| POST   | `/students/{id}/courses/{courseId}` | Enroll student in course             |
| GET    | `/students/{id}/courses`            | Get all courses for student          |
| DELETE | `/students/{id}/courses/{courseId}` | Remove student from course           |

### Book Endpoints

| Method | Endpoint                  | Description                          |
|--------|---------------------------|--------------------------------------|
| GET    | `/books`                  | Get all books                        |
| GET    | `/books/{id}`             | Get book by ID                       |
| POST   | `/books`                  | Create new book                      |
| PUT    | `/books/{id}`             | Update book                          |
| DELETE | `/books/{id}`             | Delete book                          |
| GET    | `/books/{id}/owner`       | Get owner of book                    |

### Course Endpoints

| Method | Endpoint                  | Description                          |
|--------|---------------------------|--------------------------------------|
| GET    | `/courses`                | Get all courses                      |
| GET    | `/courses/{id}`           | Get course by ID                     |
| POST   | `/courses`                | Create new course                    |
| PUT    | `/courses/{id}`           | Update course                        |
| DELETE | `/courses/{id}`           | Delete course                        |
| GET    | `/courses/{id}/students`  | Get all students in course           |

## Validation Rules

### Student
- Name: min 3 characters
- Email: valid format and not blank
- Date of Birth: must be in the past

### Book
- Title: min 8 characters
- Author: min 6 characters

### Course
- Name: min 3 characters
- Description: min 10 characters

## Getting Started

| Requirement  | Version  |
|--------------|----------|
| Java         | 17+      |
| Maven        | 3.8+     |

- IDE (IntelliJ, Eclipse, etc.)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/DoaaTawfik0/student-management-system.git
   ```
2. Navigate to project directory:
   ```bash
   cd student-management-system
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on port 8080 (default).

## Database Configuration

The application uses an embedded H2 database by default.  
Access the H2 console at: `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:mem:testdb`  
Username: `sa`  
Password: (leave empty)

## Exception Handling

The application provides detailed error responses for:
- Resource not found (404)
- Validation errors (400)
- Internal server errors (500)

Example error response:
```json
{
  "timeStamp": "2023-05-15T10:30:45.12345",
  "message": "Student not found with id: 999",
  "details": "uri=/students/999"
}
```

## API Testing Resources

### Swagger UI
For interactive testing  
📚 [Swagger UI](http://localhost:8080/swagger-ui/index.html)

## Future Enhancements

- [ ] Add authentication and authorization  
- [ ] Implement pagination and sorting  
- [ ] Add more comprehensive testing  
- [ ] Implement DTOs for better separation  
- [ ] Dockerize application  
- [ ] Add CI/CD pipeline
