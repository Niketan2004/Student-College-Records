public class Query {
     // QUERY TO ADD COURSES
     static final String addCourse = "INSERT INTO courses (course_name, fees) VALUES (?,?)";
     // QUERY TO REMOVE COURSES
     static final String removeCourse = "Delete from courses where course_id = ?";
     // QUERY TO DISPLAY COURSES;
     static final String displayCourses = "SELECT * FROM COURSES";
     // QUERY TO INSERT STUDENT;
     static final String insertStudent = " INSERT INTO Students (first_name, last_name, date_of_birth, gender, address, email, phone_number) VALUES (?,?,?,?,?,?,?)";
     // QUERY TO ENROLLING STUDENT INTO COURSE
     static final String studentCourse = "INSERT INTO Enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, CURRENT_DATE)";
     // QUERY TO UPDATE NAME OF STUDENT
     static final String updateName = "UPDATE students SET first_name = ? , last_name = ? WHERE student_id =?";
     // QUERY TO REMOVE STUDENT FROM DATABASE
     static final String removeStudent = " DELETE FROM students WHERE student_id = ? ";
     // QUERY TO INSERT THE AMOUNT OF FEES PAID BY STUDENT
     static final String feesPaid = " insert into fees(student_id, course_id,fees_paid) values(?,?,?)";
     // QUERY TO DISPLAY COURSE NAME OF STUDENT ENROLLED IN
     static final String displayStudentCourse = "SELECT s.first_name, s.last_name, c.course_name FROM students s JOIN enrollments e ON s.student_id = e.student_id "
               + "JOIN courses c ON e.course_id = c.course_id "
               + "WHERE s.student_id = ?";
     // QUERY TO DISPLAY LIST OF STUDENTS ENROLLED IN PERTICULAR COURSES
     static final String studentinCourse = "SELECT s.student_id, s.first_name, s.last_name, e.enrollment_date FROM students s "
               + "JOIN enrollments e ON s.student_id = e.student_id "
               + "WHERE e.course_id = ?";
     // QUERY TO RETRIEVE ALL THE DATA OF STUDENT FROM DATABASE
     static final String studentsAllDataById = "SELECT s.*, e.enrollment_id, c.course_name, e.enrollment_date FROM students s "
               + "JOIN enrollments e ON s.student_id = e.student_id "
               + "JOIN courses c ON e.course_id = c.course_id "
               + "WHERE s.student_id = ?";
     // QUERY TO DISPLAY ENROLLMENT DETAILS OF STUDENT
     static final String studentEnrollment = "SELECT  s.first_name, s.last_name,e.* FROM enrollments e JOIN students s ON e.student_id = s.student_id WHERE e.student_id = ?";
}
