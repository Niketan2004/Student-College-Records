import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Enrollment {
     public void Enroll(Scanner sc, Connection con) {

          while (true) {
               System.out.println("-------------------------- WELCOME TO ENROLLMENT SECTION ---------------------");
               System.out.println(
                         "-----------------------------------------------------------------------------------------");
               System.out.println("1.ADD STUDENT \n 2.UPDATE STUDENT \n 3.REMOVE STUDENT \n 4.FEES PAYMENTS \n 5.EXIT");
               System.out.println("Enter Your Choice :- ");
               int choice = sc.nextInt();
               if (choice == 5) {
                    System.out.println("Returning to Homepage....");
                    LoadingAnimation.exitAnimation();
                    break;
               }
               LoadingAnimation.animation();
               try {
                    switch (choice) {
                         case 1 -> addStudent(con, sc);
                         case 2 -> updateStudent(con, sc);
                         case 3 -> removeStudent(sc, con);
                         case 4 -> feesUpdate(sc, con);

                         default -> System.out.println("Enter Valid choice !");
                    }

               } catch (Exception e) {
                    System.out.println("Please Enter Valid Number !");

               }
          }

     }

     // Adding Student into Daatabase
     private void addStudent(Connection con, Scanner sc) {
          System.out.println("------------------------- ENTER STUDENT DETAILS ----------------------");
          sc.nextLine();
          System.out.println("Enter Student First name :-  "); // STUDENT NAME
          String name = sc.nextLine();
          sc.nextLine();
          System.out.println("Enter Student Last Name :- "); // STUDENT LAST NAME
          String last_name = sc.nextLine();
          // sc.nextLine();
          System.out.println("Enter Student Birth Date (yyyy-mm-dd) :- "); // STUDENTS BIRTH DATE
          String date = sc.nextLine();
          // sc.nextLine();
          System.out.println("Enter Gender (M/F)"); // STUDENTS GENFDER
          char gender = Character.toUpperCase(sc.next().charAt(0));
          sc.nextLine();
          System.out.println("Enter Address of Student :- "); // STUDENTS ADDRESS
          String add = sc.nextLine();

          // sc.nextLine();
          System.out.println("Enter Email id of Student :- "); // STUDENT EMAIL ID
          String email = sc.nextLine();
          // sc.nextLine();
          System.out.println("Enter Mobile Number of Student :- "); // STUDENT PHONE NUMBER
          String num = sc.nextLine();
          // sc.nextLine();
          System.out.println("Enter Course id in which student wants to Enroll :- "); // COURSE NAME FOR WHICH STUDENT
                                                                                      // WANTS TO ENROLL
          int c_id = sc.nextInt();
          // PREPARE STATMENT FOR INSERTING DATA INTO STUDENT DATABASE
          try (PreparedStatement ps = con.prepareStatement(Query.insertStudent,
                    PreparedStatement.RETURN_GENERATED_KEYS)) { // GENERATED KEYS GIVES THE STUDENT ID

               ps.setString(1, name);
               ps.setString(2, last_name);
               ps.setString(3, date);
               ps.setString(4, String.valueOf(gender));
               ps.setString(5, add);
               ps.setString(6, email);
               ps.setString(7, num);
               // EXECUTES SQL QUERY FOR STUDENTS DATABASE
               int rowsAffected = ps.executeUpdate();
               LoadingAnimation.animation();
               if (rowsAffected > 0) {
                    // AFTER EXECUTING STUDENTS DATABASE STUDENT ID WILL BE RETURNED AND THEN THE
                    // QUERY TO INSERT RESPECTED COURSE IN THE ENROLLMENTS TABLE EXECUTES
                    try (ResultSet rs = ps.getGeneratedKeys();
                              PreparedStatement ps1 = con.prepareStatement(Query.studentCourse)) {
                         if (rs.next()) {
                              int id = rs.getInt(1);
                              ps1.setInt(1, id);
                              ps1.setInt(2, c_id);
                              int rowsAffected1 = ps1.executeUpdate();
                              if (rowsAffected1 > 0) {
                                   // WHEN DATA SUCCESSFULLY INSERTED INTO ENROLLMENTS TABLE THE FOLLOWING CODE
                                   // WORKS.

                                   System.out.println("Student has been  Successfully Enrolled to respected Course ! ");
                                   System.out.println("Student id = " + id);
                                   System.out.println("PLEASE NOTE THIS STUDENT ID ");
                              } else {
                                   System.out.println("Failed to Enroll Student in Course !!!");
                              }
                         }
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
               } else {
                    System.out.println("Failed to insert Student Data !!!");
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     // Choosing what to update
     private void updateStudent(Connection con, Scanner sc) {
          boolean isUpdate = true;
          while (isUpdate) {
               System.out.println("------------------------  UPDATING STUDENT DATA ---------------------------");
               System.out.println(
                         "-----------------------------------------------------------------------------------");
               System.out.println(
                         "1.Student Name \n 2.Birth Date \n 3.Address \n 4.Email Id \n 5.Phone Number \n 6. Course \n 7.Exit");
               System.out.println("Choose what do you want to update :- ");
               int choice = sc.nextInt();

               if (choice == 7) {
                    System.out.println("Returning to Homepage.......");
                    LoadingAnimation.exitAnimation();
                    isUpdate = false;
                    break;
               } else {

                    System.out.println("Enter Student Id :- ");
                    int student_id = sc.nextInt();
                    sc.nextLine();
                    String query;
                    switch (choice) {
                         case 1 -> {
                              // if user wants to change name then this code works
                              System.out.println("Enter Student First Name :- "); // Taking first name
                              String name = sc.nextLine();
                              System.out.println("Enter Student Last name :- "); // Taking Last Name
                              String last_name = sc.nextLine();
                              // updating student name
                              // SEPERATELY EXECUTED BECAUSE OF THE VALUES TO BE UPDATED ARE TWO :- FIRST_NAME
                              // AND LAST_NAME ...
                              try (PreparedStatement ps = con.prepareStatement(Query.updateName)) {
                                   ps.setString(1, name);
                                   ps.setString(2, last_name);
                                   ps.setInt(3, student_id);
                                   int rowsAffected = ps.executeUpdate();
                                   // checking if data updated or not
                                   LoadingAnimation.animation();
                                   if (rowsAffected > 0) {
                                        System.out.println("Student Name Updated Successfully");

                                   } else {
                                        System.out.println("Failed to update Student Name ! ");
                                   }
                              } catch (Exception e) {
                                   e.printStackTrace();
                              }
                         }
                         // UPDATNG BIRTH DATE OF STUDENT
                         case 2 -> {
                              sc.nextLine();
                              System.out.println("Enter Birth Date (yyyy-mm-dd) :- ");
                              String date = sc.nextLine();
                              LoadingAnimation.animation();
                              query = "UPDATE students SET date_of_birth = ? where student_id = ?"; // QUERY TO UPDATE
                                                                                                    // BIRTH DATE IN
                                                                                                    // DATABASE
                              // METHOD THAT EXECUTES THE UPDATE WITH THE GIVEN QUERY IN IT.
                              update(con, sc, query, date, student_id);
                         }
                         // UPDATIN ADDRESS OF THE STUDENT
                         case 3 -> {
                              sc.nextLine();
                              System.out.println("Enter new Address :- ");
                              String add = sc.nextLine();
                              LoadingAnimation.animation();
                              query = "UPDATE students SET address = ? where student_id = ?";
                              update(con, sc, query, add, student_id);

                         }
                         // UPDATING EMAIL ADDRESS OF STUDENT
                         case 4 -> {
                              sc.nextLine();
                              System.out.println("Enter New Email Id :- ");
                              String email = sc.nextLine();
                              LoadingAnimation.animation();
                              query = "UPDATE students SET email = ? where student_id = ?";
                              update(con, sc, query, email, student_id);

                         }
                         // UPDATING STUDENTS PHONE NUMBER
                         case 5 -> {
                              sc.nextLine();
                              System.out.println("Enter New Phone Number :- ");
                              String num = sc.nextLine();
                              LoadingAnimation.animation();
                              query = "UPDATE students SET phone_number = ? where student_id = ?";
                              update(con, sc, query, num, student_id);
                         }
                         case 6 -> {
                              System.out.println("Enter new course id :-  ");
                              int c_id = sc.nextInt();
                              LoadingAnimation.animation();
                              query = " UPDATE enrollments SET course_id = ? WHERE student_id = ?"; // QUERY TO UPDATE
                                                                                                    // CURSE OF THE
                                                                                                    // PERTICULAR
                                                                                                    // STUDENT.
                              // COURSE IS UPDATED IN THE FOLLOWING CODE
                              try (PreparedStatement ps = con.prepareStatement(query)) {
                                   ps.setInt(1, c_id);
                                   ps.setInt(2, student_id);
                                   int rowsAffected = ps.executeUpdate();
                                   // checking if data updated or not
                                   if (rowsAffected > 0) {
                                        System.out.println("Students Course Updated Successfully");

                                   } else {
                                        System.out.println("Failed to update Student Course ! ");
                                   }
                              } catch (Exception e) {
                                   e.printStackTrace();
                              }
                         }
                    }
               }
          }
     }

     // COMMON METHOD TO UPDATE PERTICULAR DATA
     private void update(Connection con, Scanner sc, String query, String updatedValue1, int student_id) {
          try (PreparedStatement ps = con.prepareStatement(query)) {
               ps.setString(1, updatedValue1); // VALUE TO UPDATED
               ps.setInt(2, student_id); // STUDENT ID FOR WHICH DATA TO BE UPDATED
               int rowsAffected = ps.executeUpdate();
               // CHECKS THAT QUERY IS PERFECTLY EXECUTED AND DATA UPDATED SUCCESSFULY.
               if (rowsAffected > 0) {
                    System.out.println("Data Updated Successfully!!!!!!!");

               } else {
                    System.out.println("Failed to update data !!!!!");
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     private void removeStudent(Scanner sc, Connection con) {
          sc.nextLine();
          System.out.println("Enter the Student Id :- ");
          int id = sc.nextInt();
          try (PreparedStatement ps = con.prepareStatement(Query.removeStudent)) {
               ps.setInt(1, id);
               int rowsAffected = ps.executeUpdate();
               LoadingAnimation.animation();
               if (rowsAffected > 0) {
                    System.out.println("Student is Successfully Removed from the Database !");

               } else {
                    System.out.println("Failed to remove Student from the Database ! ");
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     private void feesUpdate(Scanner sc, Connection con) {
          System.out.println("-------------------------- FEES PAYMENT -----------------------------");
          System.out.println("Enter Student Id :- ");
          int s_id = sc.nextInt();
          System.out.println("Enter course Id :- ");
          int c_id = sc.nextInt();
          System.out.println("Enter the Amount  paid :- ");
          int fees = sc.nextInt();
          try (PreparedStatement ps = con.prepareStatement(Query.feesPaid)) {
               ps.setInt(1, s_id);
               ps.setInt(2, c_id);
               ps.setInt(3, fees);
               int rowsAffected = ps.executeUpdate();
               LoadingAnimation.animation();
               if (rowsAffected > 0) {
                    System.out.println("Amount has been Successfully Updated !");
               } else {
                    System.out.println("Failed to Update amount in Database!");
               }
          } catch (Exception e) {
               e.printStackTrace();
          }

     }
}
