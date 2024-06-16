# College Management System 🎓

This repository contains a College Management System implemented in Java with MySQL database integration. The system facilitates managing students, courses, enrollments, and fees within a college environment.

## Project Structure 📁

The project is structured as follows:

- **`src/`**: Contains Java source code files.
  - **`LoadingAnimation.java`**: Handles loading and exiting animations for a better user interface experience.
  - **`Display.java`**: Provides methods to display student and course-related information based on user inputs.

- **`db.properties`**: Configuration file for database connection details.

## Classes and Their Functionality 🖥️

### `LoadingAnimation.java`

This class manages the loading and exiting animations displayed during the execution of certain tasks within the system. It includes methods like `animation()` and `exitAnimation()` which simulate loading processes using ASCII characters.

### `Display.java`

This class is responsible for displaying various types of information related to students and courses based on user input. It contains methods such as:

- **`Show(Scanner sc, Connection con)`**: Main method that interacts with the user to display different types of information such as all students in a course, student course details, complete student details, fees paid, and provides an option to exit.
  
- **Private Methods**:
  - **`displayStudentsInCourse(Scanner sc, Connection con)`**: Displays all students enrolled in a specific course based on the course ID.
  - **`displayStudentInfo(Scanner sc, Connection con)`**: Shows the course details of a specific student based on the student ID.
  - **`displayCompleteStudentDetailsById(Scanner sc, Connection con)`**: Displays comprehensive details of a student including personal information, enrollment details, and course details based on the student ID.
  - **`displayEnrollmentDataByStudentId(Scanner sc, Connection con)`**: Shows enrollment data including student name, enrollment ID, course ID, enrollment date, fees paid, and total fees based on the student ID.

## Database Schema 🗃️

### Table: `courses`

| Field          | Type           | Null | Key | Default           | Extra          |
|----------------|----------------|------|-----|-------------------|----------------|
| course_id      | int            | NO   | PRI | NULL              | auto_increment |
| course_name    | varchar(100)   | YES  |     | NULL              |                |
| fees           | decimal(10,2)  | YES  |     | NULL              |                |
| total_enrolled | int            | YES  |     | 0                 |                |

### Table: `students`

| Field         | Type          | Null | Key | Default           | Extra          |
|---------------|---------------|------|-----|-------------------|----------------|
| student_id    | int           | NO   | PRI | NULL              | auto_increment |
| first_name    | varchar(50)   | YES  |     | NULL              |                |
| last_name     | varchar(50)   | YES  |     | NULL              |                |
| date_of_birth | date          | YES  |     | NULL              |                |
| gender        | char(1)       | YES  |     | NULL              |                |
| address       | varchar(100)  | YES  |     | NULL              |                |
| email         | varchar(50)   | YES  |     | NULL              |                |
| phone_number  | varchar(15)   | YES  | UNI | NULL              |                |

### Table: `fees`

| Field             | Type           | Null | Key | Default           | Extra             |
|-------------------|----------------|------|-----|-------------------|-------------------|
| transaction_id    | int            | NO   | PRI | NULL              | auto_increment    |
| student_id        | int            | YES  | MUL | NULL              |                   |
| course_id         | int            | YES  | MUL | NULL              |                   |
| total_fees        | decimal(10,2)  | YES  |     | NULL              |                   |
| fees_paid         | decimal(10,2)  | YES  |     | NULL              |                   |
| payment_date_time | datetime       | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |

### Table: `enrollments`

| Field           | Type           | Null | Key | Default           | Extra          |
|-----------------|----------------|------|-----|-------------------|----------------|
| enrollment_id   | int            | NO   | PRI | NULL              | auto_increment |
| student_id      | int            | YES  | MUL | NULL              |                |
| course_id       | int            | YES  | MUL | NULL              |                |
| enrollment_date | date           | YES  |     | NULL              |                |
| fees_paid       | decimal(10,2)  | YES  |     | 0.00              |                |
| total_fees      | decimal(10,2)  | YES  |     | 0.00              |                |

