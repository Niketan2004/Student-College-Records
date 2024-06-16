import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Display {
     public void Show(Scanner sc, Connection con) {

          while (true) {

               System.out.println(
                         "--------------------------------- WELCOME TO DISPLAY TAB --------------------------------------");
               System.out.println(
                         "1.All students in Course(List of Students in Course) \n 2.Students course(Enrolled course of Student) \n 3.Students full detail \n 4.Fees Paid \n 5.Exit");
               int choice = sc.nextInt();
               if (choice == 5) {
                    System.out.println("Returning to HomePage!");
                    LoadingAnimation.exitAnimation();
                    break;
               }
               LoadingAnimation.animation();
               switch (choice) {
                    case 1 -> displayStudentsInCourse(sc, con);
                    case 2 -> displayStudentInfo(sc, con);
                    case 3 -> displayCompleteStudentDetailsById(sc, con);
                    case 4 -> displayEnrollmentDataByStudentId(sc, con);
                    default -> System.out.println("Enter Valid choice!");
               }
          }
     }

     // Method to display all the student in same course with the help of course id
     private void displayStudentsInCourse(Scanner sc, Connection con) {
          System.out.println(
                    "-------------------------------- DISPLAYING STUDENTS IN COURSE ------------------------------");
          System.out.println("Enter Course Id: ");
          int courseId = sc.nextInt();
          LoadingAnimation.animation();
          try (PreparedStatement ps = con.prepareStatement(Query.studentinCourse)) {
               ps.setInt(1, courseId);
               try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("+------------+------------+-----------+----------------+");
                    System.out.println("| Student ID | First Name | Last Name | Enrollment Date |");
                    System.out.println("+------------+------------+-----------+----------------+");
                    while (rs.next()) {
                         int studentId = rs.getInt("student_id");
                         String firstName = rs.getString("first_name");
                         String lastName = rs.getString("last_name");
                         String enrollmentDate = rs.getString("enrollment_date");
                         System.out.printf("| %-10d | %-10s | %-9s | %-14s |\n", studentId, firstName, lastName,
                                   enrollmentDate);
                    }
                    System.out.println("+------------+------------+-----------+----------------+");
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     // Method to display student basic info by student id
     private void displayStudentInfo(Scanner sc, Connection con) {
          System.out.println("------------------------ Display Student Course  ----------------------------------");
          System.out.println("Enter Student Id: ");
          int studentId = sc.nextInt();
          LoadingAnimation.animation();
          try (PreparedStatement ps = con.prepareStatement(Query.displayStudentCourse)) {

               ps.setInt(1, studentId);
               try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("+------------+-----------+-------------------------+");
                    System.out.println("| First Name | Last Name | Course Name             |");
                    System.out.println("+------------+-----------+-------------------------+");
                    while (rs.next()) {
                         String firstName = rs.getString("first_name");
                         String lastName = rs.getString("last_name");
                         String courseName = rs.getString("course_name");
                         System.out.printf("| %-10s | %-9s | %-23s |\n", firstName, lastName, courseName);
                    }
                    System.out.println("+------------+-----------+-------------------------+");
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     // Method to display complete student details by student ID
     private void displayCompleteStudentDetailsById(Scanner sc, Connection con) {
          System.out.println("Enter Student Id: ");
          int studentId = sc.nextInt();
          LoadingAnimation.animation();
          try (PreparedStatement ps = con.prepareStatement(Query.studentsAllDataById)) {
               System.out.println(
                         "------------------------------ DISPLAYING STUDENTS ALL DATA -------------------------------------");
               ps.setInt(1, studentId);
               try (ResultSet rs = ps.executeQuery()) {
                    System.out.println(
                              "+------------+------------+------------+------------+------------+------------------------+----------------------+------------+---------------+-----------------+-------------------+");
                    System.out.println(
                              "| Student ID | First Name | Last Name  | Birth Date | Gender     | Address                  | Email                | Phone      | Enrollment ID | Course Name     | Enrollment Date   |");
                    System.out.println(
                              "+------------+------------+------------+------------+------------+------------------------+----------------------+------------+---------------+-----------------+-------------------+");
                    while (rs.next()) {
                         int id = rs.getInt("student_id");
                         String firstName = rs.getString("first_name");
                         String lastName = rs.getString("last_name");
                         String birthDate = rs.getString("date_of_birth");
                         String gender = rs.getString("gender");
                         String address = rs.getString("address");
                         String email = rs.getString("email");
                         String phone = rs.getString("phone_number");
                         int enrollmentId = rs.getInt("enrollment_id");
                         String courseName = rs.getString("course_name");
                         String enrollmentDate = rs.getString("enrollment_date");

                         // Adjusted printf statement for proper formatting
                         System.out.printf(
                                   "| %-10d | %-10s | %-10s | %-10s | %-10s | %-25s | %-20s | %-10s | %-13d | %-15s | %-17s |\n",
                                   id, firstName, lastName, birthDate, gender, address, email, phone, enrollmentId,
                                   courseName, enrollmentDate);
                    }
                    System.out.println(
                              "+------------+------------+------------+------------+------------+------------------------+----------------------+------------+---------------+-----------------+-------------------+");
               }

          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     // Method to display enrollment data with student name by student ID
     private void displayEnrollmentDataByStudentId(Scanner sc, Connection con) {
          System.out.println("Enter Student Id: ");
          int studentId = sc.nextInt();
          LoadingAnimation.animation();
          try (PreparedStatement ps = con.prepareStatement(Query.studentEnrollment)) {
               ps.setInt(1, studentId);
               try (ResultSet rs = ps.executeQuery()) {
                    System.out.println(
                              "+---------------+---------------+------------------+--------------+-------------+---------------------+-------------+--------------+");
                    System.out.println(
                              "|  First Name   |   Last Name   |  Enrollment ID   |  Student ID  |  Course ID  |  Enrollment Date    |  Fees Paid  |  Total Fees  |");
                    System.out.println(
                              "+---------------+---------------+------------------+--------------+-------------+---------------------+-------------+--------------+");
                    while (rs.next()) {
                         String firstName = rs.getString("first_name");
                         String lastName = rs.getString("last_name");
                         int enrollmentId = rs.getInt("enrollment_id");
                         int studentIdFromDB = rs.getInt("student_id");
                         int courseId = rs.getInt("course_id");
                         String enrollmentDate = rs.getString("enrollment_date");
                         double feesPaid = rs.getDouble("fees_paid");
                         double totalFees = rs.getDouble("total_fees");

                         System.out.printf("| %-13s | %-13s | %-16d | %-12d | %-11d | %-19s | %-11.2f | %-12.2f |\n",
                                   firstName, lastName, enrollmentId, studentIdFromDB, courseId, enrollmentDate,
                                   feesPaid, totalFees);
                    }
                    System.out.println(
                              "+---------------+---------------+------------------+--------------+-------------+---------------------+-------------+--------------+");
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
     }
}