## Queries and Triggers 📋

### Queries

- **Creating Courses Table**:

```sql
CREATE TABLE IF NOT EXISTS courses (
    course_id int NOT NULL AUTO_INCREMENT,
    course_name varchar(100),
    fees decimal(10,2),
    total_enrolled int DEFAULT 0,
    PRIMARY KEY (course_id)
);

```
- **Creating Students table**;
  
  ```sql
  CREATE TABLE IF NOT EXISTS students (
    student_id int NOT NULL AUTO_INCREMENT,
    first_name varchar(50),
    last_name varchar(50),
    date_of_birth date,
    gender char(1),
    address varchar(100),
    email varchar(50),
    phone_number varchar(15) UNIQUE,
    PRIMARY KEY (student_id)
    );
  ```

- **Creating Fees table**;

  ```sql
    CREATE TABLE IF NOT EXISTS fees (
    transaction_id int NOT NULL AUTO_INCREMENT,
    student_id int,
    course_id int,
    total_fees decimal(10,2),
    fees_paid decimal(10,2),
    payment_date_time datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
    );
  ```
- ** Creating Enrollments table**;
    ```sql
    CREATE TABLE IF NOT EXISTS enrollments (
    enrollment_id int NOT NULL AUTO_INCREMENT,
    student_id int,
    course_id int,
    enrollment_date date,
    fees_paid decimal(10,2) DEFAULT 0.00,
    total_fees decimal(10,2) DEFAULT 0.00,
    PRIMARY KEY (enrollment_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
  );
  ```
  ## Triggers
   - Trigger to Update Total Enrolled Count in Courses Table:
    ```sql
  DELIMITER //
  CREATE TRIGGER update_total_enrolled
  AFTER INSERT ON enrollments
  FOR EACH ROW
  BEGIN
  UPDATE courses
  SET total_enrolled = total_enrolled + 1
  WHERE course_id = NEW.course_id;
  END //
  DELIMITER ;
    ```
  -Trigger to Update Total Enrolled Count in Courses Table on Enrollment Deletion:
  ```sql
  Copy code
  DELIMITER //
  CREATE TRIGGER decrease_total_enrolled
  AFTER DELETE ON enrollments
  FOR EACH ROW
  BEGIN
    UPDATE courses
    SET total_enrolled = total_enrolled - 1
    WHERE course_id = OLD.course_id;
  END //
  DELIMITER ;
  ```
-Trigger to Update Total Fees in Fees Table on Enrollment Insertion:
  ```sql
  Copy code
  DELIMITER //
  CREATE TRIGGER update_total_fees_insert
  AFTER INSERT ON enrollments
  FOR EACH ROW
  BEGIN
    UPDATE fees
    SET total_fees = total_fees + NEW.total_fees
    WHERE student_id = NEW.student_id;
  END //
  DELIMITER ;
  ```
-Trigger to Update Total Fees in Fees Table on Enrollment Deletion:
  ```sql
  Copy code
  DELIMITER //
  CREATE TRIGGER update_total_fees_delete
  AFTER DELETE ON enrollments
  FOR EACH ROW
  BEGIN
    UPDATE fees
    SET total_fees = total_fees - OLD.total_fees
    WHERE student_id = OLD.student_id;
  END //
  DELIMITER ;
  ```

## Conclusion
<<<<<<< HEAD
This College Management System project leverages Java for its backend logic and MySQL for data storage. It offers functionalities for managing students, courses, enrollments, and fees efficiently. The structure and queries provided ensure proper database management and seamless interaction with the system.  
=======
This College Management System project leverages Java for its backend logic and MySQL for data storage. It offers functionalities for managing students, courses, enrollments, and fees efficiently. The structure and queries provided ensure proper database management and seamless interaction with the system.  
>>>>>>> 0f2e4585c84844157431395f3ab9685f2507f37e
